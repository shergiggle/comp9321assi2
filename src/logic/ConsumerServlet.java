package logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exception.InvalidActionException;
import jdbc.DAO;

/**
 * Servlet implementation class ConsumerServlet
 */
@WebServlet("/ConsumerServlet")
public class ConsumerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DAO dao;
    private Connection connection;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
			
			String checkinsqldate = checkindate+"-"+checkinmonth+"-"+checkinyear;
			String checkoutsqldate = checkoutdate+"-"+checkoutmonth+"-"+checkoutyear;
			
			//needs a select statement here
			
		}
		if(action.equals("confirm")){
			
			
		}
		if(action.equals("checkout")){
			
			
		}
		if(action.equals("complete")){
			
			
		}
		if(action.equals("reset")){
			
			
		}
		
	}

}
