package apex.controllers;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CompleteWorkoutController
 * Handles posts from workoutPlanner.jsp and workouts.jsp
 */
@WebServlet("/CompleteWorkoutController")
public class CompleteWorkoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
	private static java.sql.PreparedStatement prepStmt;
	private static java.sql.PreparedStatement prepStmt2;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		
		String action = request.getParameter("workout");
		
		if (action.equals("Do it!")){
		
			//Get the selected workout name from Workout Planner
			String workoutName = request.getParameter("wkName");
			String workoutId = request.getParameter("wkId");
			
			// Set workout name in the request object
			request.setAttribute("workoutName", workoutName);
			request.setAttribute("wkId", workoutId);
			rd = request.getRequestDispatcher("workouts.jsp"); // jsp to send to
		}
		
		if (action.equals("Complete")) {
			
			// Get values of exercises names, reps, sets, and weight
			String[] stringName = request.getParameterValues("name");
			String[] stringReps = request.getParameterValues("reps");
			String[] stringSets = request.getParameterValues("sets");
			String[] stringWeight = request.getParameterValues("weight");
			
			System.out.println("reps" + stringReps[0]);
			
			
			// Get value of workout id
			String[] workoutID = request.getParameterValues("workoutID");
			System.out.println("workoutid " + workoutID[0]);
			
			updateWorkout(stringName, stringReps, stringSets, stringWeight, workoutID);
			
			rd = request.getRequestDispatcher("workoutHistory.jsp");
		}

		rd.include(request, response); //Send to next page
		
	}//End doPost method
	
	
	private boolean updateWorkout(String[] stringName, String[] stringReps, String[] stringSets, 
			String[] stringWeight, String[] workoutID) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");

			// Update the workouts table to reflect that the workout is completed
			String query = "UPDATE workouts SET completed = true where wk_id = ?";
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, workoutID[0]);
			prepStmt.executeUpdate();	
			
			
			for (int i=0; i < stringName.length; i++){
				
				
				System.out.println(stringReps[i] + stringSets[i] + stringWeight[i] + workoutID[0] + stringName[i]);
				
				String query2 = "UPDATE workoutexercises SET reps = ?, sets = ?, weight = ? where wk_id = ? and ex_name = ?";
				prepStmt2 = con.prepareStatement(query2);
				prepStmt2.setInt(1, Integer.parseInt(stringReps[i]));
				prepStmt2.setInt(2, Integer.parseInt(stringSets[i]));
				prepStmt2.setInt(3, Integer.parseInt(stringWeight[i]));
				prepStmt2.setString(4, workoutID[0]);
				prepStmt2.setString(5, stringName[i]);
				prepStmt2.executeUpdate();	
			}
			
			
			return true;

		} catch (Exception ex) { 
			ex.printStackTrace();
		}

		return false;
	}
	

}
