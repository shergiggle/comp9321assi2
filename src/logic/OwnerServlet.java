package logic;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exception.InvalidActionException;

import jdbc.DAO;

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
			String user = request.getParameter("userid");
			String pw = request.getParameter("password");
			
			forwardPage = "ownerView.jsp";
		}
		if(action.equals("discount")){
			forwardPage = "ownerDiscount.jsp";
		}
		
		if(action.equals("applydiscount")){
			forwardPage = "ownerConfirm.jsp";
		}
		
		if(action.equals("confirm")){
			message = "Discount Applied";
			request.setAttribute("message", message);
			forwardPage = "ownerView.jsp";
		}
		
		if(action.equals("cancel")){
			forwardPage = "ownerView.jsp";
		}
		
	}

}
