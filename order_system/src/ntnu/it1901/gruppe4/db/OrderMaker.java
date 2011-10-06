/**
 * 
 */
package ntnu.it1901.gruppe4.db;

import java.util.Collections;
import java.util.List;

/**
 * @author David M.
 * 
 */
public class OrderMaker {

	private Order order;
	private List<OrderItem> orderItems;
	private float total;

	private boolean hasBeenSaved;

	OrderMaker() {
		order = new Order();
		order.setState(0);
		hasBeenSaved = false;
		calculatePrice();
	}

	OrderMaker(Order o) {
		order = o;
		hasBeenSaved = true;
		total = order.getTotalAmount();
	}

	public void save() {
		if (isValid()) {
			if (hasBeenSaved) {
				DataAPI.addOrder(order);
				for (OrderItem item : orderItems) {
					DataAPI.addOrderItem(item);
				}
			} else {
				// TODO: Save changes to already saved order
				// Needs to add and remove order items
				// Might need to remove and re-add order items
			}
		}
	}

	private void calculatePrice() {
		total = 0.0;
		for (OrderItem item : orderItems) {
			total += item.getAmount();
		}

		// TODO: beregn frakt
	}

	public boolean isValid() {
		if (getItemCount() == 0)
			return false;
		if (order.getIdAddress() == null)
			return false;
		if (!order.getIdAddress().isValid())
			return false;
		return true;
	}

	public OrderItem addItem(Dish dish) {
		if (canBeChanged()) {
			OrderItem newOrderItem = new OrderItem(order, dish);
			orderItems.add(newOrderItem);
			calculatePrice();
			return newOrderItem;
		} else {
			throw new RuntimeException("Order cannot be changed at this time.");
		}
	}

	private boolean canBeChanged() {
		return order.getState() < 40;
	}

	public int getItemCount() {
		return orderItems.size();
	}

	public OrderItem getItem(int index) {
		return orderItems.get(index);
	}

	public void remItem(int index) {
		if (canBeChanged()) {
			orderItems.remove(index);
			calculatePrice();
		} else {
			throw new RuntimeException("Order cannot be changed at this time.");
		}
	}

	public void remItem(OrderItem item) {
		if (canBeChanged()) {
			orderItems.remove(item);
			calculatePrice();
		} else {
			throw new RuntimeException("Order cannot be changed at this time.");
		}
	}

	public List<OrderItem> getItemList() {
		return Collections.unmodifiableList(orderItems);
	}
}
