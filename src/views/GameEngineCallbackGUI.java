package views;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.awt.Dimension;

import controllers.AddNewPlayerListener;
import controllers.RollPlayerListener;
import controllers.RollHouseListener;
import controllers.interfaces.AddNewPlayerDelegate;
import controllers.interfaces.RollHouseDelegate;
import controllers.interfaces.RollPlayerDelegate;
import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Container class for all other components.
 * 
 * <p>Most of the delegates and callback are implemented by this class.
 * 
 * @author Joshua Orozco
 *
 */
public class GameEngineCallbackGUI extends JFrame implements 
	GameEngineCallback, AddNewPlayerDelegate, RollPlayerDelegate, RollHouseDelegate {
	
	private static final long serialVersionUID = 142234345346756757L;
	
	JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem file$exitMenu;
	private JMenu helpMenu;
	private JMenuItem help$aboutMenu;
	private JPanel rootPanel;
	private NotificationPanel notificationPanel;
	private ListOfPlayersPanel listOfPlayersPanel;
	private MainPanel mainPanel;
	private GameEngine gameEngine;
	private ToolbarPanel toolbarPanel;
	private AddPlayerFormDialog addPlayerFormDialog;
	private AboutDialog aboutDialog;
	
	/**
	 * Requires game engine. 
	 * This will kill the application is gameEngine is null
	 * 
	 * @param gameEngine
	 * @throws IllegalArgumentException If gameEngine is null
	 */
	public GameEngineCallbackGUI(GameEngine gameEngine) {
		if (gameEngine == null) {
			throw new IllegalArgumentException("Game engine is missing");
		}
		
		this.gameEngine = gameEngine;
		
		setTitle("Dice Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(533, 320);
		setLocationRelativeTo(null);
		
		setupMenus();
		setupBody();
		setupDialogs();
		setupActionListeners();
		setupDelegates();
	}
	
	/**
	 * Helper method to display a dialog about player results.
	 * @param players
	 */
	private void displayResult(Collection<Player> players) {
		listOfPlayersPanel.reloadPlayers(gameEngine.getAllPlayers());
		
		String message = "<html>";
		for (Player player : players) {
			message = message.concat(player.toString() + "<br/>");
		}
		message = message.concat("</html>");
		JOptionPane.showMessageDialog(null, message, "GAME ENDED", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Sets up menus and menu event handlers
	 */
	private void setupMenus() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// FILE menu
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		file$exitMenu = new JMenuItem("Exit");
		fileMenu.add(file$exitMenu);
		file$exitMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		// HELP menu
		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		help$aboutMenu = new JMenuItem("About");
		help$aboutMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
		});
		helpMenu.add(help$aboutMenu);
	}
	
	
	/**
	 * Sets up the body of the frame
	 */
	private void setupBody() {
		rootPanel = new JPanel();
		rootPanel.setBackground(Color.WHITE);
		getContentPane().add(rootPanel, BorderLayout.CENTER);
		rootPanel.setLayout(new BorderLayout(0, 0));
		
		toolbarPanel = new ToolbarPanel(gameEngine);
		rootPanel.add(toolbarPanel, BorderLayout.NORTH);
		toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		notificationPanel = new NotificationPanel();
		rootPanel.add(notificationPanel, BorderLayout.SOUTH);
		
		List<Player> existingPlayers = new ArrayList<>(gameEngine.getAllPlayers());
		listOfPlayersPanel = new ListOfPlayersPanel(existingPlayers);
		listOfPlayersPanel.setPreferredSize(new Dimension(150, 231));
		rootPanel.add(listOfPlayersPanel, BorderLayout.WEST);
		
		RollPlayerListener rollButtonListener = new RollPlayerListener(gameEngine);
		mainPanel = new MainPanel(rollButtonListener);
		rollButtonListener.addDelegate(this);
		rollButtonListener.setDataSource(mainPanel);
		rootPanel.add(mainPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Sets up {@link AddPlayerFormDialog}
	 */
	private void setupDialogs() {
		AddNewPlayerListener listener = new AddNewPlayerListener(gameEngine);
		addPlayerFormDialog = new AddPlayerFormDialog(listener);
		listener.setDataSource(addPlayerFormDialog);
		listener.addDelegate(this);
		toolbarPanel.setAddPlayerFormDialog(addPlayerFormDialog);
	}
	
	
	/**
	 * Sets up action listeners for {@link ToolbarPanel}
	 */
	private void setupActionListeners() {
		RollHouseListener rollHouseListener = new RollHouseListener(gameEngine);
		rollHouseListener.addDelegate(this);
		toolbarPanel.setRollHouseListener(rollHouseListener);
	}
	
	
	/**
	 * Sets up the delegate for {@link ListOfPlayersPanel}
	 */
	private void setupDelegates() {
		listOfPlayersPanel.setDelegate(mainPanel);
	}
	
	
	/* METHOD IMPLEMENTATIONS FOR models.interfaces.GameEngineCallback*/
	
	
	@Override
	public void intermediateResult(Player player, DicePair dicePair, GameEngine gameEngine) {
		mainPanel.setPlayerDice(dicePair);
		notificationPanel.setMessage(String.format("ROLLING: %s, %s\n", player.getPlayerName(), dicePair));
	}

	@Override
	public void result(Player player, DicePair result, GameEngine gameEngine) {
		mainPanel.setPlayerDice(result);
		notificationPanel.setMessage(String.format("RESULT: %s, %s\n", player.getPlayerName(), result));
		listOfPlayersPanel.reloadPlayers(gameEngine.getAllPlayers());
	}

	@Override
	public void intermediateHouseResult(DicePair dicePair, GameEngine gameEngine) {
		mainPanel.setHouseDice(dicePair);
		notificationPanel.setMessage(String.format("ROLLING: %s, %s\n", "THE HOUSE", dicePair));
	}

	@Override
	public void houseResult(DicePair result, GameEngine gameEngine) {
		mainPanel.setHouseDice(result);
		notificationPanel.setMessage(String.format("RESULT: %s, %s\n", "THE HOUSE", result));
		displayResult(gameEngine.getAllPlayers());
		listOfPlayersPanel.reloadPlayers(gameEngine.getAllPlayers());
		mainPanel.setSelectedPlayer(mainPanel.getSelectedPlayer());
	}
	
	
	
	/* METHOD IMPLEMENTATIONS FOR controllers.interfaces.AddNewPlayerDelegate */

	@Override
	public void onAddNewPlayerSuccess(GameEngine gameEngine, Player player) {
		addPlayerFormDialog.resetInput();
		addPlayerFormDialog.dispose();
		listOfPlayersPanel.reloadPlayers(gameEngine.getAllPlayers());
		notificationPanel.setMessage(
				String.format("Player \"%s\" with initial point of %d added.", player.getPlayerName(), player.getPoints()));
	}

	@Override
	public void onAddNewPlayerFailure(GameEngine gameEngine, String reason) {
		JOptionPane.showMessageDialog(null, reason, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
	/* METHOD IMPLEMENTATIONS FOR controllers.interfaces.RollHouseDelegate */

	@Override
	public void onRollHouseStarted(GameEngine gameEngine) {
		notificationPanel.setMessage("Roll house started");
		mainPanel.disableInteraction();
	}

	@Override
	public void onRollHouseFailed(GameEngine gameEngine, String reason) {
		JOptionPane.showMessageDialog(null, reason, "Error", JOptionPane.ERROR_MESSAGE);
		mainPanel.enableInteraction();
	}

	@Override
	public void onRollHouseEnded(GameEngine gameEngine) {
		notificationPanel.setMessage("Roll house ended");
		mainPanel.enableInteraction();
		mainPanel.setPlayerDice(null);
	}
	
	
	/* METHOD IMPLEMENTATIONS FOR controllers.interfaces.RollPlayerDelegate */

	@Override
	public void onRollPlayerStarted(GameEngine gameEngine, Player player, int bet) {
		mainPanel.disableInteraction();
		mainPanel.setHouseDice(null);
		notificationPanel.setMessage(String.format("Player \"%s\" started rolling.", player.getPlayerName()));
		listOfPlayersPanel.reloadPlayers(gameEngine.getAllPlayers());
	}
	
	@Override
	public void onRollPlayerFinished(GameEngine gameEngine, Player player, int bet) {
		mainPanel.enableInteraction();
	}

	@Override
	public void onRollPlayerError(GameEngine gameEngine, Player player, int bet, String reason) {
		JOptionPane.showMessageDialog(null, reason, "Error", JOptionPane.ERROR_MESSAGE);
		mainPanel.enableInteraction();
		mainPanel.selectBet();
	}
	
	
	
	/**
	 * Exits the application. 
	 * This the same operation as when you press the close button
	 */
	public void exit() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		System.exit(0);
	}
	
	
	/**
	 * Displays the about dialog
	 */
	public void showAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new AboutDialog();
		}
		aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		aboutDialog.setVisible(true);
	}
	
	
	

}
