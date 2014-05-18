package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exception.*;
import jdbc.*;

/**
 * Servlet implementation class OwnerServlet
 */
@WebServlet("/owner")
public class OwnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DAO dao;
    
	private Date start = null;
	private Date end = null;
	private int discountamount;
	private int hotelid;
	private int roomtypeid;
	
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    static Logger logger = Logger.getLogger(ConsumerServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OwnerServlet() {
        super();
        dao = new DAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String forwardPage = null;
		String message = null;
		
		String action = request.getParameter("action");
		
		if(action.equals(null)){
			try {
				throw new InvalidActionException();
			} catch (InvalidActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(action.equals("login")){
			//--Confirm that the use is the owner
			String user = request.getParameter("userid");
			String pw = request.getParameter("password");
			StaffDTO staff = null;
			try {
				staff = dao.getStaff(user, pw);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(staff.getAccess().equals("owner")){
				//--recounts the total number of rooms booked and available
				ArrayList<OverallHotelsDTO> availableList = new ArrayList<OverallHotelsDTO>();
				List<HotelDTO> allHotels = new ArrayList<HotelDTO>();
				OverallHotelsDTO eachHotel = null;
				allHotels = dao.getAllHotels();
				
				int hotelidcheck = 0;
				for(HotelDTO hotel : allHotels){
					try {
						hotelidcheck = dao.getSinglehotelId(hotel.getName(),hotel.getCity());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						eachHotel = dao.getHotelTotalOccupancy(hotelidcheck);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					availableList.add(eachHotel);
				}
				
				request.setAttribute("availabilityList", availableList);
				forwardPage = "ownerView.jsp";
			} else {
				message = "Invalid Login Details";
				request.setAttribute("message", message);
				forwardPage = "ownerLogin.jsp";
			}
			

		}
		
		if(action.equals("discount")){
			//--no action is needed
			forwardPage = "ownerDiscount.jsp";
		}
		
		if(action.equals("applydiscount")){
			//--obtaining all the data from the page
			String cityname = request.getParameter("city");
			String hotel = request.getParameter("hotel");
			String roomtype = request.getParameter("type");
			
			String startdate = request.getParameter("startdate");
			String startmonth = request.getParameter("startmonth");
			String startyear = request.getParameter("startyear");
			
			String enddate = request.getParameter("enddate");
			String endmonth = request.getParameter("endmonth");
			String endyear = request.getParameter("endyear");
			
			String amount = request.getParameter("amount");
			
			java.sql.Date startingdate = null;
			try {
				startingdate = stringtosqldate(startdate, startmonth, startyear);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			java.sql.Date endingdate = null;
			try{
				endingdate = stringtosqldate(enddate, endmonth, endyear);
			} catch (ParseException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int discount = Integer.parseInt(amount);
			
			//-- Storing a copy of the data for the booking to remember
			try {
				this.hotelid = dao.getSinglehotelId(hotel, cityname);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.roomtypeid = dao.getRoomtypeid(roomtype);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.start = startingdate;
			this.end = endingdate;
			this.discountamount = discount;
			
			//--passing all the data to the next page
			request.setAttribute("city", cityname);
			request.setAttribute("hotelname", hotel);
			request.setAttribute("type", roomtype);
			request.setAttribute("startdate", startingdate);
			request.setAttribute("enddate", endingdate);
			request.setAttribute("amount", discount);
			
			forwardPage = "ownerConfirm.jsp";
		}
		
		if(action.equals("confirm")){
			//-- insert the new discount

			try {
				dao.applyDiscount(this.roomtypeid, this.start, this.end, this.discountamount, this.hotelid);
				// -- success message
				message = "Discount Applied Successful";
				//-- clear the data
				this.hotelid = 0;
				this.roomtypeid = 0;
				this.start = null;
				this.end = null;
				this.discountamount = 0;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				message = "Discount Not Applied";
			}
			
			//--recount the total number of rooms available and booked
			ArrayList<OverallHotelsDTO> availableList = new ArrayList<OverallHotelsDTO>();
			List<HotelDTO> allHotels = new ArrayList<HotelDTO>();
			OverallHotelsDTO eachHotel = null;
			allHotels = dao.getAllHotels();
			
			int hotelidcheck = 0;
			for(HotelDTO hotel : allHotels){
				try {
					hotelidcheck = dao.getSinglehotelId(hotel.getName(), hotel.getCity());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					eachHotel = dao.getHotelTotalOccupancy(hotelidcheck);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				availableList.add(eachHotel);
			}
			
			request.setAttribute("availabilityList", availableList);			
			request.setAttribute("message", message);
			forwardPage = "ownerView.jsp";
		}
		
		if(action.equals("cancel")){
			//--recount the total number of rooms booked and available
			ArrayList<OverallHotelsDTO> availableList = new ArrayList<OverallHotelsDTO>();
			List<HotelDTO> allHotels = new ArrayList<HotelDTO>();
			OverallHotelsDTO eachHotel = null;
			allHotels = dao.getAllHotels();
			
			int hotelidcheck = 0;
			for(HotelDTO hotel : allHotels){
				try {
					hotelidcheck = dao.getSinglehotelId(hotel.getName(), hotel.getCity());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					eachHotel = dao.getHotelTotalOccupancy(hotelidcheck);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				availableList.add(eachHotel);
			}
			
			request.setAttribute("availabilityList", availableList);
			forwardPage = "ownerView.jsp";
		}
		
	}

	private java.sql.Date stringtosqldate(String day, String month, String year) throws ParseException{
		String startDate = day+"-"+month+"-"+year;
		
		java.util.Date date = formatter.parse(startDate);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
		
		return sqlDate;
		
	}
}
