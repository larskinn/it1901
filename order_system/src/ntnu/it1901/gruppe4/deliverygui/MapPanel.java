package ntnu.it1901.gruppe4.deliverygui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class is the Panel where the Google Static API Map is implemented.
 * <p>
 * It takes an address given to it by {@link Address} and puts it in the static
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

	public MapPanel() {
		setBackground(Color.white);
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	public void setAddress(Address address) {
		setAddress(address, prevSize);
	}

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
				JOptionPane.showMessageDialog(null, 
						"Kunne ikke koble til internett. Kart vil være utilgjengelig.", 
						"Feil", JOptionPane.WARNING_MESSAGE);
				hasInternetConnection = false;
			}
			add(new JLabel("Ingen internettilgang."));
		}
		revalidate();
		repaint();
	}

}
