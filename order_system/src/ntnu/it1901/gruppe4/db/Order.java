package ntnu.it1901.gruppe4.db;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ntnu.it1901.gruppe4.db.DataAPI;

/**
 * Data class for orders
 * 
 * @author David M.
 */
@DatabaseTable(tableName = "order")
public class Order {
	// ORMLite doesn't like enums. We'll just use const ints instead.
	public final static int NOT_SAVED = 0;
	public final static int SAVED = 1;
	public final static int IN_PRODUCTION = 2;
	public final static int READY_FOR_DELIVERY = 3;
	public final static int IN_TRANSIT = 4;
	public final static int DELIVERED_AND_PAID = 5;

	private static String state_names[] = { "Ikke lagret", "Lagret",
			"I produksjon", "Klar til levering", "I bil",
			"Levert og betalt" };

	@DatabaseField(canBeNull = false, generatedId = true)
	int idOrder;

	@DatabaseField(useGetSet = true)
	Date orderTime;

	@DatabaseField(useGetSet = true)
	Date deliveryTime;

	@DatabaseField(useGetSet = true)
	int state;
	// States:
	// 0: Not saved
	// 1: Saved
	// 2: In production (not used)
	// 3: Ready for delivery
	// 4: In transit
	// 5: Delivered & paid for

	@DatabaseField(useGetSet = true)
	float totalAmount;

	// PS: The idAddress object might not actually contain the data. The
	// idAddress object will be an Address object without any id, except for the
	// idAddress field. This is it's only use, and it's used by DataAPI.

	@DatabaseField(useGetSet = true, foreign = true, canBeNull = false)
	Address idAddress;

	public Order() {
		setState(Order.NOT_SAVED);
	}

	public Order(Address address) {
		setIdAddress(address);
		setDeliveryTime(new Date()); // Supposedly this is the current time
		setState(Order.NOT_SAVED);
	}

	public Address getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(Address idAddress) {
		this.idAddress = idAddress;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public int getState() {
		return state;
	}

	public String getStateName() {
		if (state < 0 || state > 5)
			return "(" + state + ")";
		else
			return state_names[state];
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Persists the Order to the database by updating an existing Order, or --
	 * if one doesn't exist -- adding a new Order.
	 */
	protected void save() {
		DataAPI.saveOrder(this);
	}

	/**
	 * Returns TRUE if this order should be visible in OrderWindow.
	 * @return TRUE or FALSE
	 */
	public boolean isVisibleToOperator()
	{
		return state == SAVED || state == NOT_SAVED;
	}
	/**
	 * Returns TRUE if this order should be visible in ChefWindow.
	 * @return TRUE or FALSE
	 */
	public boolean isVisibleToChef()
	{
		return state == SAVED || state == IN_PRODUCTION;
	}
	/**
	 * Returns TRUE if this order should be visible in DeliveryWindow.
	 * @return TRUE or FALSE
	 */
	public boolean isVisibleToDelivery()
	{
		return state == READY_FOR_DELIVERY || state == IN_TRANSIT;
	}
	/**
	 * Returns TRUE if this order should be visible in order history.
	 * @return TRUE or FALSE
	 */
	public boolean isVisibleInHistory()
	{
		return state == DELIVERED_AND_PAID || state == SAVED || state == IN_PRODUCTION || state == READY_FOR_DELIVERY || state == IN_TRANSIT;
	}
}
