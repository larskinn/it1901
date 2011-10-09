package ntnu.it1901.gruppe4.ordergui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Customer;

public class CustomerPanelItem extends JPanel {
	private Customer customer;
	private JLabel text;
	
	public CustomerPanelItem(Customer customer) {
		this.customer = customer;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		text = new JLabel("<html><table><tr><td>Navn:" +
							"</td><td>" + customer.getName() +
							"</td></tr><tr><td>Nummer:" +
							"</td><td>" + customer.getPhone() +
							"</td></tr></table></html>");
		
		add(text);
		
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
	}
	
	public Customer getCustomer() {
		return customer;
	}
}