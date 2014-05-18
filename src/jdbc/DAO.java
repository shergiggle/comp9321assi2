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
			logger.info("created connection");
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				int roomnumber = res.getInt("roomnumber");
				String roomtype = res.getString("roomtype");
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
			Date checkoutsqldate, String city, int maxprice) throws SQLException {
		ArrayList<SearchDTO> search = new ArrayList<SearchDTO>();
		PreparedStatement sql = null;
		//ResultSet discountPrice = null;

		sql = connection.prepareStatement("select h.id, h.city as hotelname, rt.cost, rt.name as roomtype, count(rt.name) as count"
				+ " from hotel h join room r on r.hotelid = h.id"
				+ " join roomtype rt on rt.id = r.roomtypeid"
				+ " where h.city = ?"
				+ " and rt.cost <= ?"
				+ " group by h.id, h.city, rt.cost, rt.name");
		
		sql.setString(1, city);
		sql.setInt(2, maxprice);
		ResultSet resAll = sql.executeQuery();
//-------------------------
		
		sql = connection.prepareStatement("select rt.name as roomtype, rt.cost, count(rt.name) as count"
				+ " from roomavailability ra join roomtype rt on (ra.roomtypeid = rt.id)"
				+ " join customerbooking cb on (cb.id = ra.customerbookingid)"
				+ " join hotel h on (cb.hotelid = h.id)"
				+ " where h.city = ?"
				+ " and ((cb.startdate between ? and ?)"
				+ " or (cb.enddate between ? and ?))"
				+ " group by rt.name, rt.cost");		
		
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
			System.out.print(roomType+"\n\n\n\n");
			//new query to select hotel id, hotel city, hotelname where rt.name like roomtype
			sql = connection.prepareStatement("select h.id as hotelid, h.name as hotelname, h.city from hotel h"
					+ "join room r on r.hotelid = h.id"
					+ "join roomtype rt on rt.id = r.roomtypeid"
					+ "where rt.name = ?"
					+ "group by h.id, h.name, h.city");
			sql.setString(1, roomType);
			ResultSet details = sql.executeQuery();
			
			search.add(new SearchDTO(details.getInt("hotelid"), details.getString("city"), details.getString("hotelname"), price ,roomType,count));
		}

		resAll.close();
		resBooked.close();
		sql.close();
		
		return search;	
	}

	@SuppressWarnings("null")
	public ArrayList<HotelDTO> getHotelsinCity(String city) throws SQLException {
		ArrayList<HotelDTO> hotelname = null;
		PreparedStatement sql = null;
		String query = "select h.name, h.city"
				+ "from hotel h"
				+ "where h.city = ?"
				+ "group by h.name, h.city";
		sql = connection.prepareStatement(query);
		sql.setString(1, city);
		
		ResultSet res = sql.executeQuery();
		while(res.next()){
			String temphotelname = res.getString("name");
			String tempcity = res.getString("city");
			HotelDTO hname = new HotelDTO(temphotelname, tempcity);
			hotelname.add(hname);
		}
		
		res.close();
		sql.close();
		
		return hotelname;
	}

	public RoomPriceDTO getRoomTypePrice(String room) throws SQLException {
		PreparedStatement sql = null;
		String query = "select cost, name from roomtype where name = ?";
		sql = connection.prepareStatement(query);
		sql.setString(1, room);
		
		ResultSet res = sql.executeQuery();
		res.next();
			RoomPriceDTO tempprice = new RoomPriceDTO(res.getInt("cost"), res.getString("name"));
		
			res.close();
			sql.close();
			
		return tempprice;
	}

	public int getHotelId(String name) throws SQLException{
		int hotelid = 0;
		
		PreparedStatement sql = null;
		String query = "select id from hotel where name = ? and city = ?";
		sql = connection.prepareStatement(query);
		sql.setString(1, name);
		
		ResultSet res = sql.executeQuery();
		res.next();
			hotelid = res.getInt("id");
				
			res.close();
			sql.close();
			
		return hotelid;
	}

	public void makeCustomer(String firstname, String lastname) throws SQLException {
		PreparedStatement sql = null;
		String query = "insert into customer (default, ? , ?)";
		sql = connection.prepareStatement(query);
		sql.setString(1, firstname);
		sql.setString(1, lastname);
		ResultSet res = sql.executeQuery();
		
		res.close();
		sql.close();
		
	}

	public int getLastInsertedCustomerId() throws SQLException {
		PreparedStatement sql = null;
		String query = "select MAX(id) as customerid from customer";
		sql = connection.prepareStatement(query);
		ResultSet res = sql.executeQuery();
			res.next();
			int lastid = res.getInt("customerid");
			
			res.close();
			sql.close();
			
			return lastid;
		
	}
	
	public int getRoomtypeid(String roomtype) throws SQLException{
		PreparedStatement sql = null;
		String query = "select id from roomtype where name = ?";
		sql = connection.prepareStatement(query);
		sql.setString(1, roomtype);
		ResultSet res = sql.executeQuery();
		int roomid = res.getInt("id");
		
		res.close();
		sql.close();
		
		return roomid;
	}
	
	public void makeBooking(int hotelid, int roomtypeid, Date checkin, Date checkout,
			String firstname, String lastname, String generatepin, String uniquestring) throws SQLException{
		PreparedStatement sql = null;
		String query = "insert into customerbooking (default, ? , ? , ? , ? , ? , ? , ? , ? )";
		sql = connection.prepareStatement(query);
		sql.setInt(1, hotelid);
		sql.setInt(2, roomtypeid);
		sql.setDate(3, checkin);
		sql.setDate(4, checkout);
		sql.setString(5, firstname);
		sql.setString(6, lastname);
		sql.setString(7, generatepin);
		sql.setString(8, uniquestring);
		ResultSet res = sql.executeQuery();
	
		res.close();
		sql.close();
		
	}
	
	public OverallHotelsDTO getHotelTotalOccupancy(int hotelid) throws SQLException{
		OverallHotelsDTO ownerHotel = null;
		PreparedStatement sql = null;
		
	//--number of booked rooms for each hotel		
		sql = connection.prepareStatement("select count(rt.name) as booked from roomavailability ra"
											+ " join roomtype rt on ra.roomtypeid = rt.id"
											+ " join customerbooking cb on (cb.id = ra.customerbookingid)"
											+ " join hotel h on (cb.hotelid = h.id)"
											+ " where h.id = ?;");
		sql.setInt(1, hotelid);
		ResultSet resbooked = sql.executeQuery();
		
	//--number of total rooms for each hotel
		sql = connection.prepareStatement("select count(r.id) as total from room r " +
											" join hotel h on h.id = r.hotelid " +
											" where h.id = ?;");
		sql.setInt(1, hotelid);
		ResultSet resavailable = sql.executeQuery();
	
	//--get hotel name
		sql = connection.prepareStatement("select name from hotel" +
											" where id = ?;");
		sql.setInt(1, hotelid);
		ResultSet hotelname = sql.executeQuery();
		
		int availum = resavailable.getInt("total");
		int booknum = resbooked.getInt("booked");
		String name = hotelname.getString("name");
		int available = availum - booknum;
		
		ownerHotel = new OverallHotelsDTO(name, booknum, available);
		
		sql.close();
		hotelname.close();
		resbooked.close();
		resavailable.close();
		
		return ownerHotel;
	}
	
	public StaffDTO getStaff(String username, String password) throws SQLException{
		StaffDTO staff = null;
		PreparedStatement sql = null;
		//-- get everything in staff table
		sql = connection.prepareStatement("select firstname, lastname, password, access " +
											" from staff" +
											" where firstname = ?" +
											" and password = ?;");
		sql.setString(1, username);
		sql.setString(2, password);
		ResultSet staffinfo = sql.executeQuery();
		
		String fname = staffinfo.getString("firstname");
		String lname = staffinfo.getString("lastname");
		String pw = staffinfo.getString("password");
		String access = staffinfo.getString("access");
		int hotelid = staffinfo.getInt("hotelid");
		
		staff = new StaffDTO(fname, lname, pw, access, hotelid);
		
		staffinfo.close();
		sql.close();
		
		return staff;
	}
	
	public Date getCheckInDate(String pin) throws SQLException{
		Date date = null;
		
		PreparedStatement sql = null;

		sql = connection.prepareStatement("select startdate from customerbooking");
		sql.setString(1, pin);
		ResultSet staffinfo = sql.executeQuery();
		
		date = staffinfo.getDate("startdate");
		
		staffinfo.close();
		sql.close();
		
		return date;
	}
	
	public ArrayList<BookingDetailsDTO> getBookingDetails(String pin) throws SQLException{
		ArrayList<BookingDetailsDTO> bookingdetails = new ArrayList<BookingDetailsDTO>();
		
		PreparedStatement sql = null;
		sql = connection.prepareStatement("select h.name as hotelname, rt.name as roomtype,"
				+ " cb.startdate, cb.enddate, cb.firstname, cb.lastname from customerbooking cb"
				+ " join roomtype rt on rt.id = cb.roomtypeid"
				+ " join hotel h on h.id = cb.hotelid"
				+ " where cb.pin = ?"
				+ " group by  h.name, rt.name, cb.startdate, cb.enddate, cb.firstname, cb.lastname");
		
		sql.setString(1,pin);
		ResultSet res = sql.executeQuery();
		
		while(res.next()){
			String hotelname = res.getString("hotelname");
			String roomtype = res.getString("roomtype");
			Date startdate = res.getDate("startdate");
			Date enddate = res.getDate("enddate");
			String firstname = res.getString("firstname");
			String lastname = res.getString("lastname");
			
			BookingDetailsDTO details = new BookingDetailsDTO(hotelname, roomtype, startdate, enddate, firstname, lastname);
			bookingdetails.add(details);
			
		}
		
		res.close();
		sql.close();
		
		return bookingdetails;
	}
	
	public void applyDiscount(int roomtypeid, Date start, Date end, int discounted, int hotelid) throws SQLException{
		PreparedStatement sql = null;
		sql = connection.prepareStatement("insert into discount (default, ?, ?, ?, ?, ?);");
		sql.setInt(1, roomtypeid);
		sql.setDate(2, start);
		sql.setDate(3, end);
		sql.setInt(4, discounted);
		sql.setInt(5, hotelid);
		ResultSet res = sql.executeQuery();
	}
	
	public int getSinglehotelId(String name, String city) throws SQLException {
		int hotelid = 0;

		PreparedStatement sql = null;
		String query = "select id from hotel " +
				" where name = ?" +
				" and city = ?;";
		sql = connection.prepareStatement(query);
		sql.setString(1, name);
		sql.setString(2, city);

		ResultSet res = sql.executeQuery();
		hotelid = res.getInt("id");
		
		res.close();
		sql.close();

		return hotelid;	
	}
	
}
