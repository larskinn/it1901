package ntnu.it1901.gruppe4.deliverygui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class ScrollMap extends JApplet {
	
	// http://www.java-forums.org/networking/34740-google-maps-static-api-java-se.html
	
	 public BufferedImage image = null;{
		try {
			image = ImageIO.read(new URL("http://maps.google.com/maps/api/staticmap?Starevegen+9,Trondheim&size=600x600&markers=color:green|Starevegen+9,Trondheim|Munkegata,Trondheim|&sensor=false"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}}
	 
	 public BufferedImage image2 = null;{
			try {
				image2 = ImageIO.read(new URL("http://maps.google.com/maps/api/staticmap?Munkegata,Trondheim&size=500x500&markers=color:green|Munkegata,Trondheim|Lade,Trondheim|&sensor=false"));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}}
	//Create images in labels
	private JLabel lblStarevegen9Map = new JLabel(new ImageIcon(image));
	private JLabel lblTrondheimMap = new JLabel(new ImageIcon(image2));
	
	//Create a scroll pane to scroll map in the labels
	private JScrollPane jspMap = new JScrollPane(lblStarevegen9Map);
	
	public ScrollMap(){
		//Create a combo box for selecting maps
		JComboBox jcboMap = new JComboBox(new String[]{"Starevegen 9", "Lade"});
		
		//Panel p to hold combo box
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(jcboMap);
		p.setBorder(new TitledBorder("Select an address to display"));
		
		//Set row header, column header and corner header
		jspMap.setCorner(JScrollPane.UPPER_LEFT_CORNER, new CornerPanel(JScrollPane.UPPER_LEFT_CORNER));
		jspMap.setCorner(JScrollPane.LOWER_LEFT_CORNER, new CornerPanel(JScrollPane.LOWER_LEFT_CORNER));
		jspMap.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new CornerPanel(JScrollPane.UPPER_RIGHT_CORNER));
		jspMap.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new CornerPanel(JScrollPane.LOWER_RIGHT_CORNER));
		
		//Add the scroll pane and combo box panel to the frame
		
		add(jspMap, BorderLayout.CENTER);
		add(p, BorderLayout.NORTH);
		
		//Register listener
		jcboMap.addItemListener(new ItemListener() {
			//**Show the selected map */
			
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String selectedItem = (String)e.getItem();
				if (selectedItem.equals("Starevegen 9")){
					//Set a new view in the view port
					jspMap.setViewportView(lblStarevegen9Map);
				}
				else if (selectedItem.equals("Trondheim")){
					jspMap.setViewportView(lblTrondheimMap);
				}
				
				//Revalidate the scroll pane
				jspMap.revalidate();
			}
		});
		
		
	}
	
	
}

//A panel displaying a line used for scroll pane corner
class CornerPanel extends JPanel{
	//Line location
	private String location;
	
	public CornerPanel(String location){
		this.location = location;
	}
	
	/**Draw a line depending on the location */
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		
		if (location == "UPPER_LEFT_CORNER"){
			g.drawLine(0, getHeight(), getWidth(), 0);
		}
		else if (location == "UPPER_RIGHT_CORNER"){
			g.drawLine(0, 0, getHeight(), getWidth());
		}
		else if (location == "LOWER_RIGHT_CORNER"){
			g.drawLine(0, getHeight(), getWidth(), 0);
		}
		else if (location == "LOWER_LEFT_CORNER"){
			g.drawLine(0, 0, getWidth(), getHeight());
		}
	}
	
}


