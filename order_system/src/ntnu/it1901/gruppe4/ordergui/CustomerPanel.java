package ntnu.it1901.gruppe4.ordergui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;

public class CustomerPanel extends JPanel {
	SearchBox nameInput;
	SearchBox numberInput;
	CustomerList customerList;
	private SearchBoxListener listener;
	private OrderSummary currentOrder;

	public class CustomerList extends JPanel {
		/**
		 * Creates a new CustomerList. Only the CustomerPanel is allowed to do this.
		 */
		private CustomerList() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}
		
		/**
		 * Converts all customers in the given collection to {@link CustomerPanelItem},
		 * adds them to the {@link CustomerList} and repaints the panel.
		 *  
		 * @param customers The customers to be added to the {@link CustomerList}.
		 */
		public void addCustomers(Collection<Customer> customers) {
			int counter = 0;
			removeAll();
			
			for (final Customer customer : customers) {
				CustomerPanelItem item = new CustomerPanelItem(customer);
				
				//Fired whenever a customer panel item is clicked
				item.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						CustomerPanel.this.currentOrder.setCustomer(customer);
					}
				});
				
				if (counter++ % 2 == 0) {
					item.setBackground(Layout.bgColor1);
				}
				else {
					item.setBackground(Layout.bgColor2);
				}
				add(item);
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

	public CustomerPanel(OrderSummary orderSummary) {
		currentOrder = orderSummary;
		nameInput = new SearchBox();
		numberInput = new SearchBox();
		listener = new SearchBoxListener();
		customerList = new CustomerList();

		setBorder(Layout.panelPadding);
		nameInput.setFont(Layout.searchBoxFont);
		numberInput.setFont(Layout.searchBoxFont);
		
		//Add all the customers to the list
		customerList.addCustomers(DataAPI.findCustomers(""));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(nameInput);
		add(Box.createVerticalStrut(Layout.spaceAfterSearchBox));
//		add(numberInput);
		
		JScrollPane sp = new JScrollPane(customerList);
		sp.setBorder(null);
		add(sp);

		nameInput.addKeyListener(listener);
		numberInput.addKeyListener(listener);
	}
	
	@Override
	public void grabFocus() {
		nameInput.grabFocus();
	}
}
