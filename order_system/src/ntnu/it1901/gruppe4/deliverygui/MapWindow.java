package ntnu.it1901.gruppe4.deliverygui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.deliverygui.DeliveryView;
import ntnu.it1901.gruppe4.deliverygui.DeliveryOrderSummary;

import javax.imageio.ImageIO;
import javax.swing.*;

/*public class MapWindow extends JFrame {
	
	static String address = JOptionPane.showInputDialog("Skriv inn en adresse");
	static String modAddress = address.replace(' ','+');
	static String newAddress = "|&markers=color:red|"+modAddress+",Trondheim";
	
	
	public static void main(String[] args) throws Exception{
		
		String staticAddress = address.replace(' ','+');
		String newAddress = "|&markers=color:red|"+staticAddress+",Trondheim";
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		BufferedImage image = ImageIO.read(new URL("http://maps.google.com/maps/api/staticmap?"+staticAddress+",Trondheim&size=500x500&markers=color:green|Munkegata,Trondheim"+newAddress+"&sensor=false"));
		JLabel label = new JLabel(new ImageIcon(image));
		panel.add(label);
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}*/

public class MapWindow extends JPanel {
	
	
	String address = "";
	String modAddress = address.replace(' ','+');
	String newAddress = "|&markers=color:red|"+modAddress+",Trondheim";
	
	public MapWindow() throws MalformedURLException, IOException{
		
		String staticAddress = address.replace(' ','+');
		String newAddress = "|&markers=color:red|"+staticAddress+",Trondheim";
		
		BufferedImage image = ImageIO.read(new URL("http://maps.google.com/maps/api/staticmap?"+staticAddress+",Trondheim&size=500x500&markers=color:green|Munkegata,Trondheim"+newAddress+"&sensor=false"));
		JLabel label = new JLabel(new ImageIcon(image));
		add(label);
	}
	
	public void setAddress(Address address){
		System.out.println("hei");
		removeAll();
		String staticAddress = address.getAddressLine().replace(' ','+');
		String markerAddress = "|&markers=color:red|"+staticAddress+",Trondheim";
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new URL("http://maps.google.com/maps/api/staticmap?"+staticAddress+",Trondheim&size=500x500&markers=color:green|Munkegata,Trondheim"+markerAddress+"&sensor=false"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel label = new JLabel(new ImageIcon(image));
		add(label);
		revalidate();
		repaint();
	}
	
}
