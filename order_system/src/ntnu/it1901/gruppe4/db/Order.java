package ntnu.it1901.gruppe4.db;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for orders
 * 
 * @author David M.
 */
@DatabaseTable(tableName = "order")
public class Order {
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
	// 10: Saved
	// 20: Ready for delivery
	// 30: In transit
	// 40: Delivered & paid for

	@DatabaseField(useGetSet = true)
	float totalAmount;

	// PS: The idAddress object might not actually contain the data. The
	// idAddress object will be an Address object without any id, except for the
	// idAddress field. This is it's only use, and it's used by DataAPI.

	@DatabaseField(useGetSet = true, foreign = true, canBeNull = false)
	Address idAddress;

	/**
	 * Constructor that creates an empty Order object
	 */
	public Order() {
	}

	/**
	 * Constructor that creates an empty Order, associated with an address
	 * 
	 * @param address
	 *            The address to be associated with this order
	 */
	public Order(Address address) {
		setIdAddress(address);
		setDeliveryTime(new Date()); // Supposedly this is the current time
		setState(0);
	}

	/**
	 * Returns the address associated with this order. This order might be a
	 * reference object, i.e. the object only contains the data id.
	 * 
	 * @return An address object
	 */
	public Address getIdAddress() {
		return idAddress;
	}

	/**
	 * Sets the address associated with this order.
	 * 
	 * @param idAddress
	 *            The address object.
	 */
	public void setIdAddress(Address idAddress) {
		this.idAddress = idAddress;
	}

	/**
	 * Returns the data id of this object
	 * 
	 * @return the data id of this object
	 */
	public int getIdOrder() {
		return idOrder;
	}

	/**
	 * Returns the time of this order
	 * 
	 * @return
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * Sets the orderTime
	 * 
	 * @param orderTime
	 *            the orderTime
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * Returns the time of delivery for this order. This is only valid if the
	 * order has been delivered.
	 * 
	 * @return
	 */
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	/**
	 * Sets the delivery time of this order. This is only valid after the order
	 * has been delivered.
	 * 
	 * @param deliveryTime
	 *            the delivery time.
	 */
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	/**
	 * Returns the state of the order. It can be 
	 * @return the state of the order.
	 */
	public int getState() {
		return state;
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
}
