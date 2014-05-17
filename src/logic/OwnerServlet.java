package logic;

import java.io.IOException;
import java.sql.Connection;
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
    private Connection connection;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String forwardPage = "";
		String message = "";
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
			staff = dao.getStaff(user, pw);
			if(staff.getAccess().equals("owner")){
				//--recounts the total number of rooms booked and available
				ArrayList<OverallHotelsDTO> availableList = new ArrayList<OverallHotelsDTO>();
				List<HotelDTO> allHotels = new ArrayList<HotelDTO>();
				OverallHotelsDTO eachHotel = null;
				int hotelid = 0;
				allHotels = dao.getAllHotels();
				
				for(HotelDTO hotel : allHotels){
					try {
						hotelid = dao.getHotelId(hotel.getName());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						eachHotel = dao.getHotelTotalOccupancy(hotelid);
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
			String city = request.getParameter("city");
			String hotel = request.getParameter("hotel");
			String type = request.getParameter("type");
			
			String startdate = request.getParameter("startdate");
			String startmonth = request.getParameter("startmonth");
			String startyear = request.getParameter("startyear");
			
			String enddate = request.getParameter("enddate");
			String endmonth = request.getParameter("endmonth");
			String endyear = request.getParameter("endyear");
			
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
			
			//--passing all the data to the next page
			request.setAttribute("city", city);
			request.setAttribute("hotelname", hotel);
			request.setAttribute("type", type);
			request.setAttribute("startdate", startingdate);
			request.setAttribute("enddate", endingdate);
			forwardPage = "ownerConfirm.jsp";
		}
		
		if(action.equals("confirm")){
			//--presents a success message
			message = "Discount Applied";
			//-- insert the new discount
			
			
			
			//--recount the total number of rooms available and booked
			ArrayList<OverallHotelsDTO> availableList = new ArrayList<OverallHotelsDTO>();
			List<HotelDTO> allHotels = new ArrayList<HotelDTO>();
			OverallHotelsDTO eachHotel = null;
			int hotelid = 0;
			allHotels = dao.getAllHotels();
			
			for(HotelDTO hotel : allHotels){
				try {
					hotelid = dao.getHotelId(hotel.getName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					eachHotel = dao.getHotelTotalOccupancy(hotelid);
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
			int hotelid = 0;
			allHotels = dao.getAllHotels();
			
			for(HotelDTO hotel : allHotels){
				try {
					hotelid = dao.getHotelId(hotel.getName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					eachHotel = dao.getHotelTotalOccupancy(hotelid);
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

}
