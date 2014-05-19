package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
		
		if(action.equals("Logout")){
			 this.staffhotelid = 0;
			 message = "Logout Successful";
			 request.setAttribute("message", message);
			 forwardPage = "receptionLogin.jsp";
		}
		else if(action.equals("Cancel")){
			
			// Shows the stuff on the reception vie page
			ArrayList<RoomDTO> roomavailable = new ArrayList<RoomDTO>();
			ArrayList<BookingDetailsDTO> bookingavailable = new ArrayList<BookingDetailsDTO>();
			try {
				roomavailable = dao.getRoomavailableinHotel(staffhotelid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// -- Show list of all bookings for the day
			try {
				bookingavailable = dao.getBookingAvailable(staffhotelid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
							
			request.setAttribute("roomsOccupied", roomavailable);
			request.setAttribute("bookingsStillProcess", bookingavailable);
			
			forwardPage = "receptionView.jsp";
		}
		
		else if(action.equals("login")){
			
			ArrayList<RoomDTO> roomavailable = new ArrayList<RoomDTO>();
			ArrayList<BookingDetailsDTO> bookingavailable = new ArrayList<BookingDetailsDTO>();
			
			//--Check that the user has reception access
			String user = request.getParameter("userid");
			String pw = request.getParameter("password");
			StaffDTO staff = null;
			System.out.println(user);
			System.out.println(pw);
			
			try {
				System.out.println("hello");
				System.out.println(dao != null);
				staff = dao.getStaff(user, pw);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (staff == null){
				message = "Invalid Login Details";
				request.setAttribute("message", message);
				forwardPage = "receptionLogin.jsp";
			}
			else if((staff.getAccess()).equals("reception")){
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
			}
		}
		
		else if(action.equals("checkin")){
			String bookid = request.getParameter("bookingid");
			if (bookid != null){
				int bookingid = Integer.parseInt(bookid);
				ArrayList<RoomDTO> roomsfree = null;
				//System.out.println(bookingid);
				// -- generate a list of all available rooms for bookingid
				try {
					roomsfree = dao.getRoomsAvailableforBookingid(bookingid, this.staffhotelid);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.setAttribute("bookingid", bookingid);
				request.setAttribute("availableRooms", roomsfree);
				forwardPage = "receptionCheckin.jsp";
			} else {
				
				// Shows the stuff on the reception view page
				ArrayList<RoomDTO> roomavailable = new ArrayList<RoomDTO>();
				ArrayList<BookingDetailsDTO> bookingavailable = new ArrayList<BookingDetailsDTO>();
				try {
					roomavailable = dao.getRoomavailableinHotel(this.staffhotelid);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// -- Show list of all bookings for the day
				try {
					bookingavailable = dao.getBookingAvailable(this.staffhotelid);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				message = "Please select a booking";
				request.setAttribute("message", message);
				request.setAttribute("roomsOccupied", roomavailable);
				request.setAttribute("bookingsStillProcess", bookingavailable);
				forwardPage = "receptionView.jsp";
			}

		}
		
		else if(action.equals("checkout")){
			forwardPage = "receptionCheckout.jsp";
		}
		
		else if(action.equals("checkinroom")){
			int roomnum = Integer.parseInt(request.getParameter("checkinroom"));
			int bookingid = Integer.parseInt(request.getParameter("bookingid"));
			ArrayList<RoomDTO> roomavailable = new ArrayList<RoomDTO>();
			ArrayList<BookingDetailsDTO> bookingavailable = new ArrayList<BookingDetailsDTO>();
			
			try {
				dao.applyCheckin(roomnum, bookingid, this.staffhotelid);
				message = "Check-In Complete";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message = "Could Not Check-In";
			}
			
			try {
				roomavailable = dao.getRoomavailableinHotel(staffhotelid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// -- Show list of all bookings for the day
			try {
				bookingavailable = dao.getBookingAvailable(staffhotelid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
							
			request.setAttribute("roomsOccupied", roomavailable);
			request.setAttribute("bookingsStillProcess", bookingavailable);
			request.setAttribute("message", message);
			forwardPage = "receptionView.jsp";
		}
		
		else if(action.equals("checkoutroom")){
			RoomDTO room = null;
			int roomnumber = Integer.parseInt(request.getParameter("checkoutroom"));
			// Check that the room that is being checked out is occupied
			try {
				room = dao.checkRoomAvailability(roomnumber, this.staffhotelid);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (room.getAvailability().equals("occupied")){
				//update the roomnumber in the room table to available	
				try {
					dao.applyCheckout(roomnumber, this.staffhotelid);
					message = "Room Check-Out Successful";
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					message = "Room Check-Out Not Successful";
				}
			} else {
				message = "Room was not occupied";
			}
			
			// Shows the stuff on the reception view page
			ArrayList<RoomDTO> roomavailable = new ArrayList<RoomDTO>();
			ArrayList<BookingDetailsDTO> bookingavailable = new ArrayList<BookingDetailsDTO>();
			try {
				roomavailable = dao.getRoomavailableinHotel(staffhotelid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// -- Show list of all bookings for the day
			try {
				bookingavailable = dao.getBookingAvailable(staffhotelid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
							
			request.setAttribute("message", message);
			request.setAttribute("roomsOccupied", roomavailable);
			request.setAttribute("bookingsStillProcess", bookingavailable);
			
			forwardPage = "receptionView.jsp";
		}
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
			dispatcher.forward(request, response);
	}
}
