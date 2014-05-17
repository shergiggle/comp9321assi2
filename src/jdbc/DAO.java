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
		ResultSet discountPrice = null;
		
		
		String query = "select h.id, h.city as hotelname, rt.cost, rt.name as roomtype, count(rt.name) as count"
				+ "from hotel h join room r on r.hotelid = h.id"
				+ "join roomtype rt on rt.id = r.roomtypeid"
				+ "where h.city = ?"
				+ "and rt.price <= ?;";
		
		sql = connection.prepareStatement(query);
		sql.setString(1, city);
		sql.setString(2, maxprice);
		ResultSet resAll = sql.executeQuery();
//-------------------------
		
		query = "select rt.name as roomtype, rt.cost, count(rt.name) as count"
				+ "from roomavailability ra join roomtype rt on (ra.roomtypeid = rt.id)"
				+ "join customerbooking cb on (cb.id = ra.customerbookingid)"
				+ "join hotel on (cb.hotelid = h.id)"
				+ "where h.city = ?"
				+ "and ((cb.startdate between ? and ?)"
				+ "or (cb.enddate between ? and ?))"
				+ "group by rt.name, rt.cost";
				
		sql.setString(1, city);
		sql.setDate(2, checkinsqldate);
		sql.setDate(3, checkoutsqldate);
		sql.setDate(4, checkinsqldate);
		sql.setDate(5, checkoutsqldate);
		ResultSet resBooked = sql.executeQuery();
		
		Map<String, Integer> allSelectCount = new HashMap<String,Integer>();
		Map<String, Integer> allSelectPrice = new HashMap<String,Integer>();
		while (resAll.next()) {
			allSelectCount.put(resAll.getString("roomtype"), resAll.getInt("count"));
			allSelectPrice.put(resAll.getString("roomtype"), resAll.getInt("cost"));
		}
		while (resBooked.next()) {
			if (allSelectCount.containsKey(resBooked.getString("roomtype"))) {
				//find matching room_Type and subtract count
				allSelectCount.put(resBooked.getString("roomtype"), (allSelectCount.get(resBooked.getString("roomtype")) - resBooked.getInt("count")));
			}
		}
		
		Iterator<String> iter = allSelectCount.keySet().iterator();
		while (iter.hasNext()) {
			String roomType = (String)iter.next();
			int price = allSelectPrice.get(roomType);
			int count = allSelectCount.get(roomType);
			
			sql = connection.prepareStatement("" +
					"select h.id, h.name, h.city, d.discountedprice from discount d " +
					"join roomtype rt on (rt.id = d.roomtypeid) " +
					"join hotel h on (h.id = d.hotelid) " +
					"where h.city = ? " +
					"and rt.name = ? " +
					"and ((? between d.startdate and d.enddate )" +
					"or (? between d.startdate and d.enddate))"
					+ "group by h.id, h.name, h.city, d.discountedprice");
			
			sql.setString(1, city);
			sql.setString(2, roomType);
			sql.setDate(3, checkinsqldate);
			sql.setDate(4, checkoutsqldate);
			discountPrice = sql.executeQuery();
			
			if (discountPrice.next()) {
				SearchDTO roomTypeDTO = new SearchDTO(discountPrice.getInt("id"), discountPrice.getString("city"), discountPrice.getString("name"),discountPrice.getInt("discountedprice"),roomType,count);
				roomTypeDTO.setDiscounted(true);
				search.add(roomTypeDTO);
			} else {
				search.add(new SearchDTO(discountPrice.getInt("id"), discountPrice.getString("city"), discountPrice.getString("name"), price ,roomType,count));
			}
		}
		
		

		sql.close();
		
		return search;	
	}

	public ArrayList<HotelDTO> getHotelsinCity(String city) throws SQLException {
		ArrayList<HotelDTO> hotelname = null;
		PreparedStatement sql = null;
		String query = "select h.name, h.city"
				+ "from hotel"
				+ "where h.city = ?";
		sql = connection.prepareStatement(query);
		sql.setString(1, city);
		
		ResultSet res = sql.executeQuery();
		while(res.next()){
			String temphotelname = res.getString("name");
			String tempcity = res.getString("city");
			HotelDTO hname = new HotelDTO(temphotelname, tempcity);
			hotelname.add(hname);
		}
		
		return hotelname;
	}


}
