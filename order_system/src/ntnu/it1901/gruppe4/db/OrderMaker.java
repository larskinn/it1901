package ntnu.it1901.gruppe4.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Object class used to create or modify an {@link Order}.
 * 
 * @author David M. 
 */
public class OrderMaker {

	private Order order;
	private List<OrderItem> orderItems;

	private List<OrderItem> addQue; // items to be added next time we save
	private List<OrderItem> remQue; // items to be removed next time we save
	private List<OrderItem> updateQue; // items to be updated next time we save

	private boolean hasBeenSaved;
	private boolean hasBeenModified;

	/**
	 * Creates a new <code>OrderMaker</code> and a new unsaved <code>Order</code>.
	 */
	public OrderMaker() {
		order = new Order();
		orderItems = new ArrayList<OrderItem>();
		addQue = new ArrayList<OrderItem>();
		remQue = new ArrayList<OrderItem>();
		updateQue = new ArrayList<OrderItem>();
		order.setState(Order.NOT_SAVED);
		hasBeenSaved = false;
		hasBeenModified = true;
		calculatePrice();
	}

	/**
	 * Creates a new <code>OrderMaker</code> to modify an existing <code>Order</code>.
	 * 
	 * @param order
	 *            The order to be modified using this <code>OrderMaker</code> object.
	 */
	public OrderMaker(Order order) {
		this.order = order;
		orderItems = DataAPI.getOrderItems(order);
		addQue = new ArrayList<OrderItem>();
		remQue = new ArrayList<OrderItem>();
		updateQue = new ArrayList<OrderItem>();
		hasBeenSaved = true;
		hasBeenModified = false;
		if (canBeChanged()) {
			calculatePrice();
		}
	}

	/**
	 * Save all changes to the <code>Order</code>, either it is new or not. Adds and removes
	 * <code>OrderItems</code> that have been added and removed since the last save.
	 */
	public void save() {
		if (isValid()) {
			if (order.getState() == Order.NOT_SAVED) {
				order.setState(Order.SAVED); // Placed, ready for chef review
			}
			if (!hasBeenSaved) {
				hasBeenSaved = true;

				Calendar cal = Calendar.getInstance();
				order.setOrderTime(cal.getTime());
			}
			DataAPI.saveOrder(order);
			for (OrderItem item : addQue) {
				DataAPI.saveOrderItem(item);
			}
			for (OrderItem item : remQue) {
				DataAPI.remOrderItem(item);
			}
			for (OrderItem item : updateQue) {
				DataAPI.saveOrderItem(item);
			}
			addQue.clear();
			remQue.clear();
			updateQue.clear();
			hasBeenModified = false;
		} else {
			throw new RuntimeException("Order is invalid.");
		}
	}

	/**
	 * Calculates the total price.
	 */
	private void calculatePrice() {
		float total = 0.0f;
		for (OrderItem item : orderItems) {
			total += item.getAmount();
		}

		float delivery = 0.0f;
		float max_delivery = Settings.getDeliveryFee();
		float tax = Settings.getTax();

		if (order.getSelfPickup()) {
			max_delivery = 0.0f;
		}

		delivery = Settings.getFreeDeliveryLimit() - total;
		if (delivery > max_delivery)
			delivery = max_delivery;
		if (delivery < 0.0f)
			delivery = 0.0f;

		total += delivery;

		float taxAmt = total * tax / (100.f + tax);

		order.setTotalAmount(total);
		order.setDeliveryFee(delivery);
		order.setTaxAmount(taxAmt);
	}

	/**
	 * Determines whether the <code>Order</code> is valid in its current state. To be valid,
	 * an <code>Order</code> needs to :
	 * 
	 * <ul>
	 * <li>Have at least one item
	 * <li>Have a valid delivery
	 * address
	 * <li>Have a valid customer (this is inherent in address.isValid()).
	 * </ul>
	 * 
	 * @return <code>true</code> if the <code>Order</code> is valid, <code>false</code> if not.
	 */
	public boolean isValid() {
		/*
		 * A valid order must have: - Items - One of: - A valid address (implies
		 * customer) - Self pickup
		 */
		if (getItemCount() == 0) {
			return false; // The order must have at least one item
		} else if (order.getIdAddress() == null) {
			if (order.getSelfPickup())
				return true; // An anonymous order with self-pickup is valid
			else
				return false; // An anonymous order without self-pickup is not
								// valid
		} else if (!order.getIdAddress().isValid()) {
			if (order.getSelfPickup())
				return true; // An order with an invalid address with
								// self-pickup is valid
			else
				return false; // An order with an invalid address without
								// self-pickup is not valid
		} else {
			return true; // Everything is OK
		}
	}

	/**
	 * Adds a new <code>Dish</code> as an <code>OrderItem</code> to the <code>Order</code>, and recalculates the price.
	 * 
	 * @param dish
	 *            the <code>Dish</code> to be added.
	 * @return a reference to a new <code>OrderItem</code> that contains the name, price and
	 *         a reference to the <code>Dish</code>.
	 */
	public OrderItem addItem(Dish dish) {
		if (canBeChanged()) {
			OrderItem newOrderItem = new OrderItem(order, dish);
			orderItems.add(newOrderItem);
			addQue.add(newOrderItem);
			calculatePrice();
			hasBeenModified = true;
			return newOrderItem;
		} else {
			throw new RuntimeException("Order cannot be changed at this time.");
		}
	}

	/**
	 * Determines whether the <code>Order</code> can be changed. To be changeable, 
	 * the <code>Order</code> must not have been delivered and paid for.
	 * 
	 * @return <code>true</code> if it can be changed, <code>false</code> if not.
	 */
	public boolean canBeChanged() {
		// PS: State numbers are described in comments in the Order class
		return order.getState() < Order.DELIVERED_AND_PAID;
	}

	/**
	 * Returns the number of items in this <code>Order</code>.
	 * 
	 * @return the number of items in this <code>Order</code>.
	 */
	public int getItemCount() {
		return orderItems.size();
	}

	/**
	 * Returns an <code>OrderItem</code> by index.
	 * 
	 * @param index
	 *            the index of the <code>OrderItem</code>.
	 * @return an <code>OrderItem</code>.
	 */
	public OrderItem getItem(int index) {
		return orderItems.get(index);
	}

	/**
	 * Removes an <code>OrderItem</code> by index.
	 * 
	 * @param index
	 *            the index of the <code>OrderItem</code>.
	 */
	public void remItem(int index) {
		if (canBeChanged()) {
			remQue.add(orderItems.get(index));
			orderItems.remove(index);
			calculatePrice();
			hasBeenModified = true;
		} else {
			throw new RuntimeException("Order cannot be changed at this time.");
		}
	}

	/**
	 * Removes an <code>OrderItem</code> by reference.
	 * 
	 * @param item
	 *            a reference to the <code>OrderItem</code> to be removed.
	 */
	public void remItem(OrderItem item) {
		if (canBeChanged()) {
			orderItems.remove(item);
			remQue.add(item);
			calculatePrice();
			hasBeenModified = true;
		} else {
			throw new RuntimeException("Order cannot be changed at this time.");
		}
	}

	/**
	 * Updates an <code>OrderItem</code>. Always use this when modifying an <code>OrderItem</code>,
	 * especially when the price is changed.
	 * 
	 * @param item
	 *            a reference to the <code>OrderItem</code> that has been modified.
	 */
	public void updateItem(OrderItem item) {
		if (canBeChanged()) {
			if (orderItems.contains(item)) {
				updateQue.add(item);
				calculatePrice();
				hasBeenModified = true;
			}
		} else {
			throw new RuntimeException("Order cannot be changed at this time.");
		}
	}

	/**
	 * Sets the {@link Address} to deliver to. The object will be re-instantiated and
	 * assigned.
	 * 
	 * @param address
	 *            A reference to the <code>Address</code> object.
	 */
	public void setAddress(Address address) {
		if (canBeChanged()) {
			if (address == null) {
				order.setIdAddress(null);
			} else {
				order.setIdAddress(DataAPI.getAddress(address.getIdAddress()));
			}
			hasBeenModified = true;
		}
	}

	/**
	 * Sets the order as anonymous, and sets the address to null.
	 */
	public void setSelfPickup(boolean selfPickup) {
		if (canBeChanged()) {
			order.setSelfPickup(selfPickup);
			calculatePrice();
			hasBeenModified = true;
		}
	}

	/**
	 * Returns the address assigned to this <code>Order</code>, or null if none is assigned.
	 * 
	 * @return an Address-object or null.
	 */
	public Address getAddress() {
		if (order.getIdAddress() == null) {
			return null;
		} else {
			return order.getIdAddress();
		}
	}

	/**
	 * Returns an unmodifiable <code>List</code> of all the <code>OrderItems</code>. If you wish to
	 * modify the list, use {@link #addItem}, {@link #remItem} or {@link #updateItem}.
	 * 
	 * @return a reference to the unmodifiable <code>List</code> containing the <code>OrderItems</code>.
	 */
	public List<OrderItem> getItemList() {
		return Collections.unmodifiableList(orderItems);
	}

	/**
	 * Returns a reference to the <code>Order</code> object.
	 * 
	 * @return a reference to the <code>Order</code> object.
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Determines if the <code>Order</code> has been modified.
	 * 
	 * @return <code>true</code> if it has been modified, <code>false</code> if not.
	 */
	public boolean isModified() {
		return hasBeenModified;
	}

	/**
	 * Sets the current state of the <code>Order</code>.
	 * 
	 * @param state The new state of the <code>Order</code>.
	 */
	public void setState(int state) {
		if (canBeChanged()) {
			if (state == Order.DELIVERED_AND_PAID
					&& order.getState() != Order.DELIVERED_AND_PAID) {
				order.setDeliveryTime(Calendar.getInstance().getTime());
			}
			order.setState(state);
			hasBeenModified = true;
		}
	}

	/**
	 * Discards the whole <code>Order</code> and its <code>OrderItems</code> from the database.
	 */
	public void discard() {
		if (canBeChanged()) {
			// Remove order and OrderItems from database.
			DataAPI.remOrderItems(order);
			DataAPI.remOrder(order);

			// Set up the OrderMaker as if the order is unsaved.
			order.setState(Order.NOT_SAVED);
			hasBeenModified = true;
			hasBeenSaved = false;

			for (OrderItem o : orderItems) {
				addQue.add(o);
			}
			remQue.clear();
			updateQue.clear();
		}
	}
}
