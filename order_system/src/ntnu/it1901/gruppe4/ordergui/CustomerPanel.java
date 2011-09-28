package ntnu.it1901.gruppe4.ordergui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class CustomerPanel extends JPanel {
	SearchBox searchBox1;
	SearchBox searchBox2;

	public CustomerPanel() {
		searchBox1 = new SearchBox();
		searchBox2 = new SearchBox();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel filler = new JPanel(); // To be removed
		filler.setPreferredSize(new Dimension(999, 999));

		add(searchBox1);
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(searchBox2);
		add(filler);

		searchBox1.setText("This is the customer view");
	}
}
