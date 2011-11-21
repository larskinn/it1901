package ntnu.it1901.gruppe4.gui.operator;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.gui.Layout;

/**
 * A panel of 3 buttons, as used in the bottom of {@Link OrderWindow}.
 * The buttons are Menu (F1), Customer (F2) and Order History (F3).
 * 
 * @author LeoMartin
 * 
 */
public class ButtonPanel extends JPanel {
	final JButton menu, customer, history;

	/**
	 * Public constructor.
	 * 
	 * @param buttonListeners
	 *            A list of {@Link ActionListener} objects to bind to the
	 *            three buttons.
	 */
	public ButtonPanel(ActionListener... buttonListeners) {
		menu = new JButton("Meny (F1)");
		customer = new JButton("Kunde (F2)");
		history = new JButton("Ordre (F3)");

		for (ActionListener listener : buttonListeners) {
			menu.addActionListener(listener);
			customer.addActionListener(listener);
			history.addActionListener(listener);
		}
		menu.setPreferredSize(Layout.buttonSize);
		customer.setPreferredSize(Layout.buttonSize);
		history.setPreferredSize(Layout.buttonSize);

		setLayout(new GridLayout(1, 4));
		add(menu);
		add(customer);
		add(history);
	}
}