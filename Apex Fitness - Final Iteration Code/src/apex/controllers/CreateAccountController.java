package apex.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import apex.util.CustomerDAO;
import apex.util.Authenticator;

/**
 * Create Account controller
 * TODO: Comments
 * @author Caleb Dilworth
 */
@WebServlet("/CreateAccountController")
public class CreateAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;

		//Get parameters from account creation page
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean reminder = Boolean.parseBoolean(request.getParameter("reminder"));
		String[] goals = request.getParameterValues("goal"); //TODO
		int weight = Integer.parseInt(request.getParameter("weight"));
		int height = Integer.parseInt(request.getParameter("height"));
		
		// Create hash for the password
		String hashPassword = Authenticator.Encrypt(password);
		
		// Create a new CustomerDAO object
		CustomerDAO dao = new CustomerDAO();
		boolean result = false;
		
		// Test if the customer choose one or two goals
		String goal = "WLMG";
		
		if (goals != null) {
			int numberOfGoals = goals.length;
			
			if (numberOfGoals == 2) {
				result = dao.createCustomerDB(firstName, lastName, age, email, hashPassword, reminder, goal, weight, height);
			} else {
				result = dao.createCustomerDB(firstName, lastName, age, email, hashPassword, reminder, goals[0], weight, height);
			}
		} else {
				result = dao.createCustomerDB(firstName, lastName, age, email, hashPassword, reminder, "NONE", weight, height);
		}
		
	
		if (result) { //creating was successful

			//Send to login page
			rd = request.getRequestDispatcher("login.jsp");
			PrintWriter out = response.getWriter();
			out.print("<font color = black> Account succesfully created! </font>");
			rd.include(request, response);

		} else { //Else stay on page, write error
			rd = getServletContext().getRequestDispatcher("/createNewAccount.jsp");
			PrintWriter out = response.getWriter();
			out.print("<font color = red> Error: Could not create account. Email may already be in use </font>");
			rd.include(request, response);
		}

	}
	
}