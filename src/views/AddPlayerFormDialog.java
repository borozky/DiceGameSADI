package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.AddNewPlayerListener;
import controllers.interfaces.AddNewPlayerDataSource;
import model.interfaces.GameEngine;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Form dialog that will pop-up when "Add new player" is clicked. 
 * The "Add new player" button is in the {@link ToolbarPanel} component.
 * 
 * <p>This class implements the {@link AddNewPlayerDataSource} to supply 
 * player name and initial points for the {@link AddNewPlayerListener}.
 * 
 * @author Joshua Orozco
 *
 */
public class AddPlayerFormDialog extends JDialog implements AddNewPlayerDataSource {

	private static final long serialVersionUID = 9142155942271244834L;
	private JPanel contentPanel;
	private JTextField playerNameField;
	private JTextField initialPointsField;
	private JPanel bodyPanel;
	private JButton okButton;
	private JButton cancelButton;
	private AddNewPlayerListener addNewPlayerListener;
	private GameEngine gameEngine;


	public AddPlayerFormDialog(GameEngine gameEngine, AddNewPlayerListener addNewPlayerListner) {
		this.gameEngine = gameEngine;
		this.addNewPlayerListener = addNewPlayerListner;
		
		// "New player", custom-size, centered, and non-resizable
		setTitle("New player");
		setBounds(0, 0, 450, 196);
		setLocationRelativeTo(null);
		setResizable(false);
		
		setupContentPanel();
		setupBodyPanel();
		setupHeading();
		setupPlayerNameGroup();
		setupInitialPointsGroup();
		setupFooter();
		setupHandlers();
	}
	
	
	/**
	 * Gets the listener for when this form is submitted.
	 * @return
	 */
	public AddNewPlayerListener getAddNewPlayerListener() {
		return addNewPlayerListener;
	}

	
	/**
	 * Helper function to setup the root body of the dialog
	 */
	private void setupContentPanel() {
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel = new JPanel(new BorderLayout(0, 0));
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}
	
	
	/**
	 * Helper method to setup the main body of the dialog.
	 */
	private void setupBodyPanel() {
		bodyPanel = new JPanel();
		bodyPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		bodyPanel.setBackground(Color.WHITE);
		contentPanel.add(bodyPanel, BorderLayout.NORTH);
		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
	}
	
	
	/**
	 * Setup dialog heading
	 */
	private void setupHeading() {
		JPanel headingPanel = new JPanel();
		FlowLayout fl_headingPanel = (FlowLayout) headingPanel.getLayout();
		fl_headingPanel.setAlignment(FlowLayout.LEFT);
		headingPanel.setBackground(Color.WHITE);
		bodyPanel.add(headingPanel);
		
		JLabel headingLabel = new JLabel("Add new player");
		headingLabel.setForeground(new Color(0, 51, 153));
		headingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		headingPanel.add(headingLabel);
	}
	
	/**
	 * Setup input for player name
	 */
	private void setupPlayerNameGroup() {
		JPanel playerNameGroup = new JPanel();
		FlowLayout fl_playerNameGroup = (FlowLayout) playerNameGroup.getLayout();
		fl_playerNameGroup.setAlignment(FlowLayout.LEFT);
		playerNameGroup.setBackground(Color.WHITE);
		bodyPanel.add(playerNameGroup);
	
		JLabel playerNameLabel = new JLabel("Player name");
		playerNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		playerNameGroup.add(playerNameLabel);
	
		playerNameField = new JTextField();
		playerNameField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		playerNameGroup.add(playerNameField);
		playerNameField.setColumns(20);
	}
	
	
	/**
	 * Setup input for initial points.
	 */
	private void setupInitialPointsGroup() {
		JPanel initialPointsGroup = new JPanel();
		FlowLayout fl_initialPointsGroup = (FlowLayout) initialPointsGroup.getLayout();
		fl_initialPointsGroup.setAlignment(FlowLayout.LEFT);
		initialPointsGroup.setBackground(Color.WHITE);
		bodyPanel.add(initialPointsGroup);
		
		JLabel initialPointsLabel = new JLabel("Initial points");
		initialPointsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		initialPointsGroup.add(initialPointsLabel);
		
		initialPointsField = new JTextField();
		initialPointsField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		initialPointsGroup.add(initialPointsField);
		initialPointsField.setColumns(10);
	}
	
	/**
	 * Setup the footer contains and the buttons for this dialog
	 */
	private void setupFooter() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		okButton = new JButton("OK");
		okButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		okButton.setPreferredSize(new Dimension(81, 23));
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cancelButton.setPreferredSize(new Dimension(81, 23));
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
	
	
	/**
	 * Setup event handlers for this dialog
	 */
	private void setupHandlers() {
		okButton.addMouseListener(addNewPlayerListener);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	/**
	 * Clear the text field of the dialog form
	 */
	public void resetInput() {
		playerNameField.setText("");
		initialPointsField.setText("");
	}
	

	@Override
	public String getPlayerName() {
		return playerNameField.getText();
	}

	@Override
	public String getInitialPoints() {
		return initialPointsField.getText();
	}
	
	
	
	
}
