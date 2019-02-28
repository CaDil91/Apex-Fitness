package apex.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import apex.util.BCrypt;

/**
 * Class is used to authenticate a user's hashed password
 * 
 * @author Team Apex
 * ICS 499 - Apex Fitness
 */
public class Authenticator {
	private static Connection con;
	private static ResultSet rs;
	private static PreparedStatement prepStmt;

	
	/**
	 * Authenticates the password
	 * @param password - the entered password
	 * @param email - the entered email
	 * @return Returns the hashed value of the password
	 */
	public String AuthenticateExisting(String email, String password){

		try {
			//Get database connection
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/apex?user=root&password=ics499");

			//Create and execute SQL statement
			String query = "SELECT pw FROM customer WHERE email = ?";

			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, email);
			rs = prepStmt.executeQuery();

			// Get the password from the result set
			if (rs.next()) {
				
				// Set the hashed password
				String hashed = rs.getString("pw");

				// Check that an unencrypted password matches the one that has
				// previously been hashed
				if (BCrypt.checkpw(password, hashed)){
					// Given password and stored password match
					return hashed;
				}else{
					// Given password and stored password do not match
					throw new Exception("incorrect password");
				}
			}


		} catch (Exception ex) { 
			ex.printStackTrace();
		}

		return password;		

	}


	/**
	 * Produces a hash for the given password
	 * @param password
	 * @return the hashed value of the password
	 */
	public static String Encrypt(String password){

		// Create hash for password
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

		return hashed;

	}


}
