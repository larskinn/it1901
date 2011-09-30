package ntnu.it1901.gruppe4.ordergui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Customer;

public class CustomerPanelItem extends JPanel {
	private Customer customer;
	private JLabel name, number;
	
	public CustomerPanelItem(Customer customer) {
		setLayout(new GridLayout(2, 1)); //Wrong layout. Change this later
		this.customer = customer;
		name = new JLabel(customer.getName());
		number = new JLabel(customer.getPhone());
		
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
		
		add(name);
		add(number);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(CustomerPanelItem.this.customer.getName() + " selected");
			}
		});
	}
	
	public Customer getCustomer() {
		return customer;
	}
}
