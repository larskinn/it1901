package ntnu.it1901.gruppe4.db;

/**
 * An enum containing the different dish types used to 
 * categorize the <code>Dishes</code> in the system.
 * 
 * @author Lars Kinn Ekroll
 */
public enum DishType {
	PIZZA("Pizza"), DRINK("Drikke"), CONDIMENT("Saus/tilbehør"), ACCESSORY("Ikke-spiselig tilbehør");
	
	private final String prettyName;
	DishType(String prettyName) {
		this.prettyName = prettyName;
	}
	
	public String toString() {
		return prettyName;
	}
}