package ntnu.it1901.gruppe4.ordergui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	JButton menu, customer, address, done;
	
	public ButtonPanel() {
		menu = new JButton("Meny (F1)");
		customer = new JButton("Kunde (F2)");
		address = new JButton("Adresse (F3)");
		done = new JButton("Ferdig (F4)");
		done.setEnabled(false);
		
		Dimension buttonSize = new Dimension(100, 50);
		menu.setPreferredSize(buttonSize);
		customer.setPreferredSize(buttonSize);
		address.setPreferredSize(buttonSize);
		done.setPreferredSize(buttonSize);
		
		setLayout(new GridLayout(1, 4));
		add(menu);
		add(customer);
		add(address);
		add(done);
	}
}
