package ntnu.it1901.gruppe4.gui.chef;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.gui.Layout;

/**
 * A panel that contains the tabs used in {@link ChefWindow}.
 * 
 * @author Leo
 */
public class ButtonPanel extends JPanel {
	final JButton orders, menu;
	
	/**
	 * Constructs a new {@link ButtonPanel}.
	 * 
	 * @param buttonListeners The listeners that will be called
	 * when a tab in this panel is clicked.
	 */
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