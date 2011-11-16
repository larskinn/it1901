package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Dish;
import ntnu.it1901.gruppe4.db.DishType;
import ntnu.it1901.gruppe4.gui.ordergui.CustomerPanelItem;
import ntnu.it1901.gruppe4.gui.ordergui.OperatorOrderSummary;

/**
 * A panel containing a {@link SearchBox} for searching in a {@link MenuPanel}. 
 * 
 * @author Leo
 */
public class MenuSearchPanel extends JPanel {
	/**
	 * A container for {@link MenuPanelItem}.<br><br>
	 * 
	 * Use {@link #refresh} to add {@link Dish Dishes} to the <code>MenuPanel</code>.
	 * 
	 * @author Leo
	 */
	public class MenuPanel extends JPanel {
		private MenuPanelItem itemBeingEdited = null;
		private String prevSearchString = "";
		private OperatorOrderSummary operatorOrderSummary;
		private boolean searching = false;
		private JPanel backButton;
		private MenuPanelItem topItem = null;

		/**
		 * Constructs a new {@link MenuPanel}
		 */
		public MenuPanel() {
			this(null);
		}

		/**
		 * Constructs a new {@link MenuPanel} that will add {@link MenuPanelItem MenuItems}
		 * to the specified {@link operatorOrderSummary} when clicked.
		 * 
		 * @param operatorOrderSummary The <code>OperatorOrderSummary</code> to which clicked <code>MenuItems</code> will be added.
		 */
		public MenuPanel(OperatorOrderSummary orderSummary) {
			this.operatorOrderSummary = orderSummary;
			backButton = new JPanel();
			JLabel backText = new JLabel("< Tilbake");
			
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			backButton.setLayout(new FlowLayout(FlowLayout.LEADING));
			backButton.setBorder(Layout.menuItemPadding);
			backText.setFont(Layout.itemFont);
			
			backButton.add(backText);
			backButton.setMaximumSize(new Dimension(Short.MAX_VALUE, backButton.getPreferredSize().height));

			//backButton's function is to take the user back to the dish type overview
			backButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					addAllDishTypes();
					
					searchInput.setText("");
					searchInput.grabFocus();
				}
			});
		}

		/**
		 * Converts all dishes in the given collection to {@link MenuPanelItem} and
		 * adds them to the {@link MenuPanel}.
		 *  
		 * @param dishes The dishes to be added to the {@link OrderMenu}.
		 */
		private void addDishes(Collection<Dish> dishes) {
			if (dishes == null) {
				return;
			}
			
			int counter = 0;
			boolean topItemSet = false;
			topItem = null;
			removeAll();

			for (Dish dish : dishes) {
				if (!dish.getActive()) {
					continue;
				}
				final MenuPanelItem item = new MenuPanelItem(dish, mode, this);
				
				if (!topItemSet) {
					topItem = item;
					topItemSet = true;
				}

				//This listener is fired every time an item is clicked
				if (operatorOrderSummary != null) {
					item.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							operatorOrderSummary.addItem(item.getdish());
						}
					});
				}

				if (mode == Mode.CHEF) {
					item.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (itemBeingEdited != null && itemBeingEdited.isBeingEdited()) {
								if (itemBeingEdited == item) {
									return;
								}
								itemBeingEdited.changeFunction(false);
							}
							item.changeFunction(true);
							itemBeingEdited = item;
						}
					});
				}

				if (counter++ % 2 == 0) {
					item.setBackground(Layout.bgColor1);
				}
				else {
					item.setBackground(Layout.bgColor2);
				}
				add(item);
			}
			
			/*If the dishes have been added by selecting a dish type
			  instead of searching, add a back button */
			if (!searching) {
				if (counter % 2 == 0) {
					backButton.setBackground(Layout.bgColor1);
				}
				else {
					backButton.setBackground(Layout.bgColor2);
				}
				add(backButton);
			}
			revalidate();
			repaint();
		}
		
		/**
		 * All available <code>DishTypes</code> in the database are converted 
		 * to {@link DishTypeItem} objects and added to the {@link MenuPanel}.
		 * 
		 * @param dishType The {@link DishType} object that will converted to a {@link DishTypeItem}.
		 */
		private void addAllDishTypes() {
			removeAll();
			int counter = 0;
			searching = false;
			topItem = null;
			
			for (DishType type : DishType.values()) {
				final DishTypeItem item = new DishTypeItem(type);
				
				item.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						addDishes(DataAPI.findDishes(item.getDishType()));
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
		 * Synchronizes the <code>Dishes</code> shown in the {@link MenuPanel} with the database using the last search string specified,
		 * or the empty string if no search string was specified.
		 */
		public void refresh() {
			refresh(prevSearchString);
		}
		
		/**
		 * Synchronizes the <code>Dishes</code> shown in the {@link MenuPanel} with the database using the search string specified.
		 * <p>
		 * If the search string is empty, all categories will be added to the <code>MenuPanel</code>.
		 * 
		 * @param searchString The <code>String</code> to search the database with.
		 */
		public void refresh(String searchString) {
			if (searchString == null) {
				return;
			}
			
			if (searchString.isEmpty()) {
				addAllDishTypes();
			}
			else {
				searching = true;
				addDishes(DataAPI.findDishes(searchString));
			}
		}

		/**
		 * Getter for the topmost {@link MenuPanelItem} element of the {@link MenuPanel}.
		 * <p>
		 * This is the element that should be selected when the user presses 'enter'.
		 * 
		 * @return The <code>MenuPanelItem</code> currently on top of the {@link MenuPanel}.
		 * If there is no item currently on top of the menu, or if the topmost item is a {@link DishTypeItem},
		 * null is returned.
		 */
		public MenuPanelItem getTopItem() {
			return topItem;
		}
	} //End of inner class

	private Mode mode;
	private OperatorOrderSummary currentOrder;
	private MenuPanel orderMenu;
	private SearchBox searchInput;
	private SearchBox nameInput;
	private SearchBox priceInput;
	private JTextArea descriptionInput;
	private JButton newDish;
	private JButton createDish;
	private JButton cancel;
	private JLabel errorMessage;
	private JComboBox typeInput;

	public MenuSearchPanel(Mode mode) {
		this(mode, null);
	}

	public MenuSearchPanel(Mode mode, OperatorOrderSummary orderSummary) {
		this.mode = mode;
		currentOrder = orderSummary;

		orderMenu = new MenuPanel(currentOrder);
		searchInput = new SearchBox();
		nameInput = new SearchBox();
		priceInput = new SearchBox();
		descriptionInput = new JTextArea();
		typeInput = new JComboBox(DishType.values());
		newDish = new JButton("Ny rett");
		createDish = new JButton("Opprett ny rett");
		cancel = new JButton("Avbryt");
		errorMessage = new JLabel();

		setBorder(Layout.panelPadding);

		descriptionInput.setBorder(searchInput.getBorder());
		descriptionInput.setFont(Layout.searchBoxFont);
		descriptionInput.setRows(5);
		descriptionInput.setLineWrap(true);

		newDish.setAlignmentY(TOP_ALIGNMENT + 0.1f);
		newDish.setFont(Layout.summaryTextFont);
		createDish.setFont(Layout.summaryTextFont);
		cancel.setFont(Layout.summaryTextFont);
		errorMessage.setFont(Layout.errorFont);
		errorMessage.setForeground(Layout.errorColor);

		//Set the initial mode of the panel to searching
		changeFunction(false);

		searchInput.addKeyListener(new KeyAdapter() {
			/* keyReleased() used for searching as getText() does not return the
			 updated content of the search box when keyTyped() is called */
			@Override
			public void keyReleased(KeyEvent e) {
				//When enter is pressed, set the topmost element of the list as the selected dish
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					MenuPanelItem m = orderMenu.getTopItem();
					
					if (m != null) {
						MenuSearchPanel.this.currentOrder.addItem(m.getdish());
					}
					return;
				}
				SearchBox source = (SearchBox)e.getSource();
				String boxContent = source.getText();

				//Do the search
				orderMenu.refresh(boxContent);
			}
		});

		newDish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeFunction(true);
			}
		});

		createDish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				float price = 0;

				if (nameInput.getText().isEmpty()) {
					errorMessage.setText("Fyll inn navnet til retten du ønsker å registrere");
					return;
				}

				String priceString = priceInput.getText();

				//Use dot instead of comma as decimal seperator
				priceString = priceString.replaceAll(",", ".");

				//Try parsing
				try {
					price = Float.parseFloat(priceString);
				}
				catch (NumberFormatException nfe) {
					errorMessage.setText("Fyll inn en gyldig pris til retten du ønsker å registrere");
					return;
				}

				DishType type = (DishType)typeInput.getSelectedItem(); 
				Dish newDish = new Dish(nameInput.getText(), price, type, descriptionInput.getText(), true);
				DataAPI.saveDish(newDish);

				searchInput.setText("");
				changeFunction(false);
			}
		});

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeFunction(false);
			}
		});
	}

	/**
	 * Changes the layout of the {@link MenuSearchPanel} to either support adding new customers or searching
	 * for existing ones.
	 * 
	 * @param addingDish True if the <code>MenuSearchPanel's</code> function is to add new dishes,
	 * or false if it is to be used for searching for existing ones.
	 */
	public void changeFunction(boolean addingDish) {
		removeAll();

		if (!addingDish) {
			//Reload all dishes from the database and add them to the list
			orderMenu.refresh("");

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			errorMessage.setText(" ");

			//If mode is set to delivery, we don't the option of adding new dishes
			if (mode == Mode.ORDER) {
				add(searchInput);
			}
			else if (mode == Mode.CHEF) {
				/*Horizontal box used to place the new customer button to the right
				  of the search field with three spaces in between them*/
				Box horizontalBox = Box.createHorizontalBox();
				horizontalBox.add(searchInput);
				horizontalBox.add(new JLabel("  "));
				horizontalBox.add(newDish);
				add(horizontalBox);
			}
			add(Box.createVerticalStrut(Layout.spaceAfterSearchBox));

			//Wrap the menu list inside a JScrollPane
			JScrollPane sp = new JScrollPane(orderMenu);
			sp.setBorder(null);
			add(sp);

			searchInput.grabFocus();
		}
		else {
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			JLabel namePrefix = new JLabel("Navn: ");
			JLabel pricePrefix = new JLabel("Pris: ");
			JLabel descriptionPrefix = new JLabel("Beskrivelse: ");
			JLabel priceSuffix = new JLabel(" kr");
			JLabel typePrefix = new JLabel("Type: ");

			namePrefix.setFont(Layout.summaryTextFont);
			pricePrefix.setFont(Layout.summaryTextFont);
			descriptionPrefix.setFont(Layout.summaryTextFont);
			priceSuffix.setFont(Layout.summaryTextFont);
			typePrefix.setFont(Layout.summaryTextFont);

			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.BASELINE_LEADING;
			gbc.weightx = 0;
			gbc.weighty = 1;
			gbc.gridy = 0;
			gbc.gridx = 0;

			//Insert name information as the first row
			add(namePrefix, gbc);

			gbc.weightx = 1;
			gbc.gridx++;
			add(nameInput, gbc);

			//Insert price information
			gbc.weightx = 0;
			gbc.gridy++;
			gbc.gridx = 0;
			add(pricePrefix, gbc);

			gbc.weightx = 1;
			gbc.gridx++;
			add(priceInput, gbc);

			gbc.weightx = 0;
			gbc.gridx++;
			add(priceSuffix, gbc);

			//Insert description
			gbc.weightx = 0;
			gbc.gridy++;
			gbc.gridx = 0;
			add(descriptionPrefix, gbc);

			gbc.weightx = 1;
			gbc.gridx++;
			add(descriptionInput, gbc);
			
			//Insert dish type selection box
			gbc.weightx = 0;
			gbc.gridy++;
			gbc.gridx = 0;
			add(typePrefix, gbc);
			
			gbc.weightx = 1;
			gbc.gridx++;
			add(typeInput, gbc); 

			//Insert buttons
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.fill = GridBagConstraints.NONE;
			gbc.gridy++;
			gbc.gridx = 1;
			add(cancel, gbc);

			gbc.anchor = GridBagConstraints.NORTHEAST;
			add(createDish, gbc);

			//Insert empty error label
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weighty = Layout.newDishDensity;
			gbc.gridy++;
			gbc.gridx = 1;
			add(errorMessage, gbc);

			//Insert default values
			nameInput.setText(searchInput.getText());
			priceInput.setText("");
			descriptionInput.setText("");
			nameInput.grabFocus();
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
}