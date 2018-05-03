package views;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;

public class NotificationPanel extends JPanel {
	
	private JLabel messageLabel;

	/**
	 * Create the panel.
	 */
	public NotificationPanel() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(10, 28));
		
		messageLabel = new JLabel("Notification goes here");
		messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(messageLabel);
	}

	public String getMessage() {
		return messageLabel.getText();
	}

	public void setMessage(String message) {
		messageLabel.setText(message);
	}
	
	public void clearMessage() {
		setMessage("");
	}

}
