package views;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

import controllers.AddPlayerButtonListener;
import controllers.StartGameButtonListener;

import javax.swing.SwingConstants;
import java.awt.Dimension;

public class MainPanel extends JPanel {
	
	private JTextField playerNameField;
	private JTextField playerBetField;
	private JLabel gameInstructionsLabel;
	private JLabel playerNameLabel;
	private JLabel playerBetLabel;
	private JButton addPlayerButton;
	private JButton startGameButton;
	private JLabel initialPointsLabel;
	private JTextField initialPointsField;
	
	/**
	 * Create the panel.
	 */
	public MainPanel() {
		
		Font defaultFont = new Font("Segoe UI", Font.PLAIN, 12);
		
		// background & layout
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		
		// FORM CONTAINER
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setLayout(new BorderLayout(0, 0));
		add(topPanel, BorderLayout.NORTH);
		
		
		// GAME INSTRUCTIONS
		JPanel gameInstructionsPanel = new JPanel();
		gameInstructionsPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		gameInstructionsPanel.setLayout(new BorderLayout(0, 0));
		topPanel.add(gameInstructionsPanel, BorderLayout.NORTH);
		
		gameInstructionsLabel = new JLabel("<html><b>Rules:</b> Any player that has higher dice number than the house wins the bet.</html>");
		gameInstructionsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		gameInstructionsLabel.setFont(defaultFont);
		gameInstructionsPanel.add(gameInstructionsLabel);
		
		
		// FORM
		JPanel addPlayerForm = new JPanel();
		addPlayerForm.setLayout(new BorderLayout(0, 3));
		topPanel.add(addPlayerForm, BorderLayout.SOUTH);
		
		// player name field
		JPanel playerNameSection = new JPanel();
		playerNameSection.setLayout(new BorderLayout(5, 0));
		addPlayerForm.add(playerNameSection, BorderLayout.NORTH);
		
		playerNameLabel = new JLabel("Player name");
		playerNameLabel.setFont(defaultFont);
		playerNameSection.add(playerNameLabel, BorderLayout.WEST);
		
		playerNameField = new JTextField();
		playerNameField.setFont(defaultFont);
		playerNameField.setColumns(10);
		playerNameSection.add(playerNameField, BorderLayout.CENTER);
		
		JPanel initialPointsSection = new JPanel();
		addPlayerForm.add(initialPointsSection, BorderLayout.CENTER);
		initialPointsSection.setLayout(new BorderLayout(0, 0));
		
		initialPointsLabel = new JLabel("Initial Points  ");
		initialPointsLabel.setFont(defaultFont);
		initialPointsSection.add(initialPointsLabel, BorderLayout.WEST);
		
		initialPointsField = new JTextField();
		initialPointsField.setPreferredSize(new Dimension(6, 23));
		initialPointsField.setFont(defaultFont);
		initialPointsSection.add(initialPointsField, BorderLayout.CENTER);
		initialPointsField.setColumns(10);
		
		JPanel playerBetSection = new JPanel();
		playerBetSection.setLayout(new BorderLayout(5, 0));
		addPlayerForm.add(playerBetSection, BorderLayout.SOUTH);
		
		// player bet field
		playerBetLabel = new JLabel("Player bet");
		playerBetLabel.setBorder(new EmptyBorder(0, 0, 0, 14));
		playerBetLabel.setFont(defaultFont);
		playerBetSection.add(playerBetLabel, BorderLayout.WEST);
		
		playerBetField = new JTextField();
		playerBetField.setFont(defaultFont);
		playerBetField.setColumns(10);
		playerBetSection.add(playerBetField, BorderLayout.CENTER);
		
		addPlayerButton = new JButton("Add player");
		addPlayerButton.setFont(defaultFont);
		playerBetSection.add(addPlayerButton, BorderLayout.EAST);
		
		// area where 'Start Game' button is placed
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) bottomPanel.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(bottomPanel, BorderLayout.SOUTH);
		
		startGameButton = new JButton("Start game");
		startGameButton.setFont(defaultFont);
		bottomPanel.add(startGameButton);
		
		enableInteraction();
		clearInput();
	}
	
	public void disableInteraction() {
		startGameButton.setEnabled(false);
		playerNameField.setEnabled(false);
		playerBetField.setEnabled(false);
		addPlayerButton.setEnabled(false);
	}
	
	public void enableInteraction() {
		startGameButton.setEnabled(true);
		playerNameField.setEnabled(true);
		playerBetField.setEnabled(true);
		addPlayerButton.setEnabled(true);
	}
	
	public String getPlayerName() {
		return playerNameField.getText();
	}
	
	public String getInitialPoints() {
		return initialPointsField.getText();
	}
	
	public String getPlayerBet() {
		return playerBetField.getText();
	}
	
	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void onAddPlayer(AddPlayerButtonListener listener) {
		addPlayerButton.addMouseListener(listener);
	}
	
	public void clearInput() {
		playerNameField.setText(null);
		playerBetField.setText(null);
		initialPointsField.setText(null);
		
		// focus on playerNameField
		playerNameField.grabFocus();
	}
	
	public void onStartGame(StartGameButtonListener listener) {
		startGameButton.addMouseListener(listener);
	}
	
	
	
	
}
