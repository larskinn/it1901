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

	@DatabaseField(useGetSet = true, foreign = true, canBeNull = false)
	Address idAddress;

	// PS: We determine customer trough address
	// @DatabaseField(useGetSet = true, foreign = true, canBeNull = false)
	// int idcustomer;

	public Order() {
	}

	public Order(Address address) {
		setIdAddress(address);
		setDeliveryTime(new Date()); // Supposedly this is the current time
		setState(0);
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

	public void setState(int state) {
		this.state = state;
	}
}
