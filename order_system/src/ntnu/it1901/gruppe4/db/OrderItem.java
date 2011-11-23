package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for order items.
 * 
 * @author David M.
 */
@DatabaseTable(tableName = "orderitem")
public class OrderItem {

	@DatabaseField(canBeNull = false, generatedId = true)
	int idOrderItem;

	@DatabaseField(useGetSet = true, foreign = true, canBeNull = false)
	Order idOrder;

	// PS: We don't always need a dish item, in case we include arbitrary things
	// like a discount
	@DatabaseField(useGetSet = true, foreign = true)
	Dish idDish;

	@DatabaseField(useGetSet = true, canBeNull = false)
	String name;

	@DatabaseField(useGetSet = true)
	float amount;

	@DatabaseField(useGetSet = true)
	String description;

	/*
	 * This apparently useless private constructor is 
	 * required by the sqlite-jdbc library.
	 */
	@SuppressWarnings("unused")
	private OrderItem() {
		
	}

	/**
	 * Constructor that creates a new <code>OrderItem</code> from a dish. The name and price
	 * of the {@link Dish} will be copied to the <code>OrderItem</code>.
	 * 
	 * @param order
	 *            the {@link Order} that this <code>OrderItem</code> should be placed in.
	 * @param dish
	 *            the <code>Dish</code> that this <code>OrderItem</code> 
	 *            should inherit its name and price from.
	 */
	public OrderItem(Order order, Dish dish) {
		setIdOrder(order);
		setIdDish(dish);
		setAmount(dish.getPrice());
		setName(dish.getName());
		setDescription("");
	}

	/**
	 * Constructor that creates a new <code>OrderItem</code> from name and price. This
	 * orderItem will not be associated with any dish.
	 * 
	 * @param order
	 *            the <code>Order</code> that the <code>OrderItem</code> should be placed in.
	 * @param name
	 *            The name of the <code>OrderItem</code>.
	 * @param amount
	 *            The price of the <code>OrderItem</code>.
	 */
	public OrderItem(Order order, String name, float amount) {
		setIdOrder(order);
		setIdDish(null);
		setAmount(amount);
		setName(name);
	}

	/**
	 * Returns the data id of this object.
	 * 
	 * @return the data id of this object.
	 */
	public int getIdOrderItem() {
		return idOrderItem;
	}

	/**
	 * Returns the <code>Order</code> that this <code>OrderItem</code> is a part of.
	 * 
	 * @return the <code>Order</code> that this <code>OrderItem</code> is a part of.
	 */
	public Order getIdOrder() {
		return idOrder;
	}

	/**
	 * Associated this <code>OrderItem</code> with a new <code>Order</code>.
	 * 
	 * @param idOrder
	 *            the new <code>Order</code> to place this <code>OrderItem</code> in.
	 */
	public void setIdOrder(Order idOrder) {
		this.idOrder = idOrder;
	}

	/**
	 * Returns the <code>Dish</code> that this <code>OrderItem</code> was created from. It may be <code>null</code>,
	 * if this <code>OrderItem</code> is a custom item.
	 * 
	 * @return the <code>Dish</code> that this order was created from, or <code>null</code>.
	 */
	public Dish getIdDish() {
		return idDish;
	}

	/**
	 * Sets the <code>Dish</code> reference of this <code>OrderItem</code>. May be null.
	 * 
	 * @param idDish
	 *            the new <code>dish</code>, or <code>null</code>.
	 */
	public void setIdDish(Dish idDish) {
		this.idDish = idDish;
	}

	/**
	 * Returns the name of this <code>OrderItem</code>. May be the name of a <code>Dish</code>, or a
	 * descriptive name of an <code>Order</code>, like "Pizza Pepperoni /w extra cheese" or
	 * "Coke 33cl".
	 * 
	 * @return the name of this <code>OrderItem</code>.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the <code>OrderItem</code>.
	 * 
	 * @param name
	 *            the new name of the <code>OrderItem</code>.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the price of the <code>OrderItem</code>. It may or may not be consistent with
	 * the current price of the <code>Dish</code>. The price is assigned at the time when the
	 * <code>OrderItem</code> was created.
	 * 
	 * @return the price of this <code>OrderItem</code>.
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * Sets the price of the <code>OrderItem</code>.
	 * 
	 * @param amount
	 *            the new price of the <code>OrderItem</code>.
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * Returns the description of the item.
	 * 
	 * @return the price of this <code>OrderItem</code>.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the <code>OrderItem</code>.
	 * 
	 * @param description
	 *            the new description of the <code>OrderItem</code>.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Saves the <code>OrderItem</code> to the database by updating an existing <code>OrderItem</code>,
	 * or -- if one doesn't exist -- adding a new <code>OrderItem</code>.
	 */
	public void save() {
		DataAPI.saveOrderItem(this);
	}
}
