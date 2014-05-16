package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import logic.*;
import Exception.*;

public class DAO {

	static Logger logger = Logger.getLogger(DAO.class.getName());
	private Connection connection;
	DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

	public DAO () {
		try {
			connection = DBConnectionFactory.getConnection();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.info("Got connection");
	}

	public List<HotelDTO> getAllHotels(){
		List<HotelDTO> hotels = new ArrayList<HotelDTO>();
		PreparedStatement ps = null;
		ResultSet res = null;
		String query = "select name, city from hotel";

		try {
			ps = connection.prepareStatement(query);
			res = ps.executeQuery();

			while (res.next()) {
				String name = res.getString("name");
				String city = res.getString("city");
				hotels.add(new HotelDTO(name, city));
			}
			
			ps.close();
			res.close();
		} catch (Exception e) {

		}
		return hotels;
	}
	
	public List<RoomDTO> getAllRooms(){
		List<RoomDTO> rooms = new ArrayList<RoomDTO>();
		PreparedStatement ps = null;
		ResultSet res = null;
		String query = "select roomnumber, roomtypeid, availability, hotelid from room";
		try {
			ps = connection.prepareStatement(query);
			res = ps.executeQuery();
			
			while (res.next()) {
				int roomnumber = res.getInt("roomnumber");
				int roomtypeid = res.getInt("roomtypeid");
				String availability = res.getString("availability");
				int hotelid = res.getInt("hotelid");
				rooms.add(new RoomDTO(roomnumber, roomtypeid, availability, hotelid));
			}
			
			ps.close();
			res.close();
		} catch (Exception e) {
			
		}
		return rooms;
	}

	public List<CustomerbookingDTO> getAllCustomerbookings(){
		List<CustomerbookingDTO> bookings = new ArrayList<CustomerbookingDTO>();
		PreparedStatement ps = null;
		ResultSet res = null;
		String query = "select * from customerbooking";
		try {
			ps = connection.prepareStatement(query);
			res = ps.executeQuery();
			
			while (res.next()) {
				int hotelid = res.getInt("hotelid");
				int roomtypeid = res.getInt("roomtypeid");
				Date start = res.getDate("startdate");
				Date end = res.getDate("enddate");
				String first = res.getString("firstname");
				String last = res.getString("lastname");
				String unique = res.getString("uniquestring");
				String pin = res.getString("pin");
				bookings.add(new CustomerbookingDTO(hotelid, roomtypeid, start, end, first, last, unique, pin));
			}
			
			ps.close();
			res.close();
		} catch (Exception e) {
			
		}
		return bookings;
	}

}
