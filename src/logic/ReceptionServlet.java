package logic;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exception.InvalidActionException;

/**
 * Servlet implementation class ReceptionServlet
 */
@WebServlet("/reception")
public class ReceptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		
		if(action.equals(null)){
			try {
				throw new InvalidActionException();
			} catch (InvalidActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(action.equals("login")){
			forwardPage = "receptionView.jsp";
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
		
	}

}
