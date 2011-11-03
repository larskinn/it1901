package ntnu.it1901.gruppe4.gui.ordergui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.gui.Layout;

/**
 * A panel used in {@link OrderWindow to display details about the customer.
 * @author LeoMartin
 *
 */
public class CustomerPanelItem extends JPanel {
	private Customer customer;
	private JLabel text;

	/**
	 * Public constructor. A {@link Customer} object is needed.
	 * 
	 * @param customer
	 *            {@link Customer} object containing name and phone number.
	 */
	public CustomerPanelItem(Customer customer) {
		this.customer = customer;

		setBorder(Layout.customerItemPadding);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		text = new JLabel("<html><table><tr><td>Navn:" + "</td><td>"
				+ customer.getName() + "</td></tr><tr><td>Nummer:"
				+ "</td><td>" + customer.getPhone()
				+ "</td></tr></table></html>");
		text.setFont(Layout.itemFont);

		add(text);

		// To prevent this component's height from growing
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}

	/**
	 * Returns the {@link Customer} object associated with this panel.
	 * 
	 * @return a {@link Customer} object.
	 */
	public Customer getCustomer() {
		return customer;
	}
}