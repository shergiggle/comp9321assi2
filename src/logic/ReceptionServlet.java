package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.*;
import Exception.*;

/**
 * Servlet implementation class ReceptionServlet
 */
@WebServlet("/reception")
public class ReceptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DAO dao;
    
    private int staffhotelid;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceptionServlet() {
        super();
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
		String forwardPage = "";
		String action = request.getParameter("action");
		String message = "";
		
		if(action.equals(null)){
			try {
				throw new InvalidActionException();
			} catch (InvalidActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(action.equals("login")){
			//--Check that the user has reception access
			String user = request.getParameter("userid");
			String pw = request.getParameter("password");
			StaffDTO staff = null;
			ArrayList<RoomDTO> roomavailable = new ArrayList<RoomDTO>();
			ArrayList<BookingDetailsDTO> bookingavailable = new ArrayList<BookingDetailsDTO>();
			try {
				//System.out.println("hello");
				staff = dao.getStaff(user, pw);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if((staff.getAccess()).equals("reception")){
				this.staffhotelid = staff.getHotelid();
				// -- Show list of all available rooms for the day at the hotel
				try {
					roomavailable = dao.getRoomavailableinHotel(staffhotelid);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// -- Show list of all bookings for the day
				try {
					bookingavailable = dao.getBookingAvailable(staffhotelid);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.setAttribute("roomsOccupied", roomavailable);
				request.setAttribute("bookingsStillProcess", bookingavailable);
				forwardPage = "receptionView.jsp";
			} else {
				message = "Invalid Login Details";
				request.setAttribute("message", message);
				forwardPage = "receptionLogin.jsp";
			}
		}
		
		if(action.equals("checkin")){
			forwardPage = "receptionCheckin.jsp";
		}
		
		if(action.equals("checkout")){
			forwardPage = "receptionCheckout.jsp";
		}
		
		if(action.equals("checkinroom")){
			forwardPage = "receptionView.jsp";
		}
		
		if(action.equals("checkoutroom")){
			forwardPage = "receptionView.jsp";
		}
		
		if(action.equals("cancel")){
			forwardPage = "receptionView.jsp";
		}
		 if(action.equals("logout")){
			 this.staffhotelid = 0;
			 message = "Logout Successful";
			 request.setAttribute("message", message);
			 forwardPage = "receptionLogin.jsp";
		 }
	}

}
