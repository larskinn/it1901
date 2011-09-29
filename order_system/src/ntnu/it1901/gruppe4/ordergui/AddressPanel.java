package ntnu.it1901.gruppe4.ordergui;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		
		searchBox.addKeyListener(new KeyAdapter() {
			/*keyReleased() used for searching as getText() does not return the
			 *updated content of the search box when keyTyped() is called 
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				SearchBox source = (SearchBox)e.getSource();
				String boxContent = source.getText();
				
				//If the search box is empty, interrupt and restore the list of results
				if (boxContent.equals("")) {
					//TODO: Return the customer list to its default view
					return;
				}
				
				//Do the search
				System.out.println("Searching addresses for: " + source.getText());
			}
		});
	}
}