package views;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;

/**
 * Displays messages at the bottom of the main window.
 * 
 * @author Joshua Orozco
 *
 */
public class NotificationPanel extends JPanel {
	private static final long serialVersionUID = -8436903918692617867L;
	private JLabel messageLabel;

	public NotificationPanel() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(10, 28));
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		messageLabel = new JLabel("");
		messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(messageLabel);
		
		clearMessage();
	}

	/**
	 * Gets the message displayed by this component
	 * @return
	 */
	public String getMessage() {
		return messageLabel.getText();
	}

	/**
	 * Sets the message of this component.
	 * @param message
	 */
	public void setMessage(String message) {
		messageLabel.setText(message);
	}
	
	/**
	 * Sets the message of this component to an empty string
	 */
	public void clearMessage() {
		setMessage("");
	}

}
