package ntnu.it1901.gruppe4.chefgui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.gui.Layout;

public class ButtonPanel extends JPanel {
	final JButton orders, menu;
	
	public ButtonPanel(ActionListener...buttonListeners) {
		orders = new JButton("Ordre (F1)");
		menu = new JButton("Meny (F2)");

		for (ActionListener listener : buttonListeners) {
			orders.addActionListener(listener);
			menu.addActionListener(listener);
		}	
		orders.setPreferredSize(Layout.buttonSize);
		menu.setPreferredSize(Layout.buttonSize);
		
		setLayout(new GridLayout(1, 4));
		add(orders);
		add(menu);
	}
}