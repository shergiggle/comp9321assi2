package logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.BookingDetailsDTO;
import jdbc.DAO;

/**
 * Servlet implementation class BookingServlet
 */
@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DAO dao;
    private Connection connection;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    static Logger logger = Logger.getLogger(ConsumerServlet.class.getName());
    private String pin = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingServlet() {
    	super();
		dao = new DAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pin = request.getParameter("pin");
		doPost(request, response);
	}
	
	public static int hoursFromNow(Date date) {
		long diff = date.getTime() - System.currentTimeMillis();
		return (int) TimeUnit.MILLISECONDS.toHours(diff);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPage = "";
		String action = request.getParameter("action");
		ArrayList<BookingDetailsDTO> bookingdetails = new ArrayList<BookingDetailsDTO>();
		//display booking details where pin = pin
		if(pin != null){
			
			Date checkindate = null;
			try {
				checkindate = dao.getCheckInDate(pin);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(hoursFromNow(checkindate)<=48){
				request.setAttribute("message", "You cannot access this page within 48 hours of your check in time");
				forwardPage = "error.jsp";
			}
			else{
				try {
					bookingdetails = dao.getBookingDetails(pin);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.setAttribute("details", bookingdetails);
				
			}
			
		}
		if(action.equals("edit")){
			//display original
			request.setAttribute("details", bookingdetails);
			forwardPage = "bookingEdit.jsp";
		
		}
		if(action.equals("confirm")){
			//update the bookingdetails
			
			
		}
		if(action.equals("reset")){
			//redirect to booking
			forwardPage = "booking.jsp";
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
		dispatcher.forward(request, response);
		
	}
	
}
