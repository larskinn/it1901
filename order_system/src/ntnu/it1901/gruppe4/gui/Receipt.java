package ntnu.it1901.gruppe4.gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderItem;
import ntnu.it1901.gruppe4.db.Settings;

public class Receipt extends JDialog {
	public Receipt(OrderSummary orderSummary) {
		this(null, orderSummary);
	}
	
	public Receipt(JFrame parentFrame, OrderSummary orderSummary) {
		StringBuilder sb = new StringBuilder();
		JLabel text = new JLabel();
		String dashedLine;
		
		//Create a dashed line seperator of variable length
		for (int i = 0; i < 60; i++) {
			sb.append('-');
		}
		
		dashedLine = sb.toString();
		sb.setLength(0);

		//Add a header to the receipt
		sb.append(
					"<html>" +
						"<body>" +
							"<table>" +
								"<tr>" +
									"<td colspan='4' align='center'> Marios Pizzeria </td>" +
								"</tr>" +
								"<tr>" +
									"<td colspan='4' align='center'>" + Settings.getRestaurantAddress() + "</td>" +
								"</tr>" +
								"<tr>" +
									"<td colspan='4'>" + dashedLine + "</td>" +
								"</tr>"
		);
		
		if (orderSummary.getItemCount() == 0) {
			sb.append(
						"<tr>" +
							"<td colspan='4'> Ingen varer bestilt </td>" +
						"</tr>"	
			);
		}
		else {
			//Add every item ordered
			for (OrderItem item : orderSummary.getItemList()) {
				sb.append(
							"<tr>" +
								"<td colspan='2'>" + item.getName() + "</td>" +
								"<td colspan='2' align='right'>" + Layout.decimalFormat.format(item.getAmount()) + " kr </td>" +
							"</tr>"				
				);
			}
			//Add delivery fee and total amount
			sb.append(
						"<tr>" +
							"<td colspan='2'>Frakt </td>" +
							"<td colspan='2' align='right'>" + Layout.decimalFormat.format(orderSummary.getOrder().getDeliveryFee()) + " kr </td>" +
						"</tr>" +
							
						"<tr>" +
							"<td colspan='4'>" + dashedLine + "</td>" +
						"</tr>" +
							
						"<tr>" +
							"<td colspan='2'>Total </td>" +
							"<td colspan='2' align='right'>" + Layout.decimalFormat.format(orderSummary.getOrder().getTotalAmount()) + " kr </td>" +
						"</tr>" +
							
						"<tr>" +
							//An empty line
						"</tr>"
			);
			
			//Add some relevant date
			if (orderSummary.getOrder().getState() != Order.NOT_SAVED) {
				sb.append(
							"<tr>" +
								"<td colspan='4'>"
						);
				
				if (orderSummary.getOrder().getDeliveryTime() != null) {
					sb.append("Levert " + Layout.dateFormat.format(orderSummary.getOrder().getDeliveryTime()));
				}
				else {
					sb.append("Bestilt " + Layout.dateFormat.format(orderSummary.getOrder().getOrderTime()));
				}
				
				sb.append(
							"</td>" +
						"</tr>" +
						"<tr>" +
						//An empty line
						"</tr>"
				);
			}
			//Add gross, mva%, mva and net values
			sb.append(		
						"<tr>" +
							"<td>Brutto</td>" +
							"<td align='center'>Mva-%</td>" +
							"<td align='right'>Mva</td>" +
							"<td align='right'>Netto</td>" +
						"</tr>" +
						"<tr>" +
							"<td>" + Layout.decimalFormat.format(orderSummary.getOrder().getGrossAmount()) + "</td>" +
							"<td align='center'>" + Layout.decimalFormat.format(Settings.getTax()) + "</td>" +
							"<td align='right'>" + Layout.decimalFormat.format(orderSummary.getOrder().getTaxAmount()) + "</td>" +
							"<td align='right'>" + Layout.decimalFormat.format(orderSummary.getOrder().getTotalAmount() -
									orderSummary.getOrder().getTaxAmount()) + "</td>" +
						"</tr>"
					);
		} //End of if (items == 0)
		
		//Add some smalltalk as a footer
		sb.append(						
					"<tr>" +
						"<td colspan='4'>" + dashedLine + "</td>" +
					"</tr>" +
						
					"<tr>" +
						"<td colspan='4' align='center'>Takk for handelen!</td>" +
					"</tr>" +
					"<tr>" +
						"<td colspan='4' align='center'>Velkommen igjen.</td>" +
					"</tr>"
		);
		
		//Close the opening tags
		sb.append("</table> </body> </html>");
		text.setText(sb.toString());
		
		//Add all html to the top left of the container element
		JPanel container = new JPanel();
		container.setLayout(null);
		text.setBounds(4, 4, text.getPreferredSize().width, text.getPreferredSize().height);
		container.add(text);
		
		//Set the final size of the dialog to the size required by the html
		container.setPreferredSize(text.getPreferredSize());
		add(container);
		pack();
		setResizable(false);
		
		setTitle("Kvittering");
		setLocationRelativeTo(parentFrame);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
