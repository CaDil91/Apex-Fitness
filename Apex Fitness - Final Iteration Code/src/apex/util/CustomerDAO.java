package apex.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import apex.models.Customer;
import apex.models.Equipment;

/**
 * Class is used to save and load Customer model information from the database 
 * @author Caleb Dilworth
 */
public class CustomerDAO {
	private static Connection con;
	private static ResultSet rs;
	private PreparedStatement prepStmt;

	/**
	 * Method to get Customer from the database
	 * 
	 * @param email. Customer Email to search for
	 * @param pw TODO pending changes to be salted. Data type byte[]
	 * @return Customer object iff customer exists in the database, else null is returned
	 */
	public Customer getCustomerDB(String email, String pw){

		try {
			//Get database connection
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");

			//Create and execute SQL statement
			String query = "SELECT * FROM customer WHERE email = ? and pw = ?";
			
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, email);
			prepStmt.setString(2, pw);
			rs = prepStmt.executeQuery();
			

			if (!rs.next()){
				return null; //ERROR: No user found in DB, return null
			} else {
				//Get all user information from query result set
				String firstName = rs.getString("f_name");
				String lastName = rs.getString("l_name");
				int age = Integer.parseInt(rs.getString("age"));
				//TODO Currently not getting password to store in Customer class. Not sure if even needed
				Boolean reminder = Boolean.parseBoolean(rs.getString("reminder"));
				String goal = rs.getString("goal");
				int weight = Integer.parseInt(rs.getString("weight"));
				int height = Integer.parseInt(rs.getString("height"));		

				con.close(); //Close DB connection

				//Create Customer with information, and return Customer object
				Customer cs = new Customer(firstName, lastName, age, email, reminder, goal, weight, height);	
				return cs;
			}

		} catch (Exception ex) { 
			ex.printStackTrace();
			return null; //ERROR. Could not connect to DB
		}
	}//End getCustomerDB method
	
	/**
	 * Creates a customer in the database with the given attributes 
	 * 
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param email
	 * @param password
	 * @param reminder
	 * @param goal
	 * @param weight
	 * @param height
	 * @return true iff user was created in the database
	 */
	public boolean createCustomerDB(String firstName, String lastName, int age, String email, String password,	boolean reminder, String goal, int weight, int height) {

		try {
			//Connect to database
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");

			//Attempt to insert a new Customer
			String query = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, email);
			prepStmt.setString(2, firstName);
			prepStmt.setString(3, lastName);
			prepStmt.setInt(4, age);
			prepStmt.setString(5, password);
			prepStmt.setBoolean(6, reminder);
			prepStmt.setString(7, goal);
			prepStmt.setInt(8, weight);
			prepStmt.setInt(9, height);
			prepStmt.executeUpdate();		

			con.close(); //Close database connection

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		return true;

	}//End createCustomerDB

	/**
	 * Method used to get a Customers equipment from the database
	 * 
	 * @param email. The unique email of the Customer
	 * @return the Customers equipment
	 */
	public ArrayList<Equipment> setCustEquipmentDB(String email){
		ArrayList<Equipment> eqList = new ArrayList<Equipment>(); //Create equipment list to return
		
		try {
			//Get database connection
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");

			//Create and execute SQL statement
			String query = "SELECT * FROM customerEquipment WHERE email = ?";
			
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, email);
			rs = prepStmt.executeQuery();

			if (!rs.next()){
				System.out.println("NO EQUIPMENT FOUND FROM QUEURY"); //TODO DELETE TESTING
			} else {
							
				//Get all results from set
				do {
					Equipment eq = new Equipment(rs.getString("eq_name"), "TODO"); //Create new equipment item. TODO description
					eqList.add(eq); //Add equipment item to list					
				} while (rs.next());
				
				con.close(); //close connection	
				
				return eqList; //return list
			}

		} catch (Exception ex) { 
			ex.printStackTrace();
		}
		
		return eqList;
		
	}//end getCustomerEqupmentDB method

	/**
	 * Method used to get a Customers workout ids form the database
	 * 
	 * @param email. The unique email of the customer
	 * @return the Customers workout ids
	 */
	public ArrayList<String> setCustWorkoutIds(String email) {
		ArrayList<String> wkIds = new ArrayList<String>(); //Create workout id list to return
		
		try {
			//Get database connection
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");

			//Create and execute SQL statement
			String query = "SELECT wk_id FROM workouts WHERE email = ?";
			
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, email);
			rs = prepStmt.executeQuery();

			if (!rs.next()){
				System.out.println("NO WORKOUTS FOUND FROM QUERY"); //TODO DELETE TESTING
			} else {
							
				//Get all results from set
				do {
					wkIds.add(rs.getString("wk_id")); //Add equipment item to list	
					System.out.println(rs.getString("wk_id")); //TODO DELETE TESTING
				} while (rs.next());
				
				con.close(); //close connection	
				
				return wkIds; //return list
			}

		} catch (Exception ex) { 
			ex.printStackTrace();
		}
		
		return wkIds;
		
	}//end setCustWorkoutIds method
	
	
}

