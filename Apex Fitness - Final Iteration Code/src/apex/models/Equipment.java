package apex.models;

/**
 * A model to hold customers equipment data
 * @author Caleb Dilworth
 */
public class Equipment {

	private String equipmentName;
	private String equipmentDescription;

	/**
	 * Class constructor
	 * 
	 * @param equipmentName. Name of equipment item
	 * @param equipmentDescription. Description of equipment item
	 */
    public Equipment(String equipmentName, String equipmentDescription) {
    	this.equipmentName = equipmentName;
    	this.equipmentDescription = equipmentDescription;
    }

	/**
	 * @return the equipmentName
	 */
	public String getEquipmentName() {
		return equipmentName;
	}

	/**
	 * @param equipmentName the equipmentName to set
	 */
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	/**
	 * @return the equipmentDescription
	 */
	public String getEquipmentDescription() {
		return equipmentDescription;
	}

	/**
	 * @param equipmentDescription the equipmentDescription to set
	 */
	public void setEquipmentDescription(String equipmentDescription) {
		this.equipmentDescription = equipmentDescription;
	}
    
    @Override
    public String toString() {
    	String rString = "";
    	rString += equipmentName;   	
    	return rString;
    }
    
    
}
