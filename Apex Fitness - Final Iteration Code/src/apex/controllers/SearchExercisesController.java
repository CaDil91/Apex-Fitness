package apex.controllers;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import apex.models.*;

/**
 * TODO: Comments
 * @author Caleb Dilworth
 * ICS 499 - Apex Fitness Project
 */
@WebServlet("/SearchExercisesController")
public class SearchExercisesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
	private static ResultSet rs;
	private static Statement stmt;

	/**
	 * Overridden doPost method called by searchExercises.jsp
	 * Method searches and sets exercises to display for the Customer
	 * If no exercises are found the Customer is notified and stays on the same web page
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		String message;
		
		//Get Customer session object
		HttpSession session = request.getSession();
		Customer cust = (Customer) session.getAttribute("customer"); //Get Customer object from current session
		String goal = cust.getGoal();
		
		System.out.println("Goal is " + goal);
		
		// Get Customers equipment
		ArrayList<Equipment> custEq = cust.getEquipmentList(); 
		
		//Get equipment names as String
		ArrayList<String> custEqNames = new ArrayList<String>();		
		for (Iterator<Equipment> iterator = custEq.iterator(); iterator.hasNext();) {
			Equipment equipment = iterator.next();
			custEqNames.add(equipment.getEquipmentName());
		}
		
		//If the Customer has no equipment set, inform them
		if (custEq.isEmpty()) {
			message = "<font color = Red> You have no equipment set. Set your personal equipment in the equipment page! </font>";
			request.setAttribute("message", message);
			rd = request.getRequestDispatcher("searchExercises.jsp"); //jsp to send to
		} else {
			

			String[] bodyTargets = request.getParameterValues("checkboxBodyTargets"); //Get body target inputs	
			ArrayList<String> bodyTargetStrings = getBodyTargetStrings(bodyTargets); //Get the full string of the body target
			
			
			ArrayList<String> exercises = getExercises(bodyTargets, custEqNames, goal); //Search DB for exercises
			ArrayList<String> recommended = getRecommended(bodyTargets, custEqNames, goal);
			
			//Check getExercises returned results
			if ((!exercises.isEmpty()) || (!recommended.isEmpty())) {
				request.setAttribute("exercises", exercises); //Set attribute for next page
				request.setAttribute("recommended", recommended); //Set attribute for next page
				request.setAttribute("bodyTargetStrings", bodyTargetStrings); //Set attribute for next page	
				rd = request.getRequestDispatcher("createWorkout.jsp"); //jsp to send to
			} else {
				message = "<font color = Red> No exercises found for this search! </font>";
				request.setAttribute("message", message);
				rd = request.getRequestDispatcher("searchExercises.jsp"); //jsp to send to
			}			
		}		
		
		rd.include(request, response); //Send to next page
		
	}//End doPost method

	/**
	 * Retrieves exercises from the DB using the given bodyTarget filters, and user equipment filters
	 * @param bodyTargets. Body target filter
	 * @param custEqNames. Equipment filter
	 * @return valid exercises. Must return initialized ArrayList, not null
	 */
	private ArrayList<String> getExercises(String[] bodyTargets, ArrayList<String> custEqNames, String goal) {
		ArrayList<String> rExercises = new ArrayList<String>(); //Holds exercises being returned
		String query;
		
		try {
			//Connect to DB
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");
			stmt = con.createStatement();
			
			//Get exercises for each body target filtering by equipment
			for (int i = 0; i < bodyTargets.length; i++) {

				//Create query
				if (goal.equals("WLMG")) {
					query = "SELECT ex_name, eq_name, goal FROM exercises WHERE body_part = '" + bodyTargets[i] + "' AND goal IS NULL";
				} else {
					query = "SELECT ex_name, eq_name, goal FROM exercises WHERE body_part = '" + bodyTargets[i] + "' AND goal <> '" + goal + "'" +
								"union SELECT ex_name, eq_name, goal FROM exercises WHERE body_part = '" + bodyTargets[i] + "' AND goal is null";					
				}
				
				rs = stmt.executeQuery(query);
				
				//For each result from result set
				while (rs.next()){
					if (custEqNames.contains(rs.getString("eq_name"))) {
						rExercises.add(rs.getString("ex_name")); //If exercise uses given equipment add to the return list
					}					
				}
				System.out.print("Not recommended: " + rExercises); //TODO DELETE
			}//End for loop


		} catch (Exception ex) { 
			ex.printStackTrace();
		}

		return rExercises;
		
	}//End getExercises method  
	
	
	/**
	 * Retrieves exercises from the DB using the given bodyTarget filters, user equipment filters, and user goal
	 * @param bodyTargets. Body target filter
	 * @param custEqNames. Equipment filter
	 * @return valid exercises. Must return initialized ArrayList, not null
	 */
	private ArrayList<String> getRecommended(String[] bodyTargets, ArrayList<String> custEqNames, String goal) {
		ArrayList<String> rExercises = new ArrayList<String>(); //Holds exercises being returned
		String query;
		
		try {
			//Connect to DB
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");		
			
			//Get exercises for each body target filtering by equipment
			for (int i = 0; i < bodyTargets.length; i++) {
				
				//Create query
				if (goal.equals("WLMG")) {
					query = "SELECT ex_name, eq_name, goal FROM exercises WHERE body_part = '" + bodyTargets[i] + "' AND goal IS NOT NULL";
				} else {
					query = "SELECT ex_name, eq_name, goal FROM exercises WHERE body_part = '" + bodyTargets[i] + "' AND goal = '" + goal + "'";					
				}
				
				rs = stmt.executeQuery(query);
				
				
				//For each result from result set
				while (rs.next()){
					if (custEqNames.contains(rs.getString("eq_name"))) {
						rExercises.add(rs.getString("ex_name")); //If exercise uses given equipment add to the return list
					}					
				}	
				
			}//End for loop

			System.out.print("Recommended: " + rExercises); //TODO DELETE
			
		} catch (Exception ex) { 
			ex.printStackTrace();
		}

		return rExercises;
		
	}//End getRecommended method  
	
	
	
	/**
	 * getBodyTargetString
	 * Converts the shortened contents of the String[] bodyTargets into their full strings.
	 * Then it inserts the full strings into an arrayList which is returned.
	 * 	
	*/
	private ArrayList<String> getBodyTargetStrings (String[] bodyTargets) {
		
		// ArrayList to return
		ArrayList<String> returnString = new ArrayList<String>();
		
		// If the String[] includes a shortened version of a body target
		// include the full string in the ArrayList
		for (int i=0; i < bodyTargets.length; i++) {
			if (bodyTargets[i].equals("SH")) {
				returnString.add("Shoulders");
			} else if (bodyTargets[i].equals("CH")) {
				returnString.add("Chest");
			} else if (bodyTargets[i].equals("BA")) {
				returnString.add("Back");
			} else if (bodyTargets[i].equals("BI")) {
				returnString.add("Biceps");
			} else if (bodyTargets[i].equals("TR")) {
				returnString.add("Triceps");
			} else if (bodyTargets[i].equals("LE")) {
				returnString.add("Legs");
			} else if (bodyTargets[i].equals("AB")) {
				returnString.add("Abs");
			} else if (bodyTargets[i].equals("CA")) {
				returnString.add("Cardio");
			}
		} // End For loop
		
		// Return ArrayList
		return returnString;
		
	} // End getBodyTargetStrings method

}