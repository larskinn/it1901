package ntnu.it1901.gruppe4.gui.operator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.SearchBox;

/**
 * A panel containing a {@link SearchBox} for searching in a contained {@link CustomerList} that can also
 * create new {@link Customer} objects.
 * 
 * @author Leo
 */
public class CustomerPanel extends JPanel {
	/**
	 * A container for {@link CustomerPanelItem}.
	 * <p>
	 * Use {@link #refresh} to add {@link Customer Customers} to the <code>CustomerList</code>.
	 * 
	 * @author Leo
	 */
	class CustomerList extends JPanel {
		private CustomerPanelItem topItem = null;
		private CustomerPanelItem itemBeingEdited;
		private String prevSearchString = "";

		/**
		 * Creates a new CustomerList containing a list of {@link CustomerPanelItem CustomerPanelItems}
		 * which is initially empty.
		 */
		CustomerList() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}

		/**
		 * Converts all <code>Customers</code> in the given collection to {@link CustomerPanelItem},
		 * adds them to the {@link CustomerList} and repaints the panel.
		 *  
		 * @param customers The customers to be added to the {@link CustomerList}.
		 */
		private void addCustomers(Collection<Customer> customers) {
			topItem = null;
			boolean topItemSet = false;
			int counter = 0;
			removeAll();

			for (final Customer customer : customers) {
				final CustomerPanelItem item = new CustomerPanelItem(customer, currentOrder, this);
				
				if (!topItemSet) {
					topItem = item;
					topItemSet = true;
				}

				//Activated when a customer panel item is clicked
				item.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						CustomerPanel.this.currentOrder.setCustomer(customer);
					}
				});

				item.addEditButtonListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//If another item is already being edited, cancel the editing of that item
						if (itemBeingEdited != null && itemBeingEdited.isBeingEdited()) {
							itemBeingEdited.changeFunction(false);
						}
						item.changeFunction(true);
						itemBeingEdited = item;
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
		
		/**
		 * Synchronizes the <code>Customers</code> shown in the {@link CustomerList} with the database using the last search string specified,
		 * or the empty string if no search string was specified.
		 */
		public void refresh() {
			refresh(prevSearchString);
		}
		
		/**
		 * Synchronizes the <code>Customers</code> shown in the {@link CustomerList} with the database using the search string specified.
		 * 
		 * @param searchString The <code>String</code> to search the database with.
		 */
		public void refresh(String searchString) {
			if (searchString == null) {
				return;
			}
			prevSearchString = searchString;
			addCustomers(DataAPI.findCustomers(searchString));
		}
		
		/**
		 * Getter for the topmost element of the {@link CustomerPanel}.
		 * <p>
		 * This is the element that should be selected when the user presses 'enter'.
		 * 
		 * @return The {@link CustomerPanelItem} currently on top of the {@link CustomerPanel}.
		 */
		public CustomerPanelItem getTopItem() {
			return topItem;
		}
	} //End of inner class
	
	CustomerList customerList;

	private boolean addingNewCustomer;
	private SearchBox searchInput;
	private SearchBox nameInput;
	private SearchBox numberInput;
	private SearchBox addressInput;
	private SearchBox postNoInput;
	private JButton newCustomer;
	private JButton createCustomer;
	private JButton cancel;
	private JLabel errorMessage;
	private OperatorOrderSummary currentOrder;
	
	/**
	 * Constructs a new {@link CustomerPanel}.
	 */
	public CustomerPanel(OperatorOrderSummary orderSummary) {
		currentOrder = orderSummary;
		customerList = new CustomerList();
		searchInput = new SearchBox();
		nameInput = new SearchBox();
		numberInput = new SearchBox();
		addressInput = new SearchBox();
		postNoInput = new SearchBox();
		newCustomer = new JButton("Ny kunde");
		createCustomer = new JButton("Lagre (Enter)");
		cancel = new JButton("Avbryt (Esc)");
		errorMessage = new JLabel();

		setBorder(Layout.panelPadding);
		newCustomer.setAlignmentY(TOP_ALIGNMENT + 0.1f);
		newCustomer.setFont(Layout.summaryTextFont);
		createCustomer.setFont(Layout.summaryTextFont);
		cancel.setFont(Layout.summaryTextFont);
		errorMessage.setFont(Layout.errorFont);
		errorMessage.setForeground(Layout.errorColor);

		//Set the initial mode of the panel to searching
		changeFunction(false);

		searchInput.addKeyListener(new KeyAdapter() {
			/*keyReleased() used for searching as getText() does not return the
			 *updated content of the search box when keyTyped() is called 
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				//When enter is pressed, set the topmost element of the list as the selected customer
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					CustomerPanelItem c = customerList.getTopItem();
					
					if (c != null) {
						currentOrder.setCustomer(c.getCustomer());
					}
					return;
				}
				SearchBox source = (SearchBox)e.getSource();
				String boxContent = source.getText();

				//Search for name and number using the DataAPI
				customerList.refresh(boxContent);
			}
		});

		newCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeFunction(true);
			}
		});

		createCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveCustomer();
			}

		});

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeFunction(false);
			}
		});
		
		/*Adds a key listener to the customer panel.
		 * Enter will save the new customer, escape will cancel the editing 
		 */
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (!addingNewCustomer || e.getID() != KeyEvent.KEY_RELEASED) {
							return false;
						}
						
						switch (e.getKeyCode()) {
							case KeyEvent.VK_ENTER:
								saveCustomer();
								break;
							case KeyEvent.VK_ESCAPE:
								changeFunction(false);
								break;
						}
						return false;
					}
				});
	}

	/**
	 * Saves the new <code>Customer</code> object to the database and changes the panel's function to searching.
	 */
	private void saveCustomer() {
		if (nameInput.getText().isEmpty()) {
			errorMessage.setText("Fyll inn navnet til kunden du ønsker å registrere");
		}
		else if (numberInput.getText().isEmpty()) {
			errorMessage.setText("Fyll inn telefonnummeret til kunden du ønsker å registrere");
		}
		else if (addressInput.getText().isEmpty()) {
			errorMessage.setText("Fyll inn adressen til kunden du ønsker å registrere");
		}
		else if (postNoInput.getText().isEmpty()) {
			errorMessage.setText("Fyll inn postnummeret til kunden du ønsker å registrere");
		}
		else {
			int postNo = 0;

			try {
				postNo = Integer.parseInt(postNoInput.getText());
			}
			catch (NumberFormatException exception) {
				errorMessage.setText("Fyll inn et gyldig postnummer til kunden du ønsker å registrere");
				return;
			}
			Customer newCustomer = new Customer(nameInput.getText(), numberInput.getText());
			DataAPI.saveCustomer(newCustomer);

			Address newAddress = new Address(newCustomer, addressInput.getText(), postNo);
			DataAPI.saveAddress(newAddress);

			currentOrder.setCustomer(newCustomer);
			searchInput.setText("");
			changeFunction(false);
		}
	}

	/**
	 * Changes the layout of the {@link CustomerPanel} to either support adding new customers or searching
	 * for existing ones.
	 * 
	 * @param addingCustomer True if the <code>CustomerPanel</code> is to be used for adding new customers,
	 * false if it is to be used for searching for existing customers.
	 */
	private void changeFunction(boolean addingCustomer) {
		addingNewCustomer = addingCustomer;
		removeAll();

		if (addingCustomer) {
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			JLabel name = new JLabel("Navn: ");
			JLabel phone = new JLabel("Telefonnummer: ");
			JLabel address = new JLabel("Adresse: ");
			JLabel postNo = new JLabel("Postnummer: ");

			name.setFont(Layout.summaryTextFont);
			phone.setFont(Layout.summaryTextFont);
			address.setFont(Layout.summaryTextFont);
			postNo.setFont(Layout.summaryTextFont);

			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.BASELINE_LEADING;
			gbc.weightx = 0;
			gbc.weighty = 1;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(name, gbc);

			gbc.gridy++;
			add(phone, gbc);

			gbc.gridy++;
			add(address, gbc);

			gbc.gridy++;
			add(postNo, gbc);

			gbc.gridwidth = 2;
			gbc.gridx++;
			gbc.gridy = 0;
			gbc.weightx = 1;
			add(nameInput, gbc);

			gbc.gridy++;
			add(numberInput, gbc);

			gbc.gridy++;
			add(addressInput, gbc);

			gbc.gridy++;
			add(postNoInput, gbc);

			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.fill = GridBagConstraints.NONE;
			gbc.gridy++;
			add(cancel, gbc);

			gbc.anchor = GridBagConstraints.NORTHEAST;
			gbc.gridx++;
			add(createCustomer, gbc);

			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weighty = Layout.newCustomerDensity;
			gbc.gridy++;
			gbc.gridx--;
			add(errorMessage, gbc);

			nameInput.setText(searchInput.getText());
			numberInput.setText("");
			addressInput.setText("");
			postNoInput.setText("");
			nameInput.grabFocus();
		}
		else {
			//Reload all customers from the database and add them to the list
			customerList.refresh("");

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			errorMessage.setText(" ");

			//Box used to place the new customer button to the right of the name input box
			//with three spaces between them
			Box horizontalBox = Box.createHorizontalBox();
			horizontalBox.add(searchInput);
			horizontalBox.add(new JLabel("  "));
			horizontalBox.add(newCustomer);
			add(horizontalBox);
			add(Box.createVerticalStrut(Layout.spaceAfterSearchBox));

			//Wrap the customer list inside a JScrollPane
			JScrollPane sp = new JScrollPane(customerList);
			sp.setBorder(null);
			sp.getVerticalScrollBar().setUnitIncrement(Layout.scrollBarSpeed);
			add(sp);

			searchInput.grabFocus();
		}
		revalidate();
		repaint();
	}

	/**
	 * Moves the user's cursor to the search box.
	 */
	@Override
	public void grabFocus() {
		searchInput.grabFocus();
	}
	
	/**
	 * Clears all text from the search box and restores the list of <code>Customers</code>.
	 */
	public void clearSearchBox() {
		searchInput.setText("");
		customerList.refresh("");
		grabFocus();
	}
}