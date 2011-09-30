package ntnu.it1901.gruppe4.ordergui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Address;

public class AddressPanelItem extends JPanel {
	private Address address;
	private JLabel addressLine, registeredTo;
	
	public AddressPanelItem(Address address) {
		setLayout(new GridLayout(2, 1)); //Wrong layout. Change this later
		this.address = address;
		addressLine = new JLabel(address.getAddressLine() + ", " + address.getPostalCode());
		registeredTo = new JLabel("Registered to: " + address.getIdCustomer().getName());
		
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
		
		add(addressLine);
		add(registeredTo);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(AddressPanelItem.this.address.getAddressLine() + " selected");
			}
		});
	}
	
	public Address getAddress() {
		return address;
	}
}
