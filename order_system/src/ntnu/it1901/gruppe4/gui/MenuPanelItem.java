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

/**
 * A panel that shows information about a {@link Dish}.
 * 
 * @author Leo
 */
public class MenuPanelItem extends JPanel {
	private boolean beingEdited;
	private Dish item;
	private JLabel name, price, priceSuffix, description, errorMessage;
	private JTextField nameInput, priceInput;
	private JComboBox typeInput;
	private JTextArea descriptionInput;
	private JButton save;

	/**
	 * Creates a new {@link MenuPanelItem} containing the specified {@link Dish} within.
	 *  
	 * @param dish The <code>Dish</code> to be associated with the new <code>MenuPanelItem</code>.
	 */
	public MenuPanelItem(Dish dish, Mode mode) {
		this.item = dish;
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

		name.setFont(Layout.itemFont);
		price.setFont(Layout.itemFont);
		priceSuffix.setFont(Layout.itemFont);
		description.setFont(Layout.itemDescriptionFont);
		nameInput.setFont(Layout.itemFont);
		priceInput.setFont(Layout.itemFont);
		errorMessage.setFont(Layout.errorFont);
		errorMessage.setForeground(Layout.errorColor);

		descriptionInput.setFont(Layout.itemDescriptionFont);
		descriptionInput.setBorder(nameInput.getBorder());
		descriptionInput.setLineWrap(true);

		save.setFont(Layout.itemFont);

		setBorder(Layout.menuItemPadding);
		setLayout(new GridBagLayout());
		changeMode(false);

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
				changeMode(false);
			}
		});
	}

	public void changeMode(boolean editing) {
		beingEdited = editing;
		
		if (editing) {
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
			//Todo: Move "magic number" to some constant
			add(Box.createHorizontalStrut(20), gbc);
			
			gbc.gridx++;
			add(typeInput, gbc);
			
			gbc.gridx++;
			//Todo: Move "magic number" to some constant
			add(Box.createHorizontalStrut(20), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			add(priceInput, gbc);

			gbc.gridx++;
			gbc.weightx = 0;
			add(priceSuffix, gbc);

			//Todo: Move "magic number" to some constant
			gbc.gridy++;
			add(Box.createVerticalStrut(15), gbc);

			gbc.gridy++;
			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.WEST;
			add(descriptionInput, gbc);

			gbc.gridx += 4;
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

			//Todo: Move "magic number" to some constant
			gbc.gridy++;
			add(Box.createVerticalStrut(15), gbc);

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