package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "orderitem")
public class OrderItem {

	@DatabaseField(canBeNull = false, generatedId = true)
	int idorderitem;

	@DatabaseField(useGetSet = true, foreign = true, canBeNull = false)
	Order idorder;

	// PS: We don't always need a dish item, in case we include arbitrary things
	// like a discount
	@DatabaseField(useGetSet = true, foreign = true)
	Dish iddish;

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
		return idorderitem;
	}

	public Order getIdorder() {
		return idorder;
	}

	public void setIdorder(Order idorder) {
		this.idorder = idorder;
	}

	public Dish getIddish() {
		return iddish;
	}

	public void setIddish(Dish iddish) {
		this.iddish = iddish;
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
