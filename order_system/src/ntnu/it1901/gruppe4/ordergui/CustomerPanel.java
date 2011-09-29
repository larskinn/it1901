package ntnu.it1901.gruppe4.ordergui;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class CustomerPanel extends JPanel {
	SearchBox nameInput;
	SearchBox numberInput;
	private SearchBoxListener listener;
	private String prevSearch = "";
	
	private class SearchBoxListener extends KeyAdapter {
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
			
			/* Do not search for the same string more than once
			 * 
			 * Note: This operation might cost more than its worth. Discuss removing
			 */
			if (boxContent.equals(prevSearch)) {
				return;
			}
			
			/* If the first character in the name box is a number,
			 * or the first character in the number box is a letter,
			 * reverse the functions of the search boxes
			 */
			if ((source == nameInput && Character.isDigit(boxContent.charAt(0)))
					|| (source == numberInput && 
					(Character.isLetter(boxContent.charAt(0)) || boxContent.charAt(0) == ' '))) {
				SearchBox tmp = numberInput;
				numberInput = nameInput;
				nameInput = tmp;
			}
			
			//Search for name or number using the DataAPI
			if (source == nameInput) {
				System.out.println("Searching names for: " + source.getText());
			}
			else if (source == numberInput) {
				System.out.println("Searching numbers for: " + source.getText());
			}
			
			prevSearch = boxContent;
		}
	}

	public CustomerPanel() {
		nameInput = new SearchBox();
		numberInput = new SearchBox();
		listener = new SearchBoxListener();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel filler = new JPanel(); // To be removed
		filler.setPreferredSize(new Dimension(999, 999));

		add(nameInput);
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(numberInput);
		add(filler);

		nameInput.addKeyListener(listener);
		numberInput.addKeyListener(listener);
	}
}
