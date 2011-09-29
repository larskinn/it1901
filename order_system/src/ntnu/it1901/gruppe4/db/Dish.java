package ntnu.it1901.gruppe4.db;

import javax.management.Descriptor;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for dishes
 * 
 * @author David M.
 */
@DatabaseTable(tableName = "dish")
public class Dish {

	@DatabaseField(canBeNull = false, generatedId = true)
	private int iddish;

	@DatabaseField(useGetSet = true, canBeNull = false)
	private String name;

	@DatabaseField(useGetSet = true)
	private float price;

	@DatabaseField(useGetSet = true)
	private String description;
	
	@DatabaseField(useGetSet = true)
	private boolean active;

	// TODO: javadoc
	
	/**
	 * Constructs a dish
	 * 
	 * @param vName
	 *            Name of the dish
	 * @param vPrice
	 *            Price of the dish
	 * @param vDescription
	 *            Short description of dish
	 * @param vActive
	 *            Whether the dish is active (can be ordered)
	 */
	public Dish(String vName, float vPrice, String vDescription, boolean vActive) {
		name = vName;
		price = vPrice;
		description = vDescription;
		active = vActive;
		
		// TODO: validate values (name can't be null)
	}

	private Dish() {
	}

	public int getIddish() {
		return iddish;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String vName) {
		name = vName;
		// TODO: validate value (name can't be null)
	}

	public void setPrice(float vPrice) {
		price = vPrice;
	}

	public void setDescription(String vDescription) {
		description = vDescription;
	}
}
