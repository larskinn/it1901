package ntnu.it1901.gruppe4.ordergui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Dish;

public class AddressPanel extends JPanel {
	SearchBox addressSearch;
	AddressList addressList;

	public class AddressList extends JPanel {
		/**
		 * Creates a new AddressList. Only the AddressPanel is allowed to do this.
		 */
		private AddressList() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBackground(Color.DARK_GRAY);
		}
		
		/**
		 * Converts all addresses in the given collection to {@link AddressPanelItem},
		 * adds them to the {@link AddressList} and repaints the panel.
		 *  
		 * @param addresses The addresses to be added to the {@link AddressList}.
		 */
		public void addAddresses(Collection<Address> addresses) {
			removeAll();
			
			for (Address address : addresses) {
				add(new AddressPanelItem(address));
			}
			revalidate();
			repaint();
		}
	}
	
	public AddressPanel() {
		addressSearch = new SearchBox();
		addressList = new AddressList();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(addressSearch);

		//Add some space between the addressSearch and the addressList
		add(Box.createRigidArea(new Dimension(0, 25)));
		
		//Add all the addresses to the list
		addressList.addAddresses(DataAPI.findAddresses(""));

		addressList.setPreferredSize(new Dimension(999, 999));
		add(addressList);
		
		addressSearch.addKeyListener(new KeyAdapter() {
			/*keyReleased() used for searching as getText() does not return the
			 *updated content of the search box when keyTyped() is called 
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				SearchBox source = (SearchBox)e.getSource();
				String boxContent = source.getText();
				
				//If the search box is empty, interrupt and restore the list of results
				if (boxContent.equals("")) {
					addressList.addAddresses(DataAPI.findAddresses(""));
					return;
				}
				
				//Do the search
				addressList.addAddresses(DataAPI.findAddresses(boxContent));
			}
		});
	}
}