package ntnu.it1901.gruppe4.db;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for orders.
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
			"I produksjon", "Klar til levering", "I bil", "Levert og betalt" };

	@DatabaseField(canBeNull = false, generatedId = true)
	int idOrder = 0;

	@DatabaseField(useGetSet = true)
	Date orderTime;

	@DatabaseField(useGetSet = true)
	Date deliveryTime;

	@DatabaseField(useGetSet = true)
	int state = 0;
	// States:
	// 0: Not saved
	// 1: Saved
	// 2: In production (not used)
	// 3: Ready for delivery
	// 4: In transit
	// 5: Delivered & paid for

	@DatabaseField(useGetSet = true)
	float totalAmount = 0;

	@DatabaseField(useGetSet = true)
	float taxAmount = 0;

	@DatabaseField(useGetSet = true)
	float deliveryFee = 0;

	// PS: The idAddress object might not actually contain the data. The
	// idAddress object will be an Address object without any id, except for the
	// idAddress field. This is it's only use, and it's used by DataAPI.

	@DatabaseField(useGetSet = true, foreign = true)
	Address idAddress = null;

	@DatabaseField(useGetSet = true)
	boolean selfPickup = false;

	/**
	 * Constructor that creates an empty {@link Order} object.
	 */
	public Order() {
		setState(Order.NOT_SAVED);
		deliveryFee = 0.0f;
	}

	/**
	 * Constructor that creates an empty {@link Order}, associated with an {@link Address}.
	 * 
	 * @param address
	 *            The <code>Address</code> to be associated with this <code>Order</code>.
	 */
	public Order(Address address) {
		setIdAddress(address);
		setDeliveryTime(new Date()); // Supposedly this is the current time
		setState(Order.NOT_SAVED);
		deliveryFee = 0.0f;
	}

	/**
	 * Returns the {@link Address} associated with this {@link Order}. This <code>Order</code> might be a
	 * reference object, i.e. the object only contains the data id.
	 * 
	 * @return An <code>Address</code> object.
	 */
	public Address getIdAddress() {
		return idAddress;
	}

	/**
	 * Sets the {@link Address} associated with this order.
	 * 
	 * @param idAddress
	 *            The <code>Address</code> object.
	 */
	public void setIdAddress(Address idAddress) {
		this.idAddress = idAddress;
	}

	/**
	 * Returns the data id of the <code>Order</code>.
	 * 
	 * @return the data id of the <code>Order</code>.
	 */
	public int getIdOrder() {
		return idOrder;
	}

	/**
	 * Returns the <code>Date</code> when this 
	 * <code>Order</code> was ordered.
	 * 
	 * @return the <code>Date</code> when this 
	 * 			<code>Order</code> was ordered.
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * Sets the <code>Date</code> this 
	 * <code>Order</code> was ordered.
	 * 
	 * @param orderTime 
	 *				the <code>Date</code> this 
	 * 				<code>Order</code> was ordered.
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * Returns the <code>Date</code> when this 
	 * <code>Order</code> was delivered.
	 * 
	 * @return the <code>Date</code> when this 
	 * 			<code>Order</code> was delivered.
	 */
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	/**
	 * Sets the <code>Date</code> this 
	 * <code>Order</code> was delivered.
	 * 
	 * @param deliveryTime 
	 *				the <code>Date</code> this 
	 * 				<code>Order</code> was delivered.
	 */
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	/**
	 * Returns the state of the order. It can be NOT_SAVED, SAVED,
	 * IN_PRODUCTION, READY_FOR_DELIVERY, IN_TRANSIT or DELIVERED_AND_PAID.
	 * 
	 * @return the state of the order.
	 */
	public int getState() {
		return state;
	}

	/**
	 * Returns a <code>String</code> with a readable description of the state of this order.
	 * 
	 * @return a <code>String</code> with the name of the state.
	 */
	public String getStateName() {
		if (state < 0 || state > 5)
			return "(" + state + ")";
		else
			return state_names[state];
	}

	/**
	 * Sets the state of the order.
	 * 
	 * @param state
	 *            the new state.
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * Returns the total amount of this <code>Order</code>. The total amount is the price of
	 * each order item, plus any fees that apply.
	 * 
	 * @return The total amount (price) of this <code>Order</code>.
	 */
	public float getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Sets the total amount of this <code>Order</code>.
	 * Should only be done by an {@link OrderMaker}.
	 * 
	 * @param totalAmount
	 *            the new total amount of the <code>Order</code>.
	 */
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Returns the tax amount of this <code>Order</code>. 
	 * The tax amount is set to total*tax/(1+tax).
	 * 
	 * @return The tax amount of this <code>Order</code>.
	 */
	public float getTaxAmount() {
		return taxAmount;
	}

	/**
	 * Sets the tax amount of this <code>Order</code>.
	 * Should only be done by an {@link OrderMaker}.
	 * 
	 * @param taxAmount
	 *            the new tax amount of the <code>Order</code>.
	 */
	public void setTaxAmount(float taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * Returns the delivery fee.
	 * 
	 * @return the delivery fee.
	 */
	public float getDeliveryFee() {
		return deliveryFee;
	}

	/**
	 * Sets the delivery fee.
	 * 
	 * @param deliveryFee
	 *            the delivery fee.
	 */
	public void setDeliveryFee(float deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	/**
	 * Saves the <code>Order</code> to the database by updating an existing <code>Order</code>, 
	 * or -- if one doesn't exist -- adding a new <code>Order</code>.
	 */
	protected void save() {
		DataAPI.saveOrder(this);
	}

	/**
	 * Returns <code>true</code> if this <code>Order</code> should be visible in the <code>OrderWindow</code>.
	 * 
	 * @return <code>true</code> or <code>false</code>.
	 */
	public boolean isVisibleToOperator() {
		return true;
	}

	/**
	 * Returns <code>true</code> if this <code>Order</code> should be visible in the <code>OrderWindow</code>.
	 * 
	 * @return <code>true</code> or <code>false</code>.
	 */
	public boolean isVisibleToChef() {
		return state == SAVED || state == IN_PRODUCTION;
	}

	/**
	 * Returns <code>true</code> if this <code>Order</code> should be visible in the <code>DeliveryWindow</code>.
	 * 
	 * @return <code>true</code> or <code>false</code>.
	 */
	public boolean isVisibleToDelivery() {
		return !selfPickup
				&& (state == READY_FOR_DELIVERY || state == IN_TRANSIT);
	}

	/**
	 * Returns <code>true</code> if this <code>Order</code> should be visible in the <code>OrderHistory</code>.
	 * 
	 * @return <code>true</code> or <code>false</code>.
	 */
	public boolean isVisibleInHistory() {
		return state == DELIVERED_AND_PAID || state == SAVED
				|| state == IN_PRODUCTION || state == READY_FOR_DELIVERY
				|| state == IN_TRANSIT;
	}

	/**
	 * Sets whether this <code>Order</code> should be picked up in 
	 * the restaurant by the customer.
	 */
	public void setSelfPickup(boolean selfPickup) {
		this.selfPickup = selfPickup;
	}

	/**
	 * Returns whether this <code>Order</code> should be picked up in 
	 * the restaurant by the customer.
	 * 
	 * @return <code>true</code> if the <code>Order</code> should be picked up.
	 */
	public boolean getSelfPickup() {
		return selfPickup;
	}
}
