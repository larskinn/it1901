package ntnu.it1901.gruppe4.db;

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