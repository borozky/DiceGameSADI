package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;

import controllers.AddPlayerButtonListener;
import controllers.StartGameButtonListener;
import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GameEngineCallbackGUI extends JFrame implements GameEngineCallback {
	
	private static final long serialVersionUID = 142234345346756757L;
	
	JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenuItem help$aboutMenu;
	
	private JPanel rootPanel;
	private NotificationPanel notificationPanel;
	private ListOfPlayersPanel listOfPlayersPanel;
	private MainPanel mainPanel;
	
	private GameEngine gameEngine;
	
	public GameEngineCallbackGUI(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		
		// the main window is 400x300, non-resizable and centered
		setResizable(false);
		setTitle("Dice Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		
		setupMenus();
		setupBody();
		setupActionListeners();
	}
	
	@Override
	public void intermediateResult(Player player, DicePair dicePair, GameEngine gameEngine) {
		notificationPanel.setMessage(String.format("ROLLING: %s, %s\n", player.getPlayerName(), dicePair));
	}

	@Override
	public void result(Player player, DicePair result, GameEngine gameEngine) {
		notificationPanel.setMessage(String.format("RESULT: %s, %s\n", player.getPlayerName(), result));
	}

	@Override
	public void intermediateHouseResult(DicePair dicePair, GameEngine gameEngine) {
		notificationPanel.setMessage(String.format("ROLLING: %s, %s\n", "THE HOUSE", dicePair));
	}

	@Override
	public void houseResult(DicePair result, GameEngine gameEngine) {
		notificationPanel.setMessage(String.format("RESULT: %s, %s\n", "THE HOUSE", result));
		
		displayResult(gameEngine.getAllPlayers());
	}
	
	private void displayResult(Collection<Player> players) {
		listOfPlayersPanel.reloadPlayers(gameEngine.getAllPlayers());
		
		String message = "<html>";
		for (Player player : players) {
			message = message.concat(player.toString() + "<br/>");
		}
		message = message.concat("</html>");
		JOptionPane.showMessageDialog(null, message, "GAME ENDED", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void setupMenus() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		help$aboutMenu = new JMenuItem("About");
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		helpMenu.add(help$aboutMenu);
	}
	
	
	private void setupBody() {
		rootPanel = new JPanel();
		rootPanel.setBackground(Color.WHITE);
		getContentPane().add(rootPanel, BorderLayout.CENTER);
		rootPanel.setLayout(new BorderLayout(0, 0));
		
		notificationPanel = new NotificationPanel();
		rootPanel.add(notificationPanel, BorderLayout.SOUTH);
		
		List<Player> existingPlayers = new ArrayList<>(gameEngine.getAllPlayers());
		listOfPlayersPanel = new ListOfPlayersPanel(existingPlayers);
		rootPanel.add(listOfPlayersPanel, BorderLayout.EAST);
		
		mainPanel = new MainPanel();
		rootPanel.add(mainPanel, BorderLayout.CENTER);
	}
	
	private void setupActionListeners() {
		
		// When 'Add player' is clicked, this fires
		AddPlayerButtonListener addPlayerListener = new AddPlayerButtonListener(mainPanel, gameEngine);
		addPlayerListener.setListOfPlayersPanel(listOfPlayersPanel);
		mainPanel.onAddPlayer(addPlayerListener);
		
		// When 'Start Game' is clicked, this fires
		StartGameButtonListener startGameListener = new StartGameButtonListener(mainPanel, gameEngine);
		mainPanel.onStartGame(startGameListener);
		
		
	}

}
