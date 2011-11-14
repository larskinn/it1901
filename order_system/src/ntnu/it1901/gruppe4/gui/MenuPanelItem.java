package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ntnu.it1901.gruppe4.db.Dish;
import ntnu.it1901.gruppe4.db.DishType;
import ntnu.it1901.gruppe4.gui.MenuSearchPanel.MenuPanel;

/**
 * A panel that shows information about a {@link Dish}.
 * 
 * @author Leo
 */
public class MenuPanelItem extends JPanel {
	private final int HGAP = 20, VGAP = 15;
	
	private boolean beingEdited;
	private boolean deleting = false;
	private Dish item;
	private MenuPanel menuPanel;
	private JLabel name, price, priceSuffix, description, errorMessage, confirmMessage;
	private JTextField nameInput, priceInput;
	private JComboBox typeInput;
	private JTextArea descriptionInput;
	private JButton save, delete;

	/**
	 * Creates a new {@link MenuPanelItem} containing the specified {@link Dish} within.
	 *  
	 * @param dish The <code>Dish</code> to be associated with the new <code>MenuPanelItem</code>.
	 * @param mode The {@link Mode} which specifies in which window this <code>MenuPanelItem</code> is shown.
	 * @param menuPanel The {@link MenuPanel} which will be updated when a <code>Dish</code> is deleted.
	 */
	public MenuPanelItem(Dish dish, Mode mode, MenuPanel menuPanel) {
		this.item = dish;
		this.menuPanel = menuPanel;
		name = new JLabel(item.getName());
		price = new JLabel();
		description = new JLabel();
		priceSuffix = new JLabel(" kr");
		errorMessage = new JLabel(" ");
		nameInput = new JTextField();
		priceInput = new JTextField();
		descriptionInput = new JTextArea();
		typeInput = new JComboBox(DishType.values());
		save = new JButton("Lagre");
		delete = new JButton("Slett");
		confirmMessage = new JLabel();

		name.setFont(Layout.itemFont);
		price.setFont(Layout.itemFont);
		priceSuffix.setFont(Layout.itemFont);
		description.setFont(Layout.itemDescriptionFont);
		nameInput.setFont(Layout.itemFont);
		priceInput.setFont(Layout.itemFont);
		confirmMessage.setFont(Layout.itemFont);
		errorMessage.setFont(Layout.errorFont);
		errorMessage.setForeground(Layout.errorColor);

		descriptionInput.setFont(Layout.itemDescriptionFont);
		descriptionInput.setBorder(nameInput.getBorder());
		descriptionInput.setLineWrap(true);

		save.setFont(Layout.itemFont);
		delete.setFont(Layout.itemFont);

		setBorder(Layout.menuItemPadding);
		setLayout(new GridBagLayout());
		changeFunction(false);

		//To prevent this component's height from growing
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameInput.getText();
				String price = priceInput.getText();
				String description = descriptionInput.getText();
				float newPrice = 0;
				
				//Use dot instead of comma as decimal seperator
				price = price.replaceAll(",", ".");
				
				if (name.isEmpty()) {
					errorMessage.setText("Ugyldig navn");
					return;
				}
				
				if (price.isEmpty()) {
					errorMessage.setText("Ugyldig pris");
					return;
				}
				
				try {
					newPrice = Float.parseFloat(price);
				}
				catch (NumberFormatException nfe) {
					errorMessage.setText("Ugyldig pris");
					return;
				}
				item.setName(name);
				item.setPrice(newPrice);
				item.setType((DishType)typeInput.getSelectedItem());
				item.setDescription(description);
				changeFunction(false);
			}
		});
		
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (deleting) {
					item.setActive(false);
					item.save();
					MenuPanelItem.this.menuPanel.refresh();
				}
				else {
					confirmMessage.setText("Sikker?");
					delete.setText("Ja");
					deleting = true;
				}
			}
		});
	}

	/**
	 * Changes the function of this {@link MenuPanelItem} to either edit the description
	 * of the contained {@link Order} or just showing it.
	 * 
	 * @param editing If the description of the current <code>Order</code> should be edited.
	 */
	public void changeFunction(boolean editing) {
		deleting = false;
		beingEdited = editing;
		
		if (editing) {
			confirmMessage.setText("");
			delete.setText("Slett");
			deleting = false;
			removeAll();

			nameInput.setText(item.getName());
			priceInput.setText(Layout.decimalFormat.format(item.getPrice()));
			descriptionInput.setText(item.getDescription());
			typeInput.setSelectedItem(item.getType());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridy = 0;
			gbc.gridx = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.WEST;
			add(nameInput, gbc);
			
			gbc.gridx++;
			add(Box.createHorizontalStrut(HGAP), gbc);
			
			gbc.gridx++;
			add(typeInput, gbc);
			
			gbc.gridx++;
			add(Box.createHorizontalStrut(HGAP), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			add(priceInput, gbc);

			gbc.gridx++;
			gbc.weightx = 0;
			add(priceSuffix, gbc);

			gbc.gridy++;
			add(Box.createVerticalStrut(VGAP), gbc);

			gbc.gridy++;
			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.WEST;
			add(descriptionInput, gbc);
			
			gbc.fill = GridBagConstraints.NONE;
			gbc.gridx += 2;
			add(confirmMessage, gbc);
			
			gbc.anchor = GridBagConstraints.NORTHEAST;
			add(delete, gbc);
			
			gbc.gridx += 2;
			gbc.gridwidth = 2;
			add(save, gbc);
			
			gbc.gridy++;
			add(errorMessage, gbc);

			nameInput.grabFocus();

			//Remove buttom padding from border
			Insets borderInsets = Layout.menuItemPadding.getBorderInsets(this);
			borderInsets.bottom = 0;
			setBorder(BorderFactory.createEmptyBorder(borderInsets.top, borderInsets.left,
						borderInsets.bottom, borderInsets.right));
			
			setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
		}
		else {
			removeAll();

			errorMessage.setText(" ");
			name.setText(item.getName());
			price.setText(Layout.decimalFormat.format(item.getPrice()));
			description.setText(item.getDescription());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridy = 0;
			gbc.gridx = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.anchor = GridBagConstraints.WEST;
			add(name, gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			add(price, gbc);

			gbc.gridx++;
			gbc.weightx = 0;
			add(priceSuffix, gbc);

			gbc.gridy++;
			add(Box.createVerticalStrut(VGAP), gbc);

			gbc.gridy++;
			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.WEST;
			add(description, gbc);

			//Set bottom padding to the height of the error message to avoid "shaking" when editing an item
			Insets borderInsets = Layout.menuItemPadding.getBorderInsets(this);
			borderInsets.bottom = errorMessage.getPreferredSize().height;
			setBorder(BorderFactory.createEmptyBorder(borderInsets.top, borderInsets.left,
						borderInsets.bottom, borderInsets.right));
			
			setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
		}
		revalidate();
		repaint();
	}

	/**
	 * Returns the {@link Dish} contained within this <code>MenuPanelItem</code>.
	 * 
	 * @return The <code>Dish</code> contained within this <code>MenuPanelItem</code>.
	 */
	public Dish getdish() {
		return item;
	}
	
	public boolean isBeingEdited() {
		return beingEdited;
	}
}