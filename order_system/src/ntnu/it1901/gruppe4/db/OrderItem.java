package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
		setIdorder(order);
		setIddish(dish);
		setAmount(dish.getPrice());
		setName(dish.getName());
	}

	public OrderItem(Order order, String name, float amount) {
		setIdorder(order);
		setIddish(null);
		setAmount(amount);
		setName(name);
	}

	public int getIdorderitem() {
		return idOrderItem;
	}

	public Order getIdorder() {
		return idOrder;
	}

	public void setIdorder(Order idOrder) {
		this.idOrder = idOrder;
	}

	public Dish getIddish() {
		return idDish;
	}

	public void setIddish(Dish idDish) {
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
}
