package ntnu.it1901.gruppe4.ordergui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	final JButton menu, customer, history;
	
	public ButtonPanel(ActionListener...buttonListeners) {
		menu = new JButton("Meny (F1)");
		customer = new JButton("Kunde (F2)");
		history = new JButton("Order History (F3)");

		for (ActionListener listener : buttonListeners) {
			menu.addActionListener(listener);
			customer.addActionListener(listener);
			history.addActionListener(listener);
		}
		
		Dimension buttonSize = new Dimension(100, 50);
		menu.setPreferredSize(buttonSize);
		customer.setPreferredSize(buttonSize);
		history.setPreferredSize(buttonSize);
		
		setLayout(new GridLayout(1, 4));
		add(menu);
		add(customer);
		add(history);
	}
}