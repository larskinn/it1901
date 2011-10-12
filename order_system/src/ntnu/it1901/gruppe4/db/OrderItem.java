package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ntnu.it1901.gruppe4.db.DataAPI;

/**
 * Data class for order items
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

	private OrderItem() {
	}

	public OrderItem(Order order, Dish dish) {
		setIdOrder(order);
		setIdDish(dish);
		setAmount(dish.getPrice());
		setName(dish.getName());
	}

	public OrderItem(Order order, String name, float amount) {
		setIdOrder(order);
		setIdDish(null);
		setAmount(amount);
		setName(name);
	}

	public int getIdOrderItem() {
		return idOrderItem;
	}

	public Order getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Order idOrder) {
		this.idOrder = idOrder;
	}

	public Dish getIdDish() {
		return idDish;
	}

	public void setIdDish(Dish idDish) {
		this.idDish = idDish;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	/**
	 * Persists the OrderItem to the database by updating an existing OrderItem,
	 * or -- if one doesn't exist -- adding a new OrderItem. 
	 */
	public void save() {
		DataAPI.saveOrderItem(this);
	}
}
