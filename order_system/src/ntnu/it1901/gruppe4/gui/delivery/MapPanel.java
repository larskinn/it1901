package ntnu.it1901.gruppe4.gui.delivery;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.Settings;

/**
 * This class is the Panel where the Google Static API Map is implemented.
 * <p>
 * It takes an address and post number given to it by {@link Address} and puts it in the static
 * URL, generating a new picture each time it is called.
 * 
 * @author Morten
 * @author Leo
 * @author Lars
 */
public class MapPanel extends JPanel {
	private boolean hasInternetConnection = true;
	private Dimension prevSize;
	private static final String RESTAURANT_ADDRESS = Settings.getRestaurantAddress().replace(' ', '+');
	private static final String DESTINATION_ICON = "http://chart.apis.google.com/chart?chst=d_map_pin_icon%26chld=glyphish_house%257C00FF00";
	private static final String RESTAURANT_ICON = "http://chart.apis.google.com/chart?chst=d_map_pin_icon%26chld=glyphish_fork-and-knife%257CFF0000";
	
	/**
	 * Creates a new {@link MapPanel}.
	 */
	public MapPanel() {
		setBackground(Color.white);
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	/**
	 * Updates the map using the specified address and previous map size.
	 * @param address The specified address
	 */
	public void setAddress(Address address) {
		setAddress(address, prevSize);
	}
	/**
	 * Updates the map using the specified address and map size.
	 * @param address The specified address
	 * @param mapSize The specified map size
	 */
	public void setAddress(Address address, Dimension mapSize) {
		if (!hasInternetConnection) {
			return;
		}
		removeAll();
		
		prevSize = mapSize;
		String destinationAddress = address == null ? 
				"" : address.getAddressLine().replace(' ', '+') + ","+ address.getPostalCode();

		try {
			add(new JLabel(new ImageIcon(ImageIO.read(new URL(
							"http://maps.google.com/maps/api/staticmap?size=" 
							+ mapSize.width + "x" + mapSize.height
							+ "&markers=icon:" + RESTAURANT_ICON + "|" + RESTAURANT_ADDRESS
							+ "&markers=icon:" + DESTINATION_ICON + "|" + destinationAddress
							+ "&sensor=false")))));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if (hasInternetConnection) {
				hasInternetConnection = false;
				JOptionPane.showMessageDialog(null, 
						"Kunne ikke koble til internett. Kart vil være utilgjengelig.", 
						"Feil", JOptionPane.WARNING_MESSAGE);
			}
			add(new JLabel("Ingen internettilgang."));
		}
		revalidate();
		repaint();
	}

}
