package ntnu.it1901.gruppe4.deliverygui;

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
 * 
 */

public class MapPanel extends JPanel {
	private static final String RESTAURANT_ADDRESS = Settings.getRestaurantAddress().replace(' ', '+');
	private static final String DESTINATION_ICON = "http://chart.apis.google.com/chart?chst=d_map_pin_icon%26chld=glyphish_house%257C00FF00";
	private static final String RESTAURANT_ICON = "http://chart.apis.google.com/chart?chst=d_map_pin_icon%26chld=glyphish_fork-and-knife%257CFF0000";

	public MapPanel() {

		BufferedImage image;
		try {
			image = ImageIO
					.read(new URL(
							"http://maps.google.com/maps/api/staticmap?size=500x500&markers=icon:"
							+ RESTAURANT_ICON + "|" + RESTAURANT_ADDRESS + "&sensor=false"));
		} catch (MalformedURLException e) {
			image = null;
		} catch (IOException e) {
			image = null;
		}
		if (image != null) {
			JLabel label = new JLabel(new ImageIcon(image));
			add(label);
		}
	}

	public void setAddress(Address address) {
		removeAll();
		String destinationAddress = address.getAddressLine().replace(' ', '+') + ",Trondheim";

		BufferedImage image = null;
		try {
			image = ImageIO
					.read(new URL(
							"http://maps.google.com/maps/api/staticmap?size=500x500"
							+ "&markers=icon:" + RESTAURANT_ICON + "|" + RESTAURANT_ADDRESS
							+ "&markers=icon:" + DESTINATION_ICON + "|" + destinationAddress
							+ "&sensor=false"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel label = new JLabel(new ImageIcon(image));
		add(label);
		revalidate();
		repaint();
	}

}
