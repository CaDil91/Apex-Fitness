package apex.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import apex.models.*;
import apex.util.*;

/**
 * This Class/Servlet is used as an interface between what the user see's and the data being used
 * This classes function is to attempt log in a user to the system, if verified/authenticated the customer is created and logged into the system
 * 
 * @author Caleb Dilworth
 * ICS 499 - Apex Fitness
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO = new CustomerDAO();

	/**
	 * Overridden doPost method called by login.jsp
	 */
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		Authenticator authenticator = new Authenticator(); //Local to be thread safe web app
		RequestDispatcher rd;
		String message = null;
		
		//Get form inputs
		String email = request.getParameter("email");
		String pw = request.getParameter("password");
		
		String hashed = authenticator.AuthenticateExisting(email, pw);

		//Get and Create customer object using Data Access Object
		Customer customer = customerDAO.getCustomerDB(email, hashed);

		//If customer was found
		if (customer != null) { 
			rd = request.getRequestDispatcher("home.jsp"); //jsp to send to
			
			customer.setEquipmentDB(customerDAO); //Initially set equipment list
			customer.setWorkoutsDB(customerDAO); //Initially set workout ids

			//Set user session attribute
			HttpSession session = request.getSession();
			session.setAttribute("customer", customer);

			//Check if customer has equipment
			if (customer.getEquipmentList().isEmpty()){			
				message = "<font color = black> Set your personal equipment in the equipment page! </font>";			
			}
			

		} else { //Else stay on page, write error
			rd = request.getRequestDispatcher("login.jsp"); //jsp to send to
			message = "<font color = red> Either username and/or password is incorrect </font>"; //Error message
		}
		
		request.setAttribute("message", message);
		response.setHeader("Cache-Control", "no-cache");
		rd.include(request, response); //Send to next page
		
		
	}//End doPost method

}