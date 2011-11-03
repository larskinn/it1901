package ntnu.it1901.gruppe4.db;

import javax.management.Descriptor;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ntnu.it1901.gruppe4.db.DataAPI;

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
	
	@DatabaseField(useGetSet = false)
	private String type;

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
	 * @param type
	 *            Type of dish
	 * @param description
	 *            Short description of dish
	 * @param active
	 *            Whether the dish is active (can be ordered)
	 */
	public Dish(String name, float price, DishType type, String description, boolean active) {
		if (name != null) {
			this.name = name;
			this.price = price;
			this.description = description;
			this.active = active;
			this.setType(type);
		} else {
			throw new IllegalArgumentException("String name can't be null");
		}
	}

	private Dish() {
	}

	/**
	 * Returns the database id
	 * 
	 * @return the database id
	 */
	public int getIdDish() {
		return idDish;
	}

	/**
	 * Returns the name of the dish
	 * 
	 * @return the name of the dish
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the price of the dish
	 * 
	 * @return the price of the dish
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * Returns the type of the dish
	 * 
	 * @return the type of the dish
	 */
	public DishType getType() {
		return DishType.valueOf(type);
	}
	

	/**
	 * Returns the description of the dish
	 * 
	 * @return the description of the dish
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the name
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		if (name != null) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("String name can't be null");
		}
		this.name = name;
		// TODO: validate value (name can't be null)
	}

	/**
	 * Changes the price
	 * 
	 * @param price
	 *            the new price
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
	public void setType(DishType type) {
		this.type = type.toString();
	}

	/**
	 * Changes the description
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Changes the active status
	 * 
	 * @param active
	 *            the new active status
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Returns the active status of the dish
	 * 
	 * @return the active status of the dish
	 */
	public boolean getActive() {
		return active;
	}
	
	/**
	 * Persists the Dish to the database by updating an existing Dish,
	 * or -- if one doesn't exist -- adding a new Dish. 
	 */
	public void save() {
		DataAPI.saveDish(this);
	}
}
