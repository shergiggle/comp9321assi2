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
		String query = "select * from room";
		try {
			ps = connection.prepareStatement(query);
			res = ps.executeQuery();
			
			while (res.next()) {
				int roomnumber = res.getInt("number");
				String roomtype = res.getString("type");
				String availability = res.getString("availability");
				int hotelid = res.getInt("hotelid");
				rooms.add(new RoomDTO(roomnumber, roomtype, availability, hotelid));
			}
			
			ps.close();
			res.close();
		} catch (Exception e) {
			
		}
		return rooms;
	}

	public ArrayList<SearchDTO> getSearchResults(Date checkinsqldate,
			Date checkoutsqldate, String city, String maxprice) throws SQLException {
		ArrayList<SearchDTO> search = new ArrayList<SearchDTO>();
		PreparedStatement sql = null;
		
		
		String query = "select h.id, h.city, h.name as hotelname, rt.cost, rt.name as roomtype, count(rt.roomtype) as count"
				+ " from roomavailability ra join roomtype rt on ra.roomtypeid = rt.roomtypeid"
				+ " join customerbooking cb on (cb.id = ra.customerbookingid)"
				+ " join hotel on (h.id = cb.hotelid)"
				+ " where (h.city = ?)"
				+ " and"
				+ " rt.price<= ?"
				+ " ((cb.startdate between ? and ?)"
				+ " or"
				+ " (cb.enddate between ? and ?))"
				+ " group by"
				+ " rt.roomtype, rt.price";
		
		sql = connection.prepareStatement(query);
		sql.setString(1, city);
		sql.setString(2, maxprice);
		sql.setDate(3, checkinsqldate);
		sql.setDate(4, checkoutsqldate);
		sql.setDate(5, checkinsqldate);
		sql.setDate(6, checkoutsqldate);
		
		ResultSet res = sql.executeQuery();
		
		while(res.next()){
			int hotelid = res.getInt("id");
			String hotellocation = res.getString("city");
			String hotelname = res.getString("hotelname");
			int roomcost = res.getInt("cost");
			String roomtype = res.getString("roomtype");
			int count = res.getInt("count");
			SearchDTO searchres = new SearchDTO(hotelid, hotellocation, hotelname, roomcost, roomtype, count);
			search.add(searchres);
		}
		
		res.close();
		sql.close();
		
		return search;	
	}


}
