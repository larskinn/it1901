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
	private int idDish;

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
	 * @param name
	 *            Name of the dish
	 * @param price
	 *            Price of the dish
	 * @param description
	 *            Short description of dish
	 * @param active
	 *            Whether the dish is active (can be ordered)
	 */
	public Dish(String name, float price, String description, boolean active) {
		if (name != null) {
			this.name = name;
			this.price = price;
			this.description = description;
			this.active = active;
		}
		else {
			throw new IllegalArgumentException("String name can't be null");
		}
	}

	private Dish() {
	}

	public int getIdDish() {
		return idDish;
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

	public void setName(String name) {
		if (name != null) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("String name can't be null");
		}
		this.name = name;
		// TODO: validate value (name can't be null)
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean getActive() {
		return active;
	}
}
