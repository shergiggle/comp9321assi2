package logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exception.InvalidActionException;
import Exception.ServiceLocatorException;
import jdbc.DAO;
import jdbc.SearchDTO;

/**
 * Servlet implementation class ConsumerServlet
 */
@WebServlet("/ConsumerServlet")
public class ConsumerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DAO dao;
    private Connection connection;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    static Logger logger = Logger.getLogger(ConsumerServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsumerServlet() {
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
			
			String city = request.getParameter("cityChosen");
			
			String maxprice = request.getParameter("maxprice");
			
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
				searchResults = dao.getSearchResults(checkinsqldate, checkoutsqldate, city, maxprice);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//send the info
			request.setAttribute("resultlist", searchResults);
			
			//send forwardpage
			forwardPage = "consumerSearch.jsp";
		}
		if(action.equals("confirm")){
			
			
		}
		if(action.equals("checkout")){
			//display selected rooms
			//process creditcard and mailsender stuff
			
		}
		if(action.equals("complete")){
			//sendmail
			
		}
		if(action.equals("reset")){
			
			
		}
		
	}
	
	public java.sql.Date stringtosqldate(String day, String month, String year) throws ParseException{
		String startDate = day+"-"+month+"-"+year;
		
		java.util.Date date = formatter.parse(startDate);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
		
		return sqlDate;
		
	}

}
