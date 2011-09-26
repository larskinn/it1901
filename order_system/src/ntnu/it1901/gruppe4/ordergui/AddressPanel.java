package ntnu.it1901.gruppe4.ordergui;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class AddressPanel extends JPanel {
	SearchBox searchBox; //To be renamed

	public AddressPanel() {
		searchBox = new SearchBox();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel filler = new JPanel(); //To be removed
		filler.setPreferredSize(new Dimension(999, 999));
		
		add(searchBox);
		add(filler);
		
		searchBox.setText("This is the address view");
	}

}
