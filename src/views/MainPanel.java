package views;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controllers.RollPlayerListener;
import controllers.interfaces.RollPlayerDataSource;
import model.interfaces.DicePair;
import model.interfaces.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.border.LineBorder;

/**
 * Holds the bet form, the player's dice and the house dice. 
 * <p>This component implements {@link RollPlayerDataSource} to supply data into 
 * {@link RollPlayerListener}.
 * 
 * @author Joshua Orozco
 *
 */
public class MainPanel extends JPanel implements ListOfPlayersPanel.Delegate, RollPlayerDataSource {
	private static final long serialVersionUID = -2154448330526344275L;
	
	Font defaultFont = new Font("Segoe UI", Font.PLAIN, 12);
	
	private JTextField playerBetField;
	private JLabel headingLabel;
	private JLabel playerBetLabel;
	private JButton rollButton;
	private JLabel initialPointsLabel;
	
	private Player selectedPlayer = null;
	private JLabel initialPointsValueLabel;
	private JPanel playerDetailsPanel;
	private JPanel placeBetForm;
	
	private JLabel diceOneLabel;
	private JLabel diceTwoLabel;
	private JLabel totalResultLabel;
	private JPanel diceWrapperPanel;
	private JPanel houseDicePanel;
	private JLabel houseDiceOneLabel;
	private JLabel houseDiceTwoLabel;
	private JLabel houseTotalResultLabel;
	private JPanel playerDicePanel;
	
	private RollPlayerListener rollListener;
	
	public MainPanel(RollPlayerListener rollListener) {
		this.rollListener = rollListener;
		
		// background & layout
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		
		// Setup subcomponents
		setupPlayerDetailsPanel();
		setupHeadingPanel();
		setupPlaceBetForm();
		setupInitialPointRow();
		setupPlayerBetSection();
		setupDiceWrapperPanel();
		setupPlayerDice();
		setupHouseDice();
		
		// interaction
		enableInteraction();
		clearInput();
		setSelectedPlayer(null);
	}

	/**
	 * Sets up the player details panel. The panel will wrap the bet form and the heading
	 */
	private void setupPlayerDetailsPanel() {
		playerDetailsPanel = new JPanel();
		playerDetailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		playerDetailsPanel.setLayout(new BorderLayout(0, 0));
		add(playerDetailsPanel, BorderLayout.NORTH);
	}

	
	/**
	 * Sets up the heading section of this component
	 */
	private void setupHeadingPanel() {
		JPanel headingPanel = new JPanel();
		headingPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		headingPanel.setLayout(new BorderLayout(0, 0));
		playerDetailsPanel.add(headingPanel, BorderLayout.NORTH);
		
		headingLabel = new JLabel("-");
		headingLabel.setForeground(Color.BLACK);
		headingLabel.setHorizontalAlignment(SwingConstants.LEFT);
		headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		headingPanel.add(headingLabel);
	}

	/**
	 * Sets up the form that wrap the player bet row
	 */
	private void setupPlaceBetForm() {
		// FORM
		placeBetForm = new JPanel();
		placeBetForm.setLayout(new BorderLayout(0, 3));
		playerDetailsPanel.add(placeBetForm, BorderLayout.SOUTH);
	}
	
	/**
	 * Sets up the initial points row
	 */
	private void setupInitialPointRow() {
		// initial points
		JPanel initialPointsSection = new JPanel();
		placeBetForm.add(initialPointsSection, BorderLayout.CENTER);
		initialPointsSection.setLayout(new BorderLayout(0, 0));
		
		initialPointsLabel = new JLabel("Initial Points  ");
		initialPointsLabel.setFont(defaultFont);
		initialPointsSection.add(initialPointsLabel, BorderLayout.WEST);
		
		initialPointsValueLabel = new JLabel("-");
		initialPointsValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		initialPointsSection.add(initialPointsValueLabel, BorderLayout.CENTER);
				
	}
	
	/**
	 * Sets up the player bet row
	 */
	private void setupPlayerBetSection() {
		// PLAYER BET SECTION
		JPanel playerBetSection = new JPanel();
		playerBetSection.setLayout(new BorderLayout(5, 0));
		placeBetForm.add(playerBetSection, BorderLayout.SOUTH);
		
		playerBetLabel = new JLabel("Player bet");
		playerBetLabel.setBorder(new EmptyBorder(0, 0, 0, 14));
		playerBetLabel.setFont(defaultFont);
		playerBetSection.add(playerBetLabel, BorderLayout.WEST);
		
		playerBetField = new JTextField();
		playerBetField.setFont(defaultFont);
		playerBetField.setColumns(10);
		playerBetSection.add(playerBetField, BorderLayout.CENTER);
		
		rollButton = new JButton("Roll");
		rollButton.setPreferredSize(new Dimension(81, 23));
		rollButton.setFont(defaultFont);
		rollButton.addMouseListener(rollListener);
		playerBetSection.add(rollButton, BorderLayout.EAST);
	}
	
	/**
	 * Sets up the panel that will wrap the player and house dice
	 */
	private void setupDiceWrapperPanel() {
		// Area where the player's dice and house dice are located
		diceWrapperPanel = new JPanel();
		diceWrapperPanel.setPreferredSize(new Dimension(10, 100));
		diceWrapperPanel.setBackground(Color.WHITE);
		add(diceWrapperPanel, BorderLayout.SOUTH);
		diceWrapperPanel.setLayout(new BorderLayout(0, 0));
	}
	
	/**
	 * Sets the panel that will hold the player's dice
	 */
	private void setupPlayerDice() {
		// PLAYER DICE
		playerDicePanel = new JPanel();
		playerDicePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		diceWrapperPanel.add(playerDicePanel, BorderLayout.WEST);
		playerDicePanel.setLayout(new BorderLayout(10, 10));
		
		diceOneLabel = new JLabel("-");
		playerDicePanel.add(diceOneLabel, BorderLayout.WEST);
		diceOneLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
		
		diceTwoLabel = new JLabel("-");
		playerDicePanel.add(diceTwoLabel, BorderLayout.EAST);
		diceTwoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
		
		totalResultLabel = new JLabel("Player: -");
		playerDicePanel.add(totalResultLabel, BorderLayout.SOUTH);
		totalResultLabel.setPreferredSize(new Dimension(80, 14));
		totalResultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));		
	}
	
	/**
	 * Sets up the panel that will hold the house dice
	 */
	private void setupHouseDice() {
		// HOUSE DICE
		houseDicePanel = new JPanel();
		houseDicePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		diceWrapperPanel.add(houseDicePanel, BorderLayout.EAST);
		houseDicePanel.setLayout(new BorderLayout(5, 5));
		
		houseDiceOneLabel = new JLabel("-");
		houseDiceOneLabel.setForeground(new Color(0, 153, 102));
		houseDiceOneLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
		houseDicePanel.add(houseDiceOneLabel, BorderLayout.WEST);
		
		houseDiceTwoLabel = new JLabel("-");
		houseDiceTwoLabel.setForeground(new Color(0, 153, 102));
		houseDiceTwoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
		houseDicePanel.add(houseDiceTwoLabel, BorderLayout.EAST);
		
		houseTotalResultLabel = new JLabel("House: -");
		houseTotalResultLabel.setForeground(new Color(0, 153, 102));
		houseTotalResultLabel.setPreferredSize(new Dimension(80, 14));
		houseTotalResultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		houseDicePanel.add(houseTotalResultLabel, BorderLayout.SOUTH);
	}
	
	/**
	 * Gets the currently selected player.
	 * @return
	 */
	public Player getSelectedPlayer() {
		return selectedPlayer;
	}
	
	/**
	 * Updates the selected player. If a player is selected, it will display 
	 * the player's current information and player's dice roll result
	 * 
	 * @param selectedPlayer
	 */
	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
		
		if (selectedPlayer == null) {
			headingLabel.setText("Select a player");
			placeBetForm.setVisible(false);
			playerDicePanel.setVisible(false);
			setPlayerDice(null);
		}
		else {
			playerDicePanel.setVisible(true);
			placeBetForm.setVisible(true);
			headingLabel.setText(selectedPlayer.getPlayerName());
			initialPointsValueLabel.setText(Integer.toString(selectedPlayer.getPoints()));
			playerBetField.setText(Integer.toString(selectedPlayer.getBet()));
			setPlayerDice(selectedPlayer.getRollResult());
		}
		
	}
	
	/**
	 * Disabled the player bet field and the roll button
	 */
	public void disableInteraction() {
		playerBetField.setEnabled(false);
		rollButton.setEnabled(false);
	}
	
	/**
	 * Enabled the bet field and the roll button
	 */
	public void enableInteraction() {
		playerBetField.setEnabled(true);
		rollButton.setEnabled(true);
	}
	
	/**
	 * Gets the selected players name. 
	 * @return If there are no selected players, this will return null.
	 */
	public String getPlayerName() {
		if (selectedPlayer != null) {
			return selectedPlayer.getPlayerName();
		}
		return null;
	}
	
	/**
	 * Get the player's initial points as String value.
	 * @return If there are no selected players, this will return null.
	 */
	public String getInitialPoints() {
		if (selectedPlayer != null) {
			return Integer.toString(selectedPlayer.getPoints());
		}
		return null;
	}
	
	/**
	 * Updates the panel that represents the dice
	 * @param dicePair
	 */
	public void setPlayerDice(DicePair dicePair) {
		if (dicePair != null) {
			diceOneLabel.setText(Integer.toString(dicePair.getDice1()));
			diceTwoLabel.setText(Integer.toString(dicePair.getDice2()));
			totalResultLabel.setText("Player: " + (dicePair.getDice1() + dicePair.getDice2()) );
		}
		else {
			diceOneLabel.setText("-");
			diceTwoLabel.setText("-");
			totalResultLabel.setText("Player: ");
		}
	}
	
	/**
	 * Updates the panel that represents the house dice
	 * @param houseDice
	 */
	public void setHouseDice(DicePair houseDice) {
		if (houseDice != null) {
			houseDiceOneLabel.setText(Integer.toString(houseDice.getDice1()));
			houseDiceTwoLabel.setText(Integer.toString(houseDice.getDice2()));
			houseTotalResultLabel.setText("House: " + (houseDice.getDice1() + houseDice.getDice2()));
		}
		else {
			houseDiceOneLabel.setText("-");
			houseDiceTwoLabel.setText("-");
			houseTotalResultLabel.setText("House: ");
		}
	}
	
	/**
	 * Gets the players bet as String
	 * @return
	 */
	public String getPlayerBet() {
		return playerBetField.getText();
	}
	
	/**
	 * Displays a pop-up error message
	 * @param errorMessage
	 */
	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Clears the bet field
	 */
	public void clearInput() {
		playerBetField.setText(null);
	}
	
	/**
	 * Highlights the bet field
	 */
	public void selectBet() {
		playerBetField.requestFocus();
		playerBetField.selectAll();
	}
	
	@Override
	public void onPlayerSelected(Player player) {
		setSelectedPlayer(player);
		System.out.println("Player: " + player.toString());
		setPlayerDice(player.getRollResult());
	}
	
	@Override
	public void onPlayerDeselected(Player player) {
		System.out.println("Player deselected");
	}

	@Override
	public String getBet() {
		return playerBetField.getText();
	}

	@Override
	public Player getPlayer() {
		return selectedPlayer;
	}

}
