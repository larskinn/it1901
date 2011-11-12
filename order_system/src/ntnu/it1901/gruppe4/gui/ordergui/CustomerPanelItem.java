package ntnu.it1901.gruppe4.gui.ordergui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.Layout;

/**
 * A panel used in {@link OrderWindow to display details about the customer.
 * @author Leo
 */
public class CustomerPanelItem extends JPanel {
	private Customer customer;
	private JLabel prefixes, info, errorMessage;
	private JTextField nameInput, numberInput, addressInput, postNoInput;
	private JButton save;
	private boolean beingEdited;

	/**
	 * Public constructor. A {@link Customer} object is needed.
	 * 
	 * @param customer
	 *            {@link Customer} object containing name and phone number.
	 */
	public CustomerPanelItem(Customer customer) {
		this.customer = customer;
		nameInput = new JTextField();
		numberInput = new JTextField();
		addressInput = new JTextField();
		postNoInput = new JTextField();
		save = new JButton("Lagre");
		prefixes = new JLabel("<html><table>" +
									"<tr><td>Navn:</td></tr>" +
									"<tr><td>Nummer:</td></tr>" +
									"<tr><td>Adresse:</td></tr>" +
									"<tr><td>Postnummer:</td></tr>" +
								"</table></html>");
		info = new JLabel();
		errorMessage = new JLabel(" ");

		setBorder(Layout.customerItemPadding);
		setLayout(new GridBagLayout());
		
		nameInput.setFont(Layout.itemFont);
		numberInput.setFont(Layout.itemFont);
		addressInput.setFont(Layout.itemFont);
		postNoInput.setFont(Layout.itemFont);
		save.setFont(Layout.itemFont);
		prefixes.setFont(Layout.itemFont);
		info.setFont(Layout.itemFont);
		errorMessage.setFont(Layout.errorFont);
		errorMessage.setForeground(Layout.errorColor);
		
		changeFunction(false);
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameInput.getText();
				String number = numberInput.getText();
				String address = addressInput.getText();
				String postNo = postNoInput.getText();
				int newPostNo = 0;
				
				if (name.isEmpty()) {
					errorMessage.setText("Ugyldig navn");
					return;
				}
				else if (number.isEmpty()) {
					errorMessage.setText("Ugyldig nummer");
					return;
				}
				else if (address.isEmpty()) {
					errorMessage.setText("Ugyldig adresse");
					return;
				}
				else if (postNo.isEmpty()) {
					errorMessage.setText("Ugyldig postnummer");
					return;
				}
				
				try {
					newPostNo = Integer.parseInt(postNo);
				}
				catch (NumberFormatException nfe) {
					errorMessage.setText("Ugyldig postnummer");
					return;
				}
				CustomerPanelItem.this.customer.setName(name);
				CustomerPanelItem.this.customer.setPhone(number);
				changeFunction(false);
				
				//FIXME: OrderSummary needs to be updated when the customer is saved
			}
		});

		// To prevent this component's height from growing
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}
	
	/**
	 * Changes the function of this {@link MenuPanelItem} to either edit the description
	 * of the contained {@link Order} or just showing it.
	 * 
	 * @param editing If the description of the current <code>Order</code> should be edited.
	 */
	public void changeFunction(boolean editing) {
		beingEdited = editing;
		
		if (editing) {
			removeAll();

			nameInput.setText(customer.getName());
			numberInput.setText(customer.getPhone());
			addressInput.setText(DataAPI.getAddresses(customer).get(0).getAddressLine());
			postNoInput.setText(Integer.toString(DataAPI.getAddresses(customer).get(0).getPostalCode()));
			errorMessage.setText(" ");

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weighty = 1;
			gbc.weightx = gbc.gridy = gbc.gridx = 0;
			gbc.gridheight = 4;
			add(prefixes, gbc);
			
			gbc.weightx = 1;
			gbc.gridx++;
			gbc.gridheight = 1;
			add(nameInput, gbc);
			
			gbc.gridy++;
			add(numberInput, gbc);
			
			gbc.gridy++;
			add(addressInput, gbc);
			
			gbc.gridy++;
			add(postNoInput, gbc);

			gbc.anchor = GridBagConstraints.SOUTHEAST;
			gbc.fill = GridBagConstraints.NONE;
			gbc.gridx++;
			add(save, gbc);
			
			//Place the error message directly below the text fields
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridy++;
			gbc.gridx--;
			add(errorMessage, gbc);

			nameInput.grabFocus();

			//Remove buttom padding from border
			Insets borderInsets = Layout.customerItemPadding.getBorderInsets(this);
			borderInsets.bottom = 0;
			setBorder(BorderFactory.createEmptyBorder(borderInsets.top, borderInsets.left,
						borderInsets.bottom, borderInsets.right));
			
			setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
		}
		else {
			removeAll();

			info.setText("<html><table>" +
							"<tr><td>" + customer.getName() + "</td></tr>" +
							"<tr><td>" + customer.getPhone() + "</td></tr>" +
							"<tr><td>" + DataAPI.getAddresses(customer).get(0).getAddressLine() + "</td></tr>" +
							"<tr><td>" + DataAPI.getAddresses(customer).get(0).getPostalCode() + "</td></tr>" +
						"</table></html>");
			errorMessage.setText(" ");

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weighty = 1;
			gbc.weightx = gbc.gridy = gbc.gridx = 0;
			add(prefixes, gbc);
			
			gbc.weightx = 1;
			gbc.gridx++;
			add(info, gbc);

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

	public boolean isBeingEdited() {
		return beingEdited;
	}

	/**
	 * Returns the {@link Customer} object associated with this panel.
	 * 
	 * @return a {@link Customer} object.
	 */
	public Customer getCustomer() {
		return customer;
	}
}