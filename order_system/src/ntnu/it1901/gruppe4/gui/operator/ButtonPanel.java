package ntnu.it1901.gruppe4.gui.operator;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.gui.Layout;

/**
 * A panel that contains the tabs used in {@link OrderWindow}.
 * 
 * @author Leo
 */
public class ButtonPanel extends JPanel {
	final JButton menu, customer, history;

	/**
	 * Constructs a new {@link ButtonPanel}.
	 * 
	 * @param buttonListeners The listeners that will be called
	 * when a tab in this panel is clicked.
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