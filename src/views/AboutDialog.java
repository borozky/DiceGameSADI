package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The about dialog.
 * <p>This will pop-up when Help->About menu is selected.
 * 
 * @author user
 *
 */
public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 4403491762235328428L;
	
	
	private JPanel contentPanel;
	private JButton okButton;
	private JButton cancelButton;

	public AboutDialog() {
		setTitle("About");
		setBounds(0, 0, 450, 300);
		setLocationRelativeTo(null); // center of the screen
		
		setupContentPanel();
		setupAboutLabel();
		setupFooter();
		setupButtonHandlers();
	}
	
	private void setupContentPanel() {
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel = new JPanel(new BorderLayout(0, 0));
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 30));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}
	
	private void setupAboutLabel() {
		JLabel aboutLabel = new JLabel("<html>"
				+ "Created by Joshua Orozco for Software Architecture: Design and Implementation course<br/><br/>"
				+ "<b>Rules: </b><br/>"
				+ "<ul>"
				+ "<li>All players can play against the house.</li>"
				+ "<li>If player's dice result is lower than the house's result, the player loses its bet.</li>"
				+ "</ul>"
				+ "</html>");
		aboutLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		contentPanel.add(aboutLabel, BorderLayout.NORTH);
	}
	
	private void setupFooter() {
		// footer parent
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		// ok button
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		buttonPane.add(okButton);
		
		// cancel button
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
	
	private void setupButtonHandlers() {
		ActionListener onClose = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
		okButton.addActionListener(onClose);
		cancelButton.addActionListener(onClose);
	}

}
