package logic;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exception.InvalidActionException;
import Exception.ServiceLocatorException;
import jdbc.DAO;
import jdbc.HotelDTO;
import jdbc.RoomPriceDTO;
import jdbc.SearchDTO;

/**
 * Servlet implementation class ConsumerServlet
 */
@WebServlet("/consumer")
public class ConsumerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DAO dao;
    private Connection connection;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    static Logger logger = Logger.getLogger(ConsumerServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsumerServlet() throws ServletException{
        super();
		dao = new DAO();
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
		String action = request.getParameter("action");
		Date checkin = null;
		Date checkout = null;
		String city = null;
		String hotel = null;
		ArrayList<HotelDTO> hotels = new ArrayList<HotelDTO>();
		String[] roomtypes = null;
		
		if(action.equals(null)){
			try {
				throw new InvalidActionException();
			} catch (InvalidActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(action.equals("search")){
			String checkindate = request.getParameter("startday");
			String checkinmonth = request.getParameter("startmonth");
			String checkinyear = request.getParameter("startyear");
			
			String checkoutdate = request.getParameter("endday");
			String checkoutmonth = request.getParameter("endmonth");
			String checkoutyear = request.getParameter("endyear");
			
			city = request.getParameter("cityChosen");
			
			String maxprice = request.getParameter("maxprice");
			int price = Integer.parseInt(maxprice);
			
			java.sql.Date checkinsqldate = null;
			try {
				checkinsqldate = stringtosqldate(checkindate, checkinmonth, checkinyear);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			java.sql.Date checkoutsqldate = null;
			try {
				checkoutsqldate = stringtosqldate(checkoutdate, checkoutmonth, checkoutyear);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//needs a select statement here
			ArrayList<SearchDTO> searchResults = new ArrayList<SearchDTO>();
			try {
				searchResults = dao.getSearchResults(checkinsqldate, checkoutsqldate, city, price);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 checkin = checkinsqldate;
			 checkout = checkoutsqldate;
			
			//send the info
			request.setAttribute("checkin", checkinsqldate);
			request.setAttribute("checkout", checkoutsqldate);
			request.setAttribute("city", city);
			request.setAttribute("resultlist", searchResults);
			
			//send forwardpage
			forwardPage = "consumerSearch.jsp";
		}
		if(action.equals("confirm")){
			
			roomtypes = request.getParameterValues("selectedrooms");
			city = request.getParameter("city");
			hotels = new ArrayList<HotelDTO>();
			ArrayList<RoomPriceDTO> roomprice = new ArrayList<RoomPriceDTO>();
			//foreach chosen roomtype get price
			
			for(String room : roomtypes){
				try {
					RoomPriceDTO tempPrice = dao.getRoomTypePrice(room);
					roomprice.add(tempPrice);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			try {
				hotels = dao.getHotelsinCity(city);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			request.setAttribute("cost", roomprice);
			request.setAttribute("city", city);
			request.setAttribute("resultlist", hotels);
			
			forwardPage = "ConsumerConfirm.jsp";
			
		}
		if(action.equals("checkout")){
			// take roomtypes selected, hotelselected, checkin, checkout
			hotel = request.getParameter("selectedHotel");
			forwardPage = "ConsumerCheckout.jsp";
		}
		if(action.equals("complete")){
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
			int hotelid = 0;
			String generatepin = PINGenerator.generate();
			// take roomtypes selected, hotelselected, checkin, checkout
			//get the hotel id
			try {
				hotelid = dao.getHotelId(hotel);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//make customer
			try {
				dao.makeCustomer(firstname, lastname);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				int customerid = dao.getLastInsertedCustomerId();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			for(String room : roomtypes){
				int roomtypeid = 0;
				try {
					roomtypeid = dao.getRoomtypeid(room);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					dao.makeBooking(hotelid, roomtypeid, checkin, checkout, firstname, lastname, generatepin, generatepin );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//send email
			String fromAddress = "9321hotel@gmail.com";
			String toAddress = email;
			String subject = request.getParameter("name");
			StringBuffer mailBody = new StringBuffer();
			URL url = new URL(request.getScheme(),
					request.getServerName(), request.getServerPort(),
					request.getContextPath());
			mailBody.append("pin: " + generatepin + ", link: "
					+ url.toExternalForm() + "/BookingServlet/?pin="
					+ generatepin);
			MailSender.sendEmail(fromAddress, toAddress, subject,
					mailBody.toString());

			
			
		}
		if(action.equals("reset")){
			
			hotel = null;
			checkin = null;
			checkout = null;
			city = null;
			hotels = new ArrayList<HotelDTO>();
			roomtypes = null;
			forwardPage = "consumerMain.jsp";
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
		dispatcher.forward(request, response);
		
	}
	
	public java.sql.Date stringtosqldate(String day, String month, String year) throws ParseException{
		String startDate = day+"-"+month+"-"+year;
		
		java.util.Date date = formatter.parse(startDate);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
		
		return sqlDate;
		
	}

}
