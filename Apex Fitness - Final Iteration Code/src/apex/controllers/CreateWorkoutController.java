package apex.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import apex.models.*;

/**
 * Servlet implementation class CreateWorkoutController
 * This controller gets selected exercises from the Customer via the createWorkout.jsp page
 * The controller uses the selected exercises to create a workout for the Customer, and save it in the DB, and the unique id of the workout in the Customer object
 * TODO Comments
 * @author Caleb Dilworth
 */
@WebServlet("/CreateWorkoutController")
public class CreateWorkoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;

	/**
	 * Post method called by createWorkout.jsp
	 * TODO Comments
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		
		String[] selectedExercises = request.getParameterValues("selectedExercises"); //Get selected exercises
		String workoutName = request.getParameter("workoutName"); //Get workout name 
		HttpSession session = request.getSession();
		Customer myCustomer = (Customer) session.getAttribute("customer"); //Get current session Customer
		String message = null; // Error message
		java.sql.Date date = new java.sql.Date(new java.util.Date().getTime()); // Get current date
		
		try {
			//Connect to database
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");

			//Create workout in database
			// TODO add a date field so we can sort by date in the workout planner
			String query = "INSERT INTO workouts VALUES (?, ?, ?, ?, false)"; //Last column = workout complete, which at this creation stage is false
			PreparedStatement prepStmt = con.prepareStatement(query);
			UUID newId = UUID.randomUUID(); //TODO This is local (class variables in Servlets are NOT thread safe), and supposed to be a thread safe method! Do more research
			prepStmt.setString(1, newId.toString());
			prepStmt.setString(2, workoutName);
			prepStmt.setString(3, myCustomer.getEmail());
			prepStmt.setDate(4, date);
			prepStmt.executeUpdate();
			
			//Create exercises for workout
			for (int i = 0; i < selectedExercises.length; i++) {
				query = "INSERT INTO workoutExercises VALUES (?, ?, 0, 0, 0)"; //Last 3 DB columns are sets/reps/weight, which is 0 at this creation stage
				prepStmt = con.prepareStatement(query);
				prepStmt.setString(1, newId.toString()); //Same id as before
				prepStmt.setString(2, selectedExercises[i]); //Current exercise to add. Gotten name pulled from DB previously. No foreign key errors will occur
				prepStmt.executeUpdate();	
			}

			rd = request.getRequestDispatcher("workoutPlanner.jsp"); //jsp to send to
			con.close(); //Close database connection

		} catch (Exception ex) {
			ex.printStackTrace();
			message = "<font color = Red> Somethign went wrong! Please try again in a few minutes... </font>";
			request.setAttribute("message", message);
			rd = request.getRequestDispatcher("createWorkout.jsp"); //jsp to send back to
		}
		
		rd.include(request, response); //Send to next page		
		
	}

}
