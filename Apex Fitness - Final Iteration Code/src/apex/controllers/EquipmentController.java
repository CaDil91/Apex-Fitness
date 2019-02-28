package apex.controllers;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import apex.models.*;

@WebServlet("/EquipmentController")
public class EquipmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
	private static java.sql.PreparedStatement prepStmt;

	/**
	 * Overridden doGet method. When called this redirects to the Equipment page
	 * TODO: Don't get session data in doGet method? Security?
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Get current users equipment
		HttpSession session = request.getSession();
		Customer myCustomer = (Customer) session.getAttribute("customer");		
		ArrayList<Equipment> custEquipment = myCustomer.getEquipmentList();
		request.setAttribute("custEq", custEquipment); //Set for jsp page

		RequestDispatcher rd;
		rd = request.getRequestDispatcher("equipment.jsp");
		rd.forward(request, response);
	}

	/**
	 * Overridden doPost method. Used to add or remove equipment from the Customers equipment in the current session
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Get customer session object
		HttpSession session = request.getSession();
		Customer cust = (Customer) session.getAttribute("customer");
		String email = cust.getEmail();
		String message = null;

		String action = request.getParameter("action"); //Get which button press called post method

		//Add or remove item based on button press
		if (action.equals("add")){ //Add item to user list

			String item = (request.getParameter("addItem"));
			
			//String item = WordUtils.capitalizeFully(request.getParameter("addItem"));
			
			//If item is added successfully
			if(addIt(item, email)){
				Equipment eq = new Equipment(item, "description TO BE FIXED"); //TODO get description? Or do we even bother storing that?
				cust.addEquipment(eq);
			} else {
				message = "<font color = red> Equipment could not be added! Please try again </font>";
			}

		} else { //Remove item from user list

			String item = request.getParameter("removeItem"); //Item to remove
			
			//If item is removed successfully
			if (removeIt(item, email)){
				cust.removeEquipment(item);
			} else {
				message = "<font color = red> Equipment could not be removed! Please try again </font>";
			}
		}
		
		ArrayList<Equipment> custEquipment = cust.getEquipmentList(); //Get current users equipment to refresh page
		request.setAttribute("message", message);
		request.setAttribute("custEq", custEquipment); //Set for jsp page

		//Forward beck to equipment page
		RequestDispatcher rd;
		rd = request.getRequestDispatcher("equipment.jsp");
		rd.include(request, response);

	}

	/**
	 * Adds item into the customers equipment
	 * @param addedVar
	 * @param email 
	 * @return true iff item was successfully added to the database
	 * TODO: Use customer DAO
	 */
	private boolean addIt(String equipment, String email) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");

			String query = "INSERT INTO customerEquipment values (?, ?)";
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, email);
			prepStmt.setString(2, equipment);
			prepStmt.executeUpdate();			
			return true;

		} catch (Exception ex) { 
			ex.printStackTrace();
		}

		return false;
	}

	/**
	 * Removes an item from the customer table
	 * @param removedVar. Item to remove
	 * @param email. Email of customer
	 * @return true iff item was successfully removed
	 * TODO: Use customer DAO
	 */
	private boolean removeIt (String removedVar, String email) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");

			String query = "DELETE from customerEquipment where eq_name = ? and email = ?";
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, removedVar);
			prepStmt.setString(2, email);
			int result = prepStmt.executeUpdate();
			if (result == 1) return true; //If update query changed the table, return true

		} catch (Exception ex) { 
			ex.printStackTrace();
		}
		
		return false; //Item was not removed, or DB exception occurred
	}
}