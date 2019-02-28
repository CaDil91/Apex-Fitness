package apex.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


import apex.util.CustomerDAO;

/**
 * A model to hold customer data
 * TODO: Comments
 * @author Caleb Dilworth
 */
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private boolean reminder;
	private String goal;
	private int weight;
	private int height;	
	private ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();
	private ArrayList<String> workoutIds = new ArrayList<String>();

	/**
	 * Default constructor
	 */
	public Customer() {
	}

	/**
	 * Constructor for the Customer
	 * 
	 * @param firstName. First name of the Customer
	 * @param lastName. Last name of the Customer
	 * @param age. Age of the Customer
	 * @param email. Email address of the Customer
	 * @param reminder. Email reminders, true or false
	 * @param goal. Goal of the Customer. Muscle Gain, Weight Loss, or Both
	 * @param weight. Customer weight
	 * @param height. Customer height
	 */
	public Customer(String firstName, String lastName, int age, String email, boolean reminder, String goal, int weight, int height) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.reminder = reminder;
		this.goal = goal;
		this.weight = weight;
		this.height = height;
	}


	//======Getters and Setters Start=======
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the reminder
	 */
	public boolean isReminder() {
		return reminder;
	}

	/**
	 * @param reminder the reminder to set
	 */
	public void setReminder(boolean reminder) {
		this.reminder = reminder;
	}

	/**
	 * @return the goal
	 */
	public String getGoal() {
		return goal;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(String goal) {
		this.goal = goal;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the equipmentList
	 */
	public ArrayList<Equipment> getEquipmentList() {
		return equipmentList;
	}
	
	//======Getters and Setters End=======

	/**
	 * Gets and sets equipmentList with data from database
	 * @param customerDAO. Passed in data access object to access database with
	 */
	public void setEquipmentDB(CustomerDAO customerDAO) {
		this.equipmentList = customerDAO.setCustEquipmentDB(this.email);
	}	
	
	/**
	 * Gets and sets workoutIds with data from database
	 * @param customerDAO. Passed in data access object to access database with
	 */
	public void setWorkoutsDB(CustomerDAO customerDAO) {
		this.workoutIds = customerDAO.setCustWorkoutIds(this.email);		
	}
	

	/**
	 * Method adds and equipment item to the Customers list
	 * @param eq. Equipment object to add to the list
	 * TODO: Add to database as well?
	 */
	public void addEquipment(Equipment eq){
		this.equipmentList.add(eq);
	}

	/**
	 * Method removes an equipment item from the Customers list
	 * @param eqName. The name of the Equipment to remove from the list
	 * TODO: remove from database as well?
	 */
	public void removeEquipment(String eqName){
		for (Iterator<Equipment> iterator = equipmentList.iterator(); iterator.hasNext();) {
			Equipment equipment = iterator.next();
			if (equipment.getEquipmentName().equals(eqName)) {
				equipmentList.remove(equipment);
				return; //Found item to remove. Exit method
			}			
		}
	}

}
