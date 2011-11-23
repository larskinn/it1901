package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for dishes.
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

	/**
	 * Constructs a new {@link Dish} object.
	 * 
	 * @param name
	 *            name of the dish.
	 * @param price
	 *            price of the dish.
	 * @param type
	 *            type of dish.
	 * @param description
	 *            short description of dish.
	 * @param active
	 *            whether the dish is active (can be ordered).
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

	/*
	 * This apparently useless private constructor is 
	 * required by the sqlite-jdbc library.
	 */
	@SuppressWarnings("unused")
	private Dish() {
		
	}

	/**
	 * Returns the database id.
	 * 
	 * @return the database id.
	 */
	public int getIdDish() {
		return idDish;
	}

	/**
	 * Returns the name of the <code>Dish</code>.
	 * 
	 * @return the name of the <code>Dish</code>.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the price of the <code>Dish</code>.
	 * 
	 * @return the price of the <code>Dish</code>.
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * Returns the {@link DishType} of the <code>Dish</code>.
	 * 
	 * @return the <code>DishType</code> of the <code>Dish</code>.
	 */
	public DishType getType() {
		return DishType.valueOf(type);
	}
	

	/**
	 * Returns the description of the <code>Dish</code>.
	 * 
	 * @return the description of the <code>Dish</code>.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the name of the <code>Dish</code>.
	 * 
	 * @param name
	 *            the new name.
	 */
	public void setName(String name) {
		if (name != null) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("String name can't be null");
		}
		this.name = name;
	}

	/**
	 * Changes the price of the <code>Dish</code>.
	 * 
	 * @param price
	 *            the new price.
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
	/**
	 * Changes the {@link DishType} of the {@link Dish}.
	 * 
	 * @param type the new <code>DishType</code>.
	 */
	public void setType(DishType type) {
		this.type = type.name();
	}

	/**
	 * Changes the description of the <code>Dish</code>.
	 * 
	 * @param description
	 *            the new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Changes the active status of the <code>Dish</code>.
	 * 
	 * @param active
	 *            the new active status.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Returns the active status of the <code>Dish</code>.
	 * 
	 * @return the active status of the <code>Dish</code>.
	 */
	public boolean getActive() {
		return active;
	}
	
	/**
	 * Saves the {@link Dish} to the database by updating an existing <code>Dish</code>,
	 * or -- if one doesn't exist -- adding a new <code>Dish</code>. 
	 */
	public void save() {
		DataAPI.saveDish(this);
	}
}
