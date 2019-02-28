package apex.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import apex.models.Customer;

/**
 * Servlet implementation class RedoWorkController
 */
@WebServlet("/RedoWorkController")
public class RedoWorkController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
	private static java.sql.PreparedStatement prepStmt;


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;

		String action = request.getParameter("workout");

		if (action.equals("View")){

			String id = request.getParameter("id");
			String name = request.getParameter("name");

			request.setAttribute("id", id);
			request.setAttribute("workoutName", name);

			rd = request.getRequestDispatcher("redoWorkouts.jsp");

		}

		if (action.equals("Redo")) {

			String[] selectedExercises = request.getParameterValues("name"); //Get exercises
			String newWorkoutName = request.getParameter("newWorkoutName"); // Get new workout name
			HttpSession session = request.getSession();
			Customer myCustomer = (Customer) session.getAttribute("customer"); //Get current session Customer
			
			String[] stringReps = request.getParameterValues("reps"); // Get rep inputs
			String[] stringSets = request.getParameterValues("sets"); // Get set inputs
			String[] stringWeight = request.getParameterValues("weight"); // Get weight inputs
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
				prepStmt.setString(2, newWorkoutName);
				prepStmt.setString(3, myCustomer.getEmail());
				prepStmt.setDate(4, date);
				prepStmt.executeUpdate();

				//Create exercises for workout
				for (int i = 0; i < selectedExercises.length; i++) {
					
					query = "INSERT INTO workoutExercises VALUES (?, ?, ?, ?, ?)";
					prepStmt = con.prepareStatement(query);
					prepStmt.setString(1, newId.toString()); //Same id as before
					prepStmt.setString(2, selectedExercises[i]); //Current exercise to add.
					prepStmt.setInt(3, Integer.parseInt(stringReps[i])); // Current reps to add
					prepStmt.setInt(4, Integer.parseInt(stringSets[i])); // Current sets to add
					prepStmt.setInt(5, Integer.parseInt(stringWeight[i])); // Current weight to add
					prepStmt.executeUpdate(); // Execute Update statement
				}

				rd = request.getRequestDispatcher("workoutPlanner.jsp"); //jsp to send to
				con.close(); //Close database connection

			} catch (Exception ex) {
				ex.printStackTrace();
				PrintWriter out = response.getWriter();
				out.print("<font color = Red> Somethign went wrong! Please try again in a few minutes... </font>");
				rd = request.getRequestDispatcher("createWorkout.jsp"); //jsp to send back to
			}

		}


		rd.forward(request, response);
	}
}
