package ntnu.it1901.gruppe4.ordergui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;

public class CustomerPanel extends JPanel {
	SearchBox nameInput;
	SearchBox numberInput;
	CustomerList customerList;
	private SearchBoxListener listener;

	public class CustomerList extends JPanel {
		/**
		 * Creates a new CustomerList. Only the CustomerPanel is allowed to do this.
		 */
		private CustomerList() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBackground(Color.DARK_GRAY);
		}
		
		/**
		 * Converts all customers in the given collection to {@link CustomerPanelItem},
		 * adds them to the {@link CustomerList} and repaints the panel.
		 *  
		 * @param customers The customers to be added to the {@link CustomerList}.
		 */
		public void addCustomers(Collection<Customer> customers) {
			removeAll();
			
			for (Customer customer : customers) {
				add(new CustomerPanelItem(customer));
			}
			revalidate();
			repaint();
		}
	}
	
	private class SearchBoxListener extends KeyAdapter {
		/*keyReleased() used for searching as getText() does not return the
		 *updated content of the search box when keyTyped() is called 
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			SearchBox source = (SearchBox)e.getSource();
			String boxContent = source.getText();
			
			//If the search box is empty, interrupt and restore the list of results
			if (boxContent.equals("")) {
				customerList.addCustomers(DataAPI.findCustomers(""));
				return;
			}
			
			/* If the first character in the name box is a number,
			 * or the first character in the number box is a letter,
			 * reverse the functions of the search boxes
			 */
			if ((source == nameInput && Character.isDigit(boxContent.charAt(0)))
					|| (source == numberInput && 
					(Character.isLetter(boxContent.charAt(0)) || boxContent.charAt(0) == ' '))) {
				SearchBox tmp = numberInput;
				numberInput = nameInput;
				nameInput = tmp;
			}
			
			//Search for name / number using the DataAPI
			customerList.addCustomers(DataAPI.findCustomers(boxContent));
		}
	}

	public CustomerPanel() {
		nameInput = new SearchBox();
		numberInput = new SearchBox();
		listener = new SearchBoxListener();
		customerList = new CustomerList();

		//Add all the customers to the list
		customerList.addCustomers(DataAPI.findCustomers(""));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(nameInput);
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(numberInput);
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(customerList);
		
		customerList.setPreferredSize(new Dimension(999, 999));

		nameInput.addKeyListener(listener);
		numberInput.addKeyListener(listener);
	}
}
