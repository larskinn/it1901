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
 * This class is a container panel for the image received from Google Static Map API.
 * <p>
 * It takes an address line and a post number given to it by a specified {@link Address} and 
 * puts it in the static URL, which will contain an image to be downloaded.
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
	 * Creates a new {@link MapPanel} initially containing no map.
	 */
	MapPanel() {
		setBackground(Color.white);
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	/**
	 * Downloads a new map image from Google matching the specifed
	 * {@link Address} which is then stored inside the {@link MapPanel}.
	 * 
	 * @param address The <code>Address</code> which the downloaded map will match.
	 */
	public void setAddress(Address address) {
		setAddress(address, prevSize);
	}

	/**
	 * Downloads a new map image of the specified size from Google matching the specifed
	 * {@link Address} which is then stored inside the {@link MapPanel}.
	 * 
	 * @param address The <code>Address</code> which the downloaded map will match.
	 * @param mapSize The size of the downloaded map.
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
