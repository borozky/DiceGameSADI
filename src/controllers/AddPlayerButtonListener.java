package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import views.GameEngineCallbackGUI;
import views.ListOfPlayersPanel;
import views.MainPanel;

public class AddPlayerButtonListener extends MouseAdapter {
	
	MainPanel mainPanel;
	GameEngine gameEngine;
	ListOfPlayersPanel listOfPlayersPanel;
	
	public AddPlayerButtonListener(MainPanel mainPanel, GameEngine gameEngine) {
		this.mainPanel = mainPanel;
		this.gameEngine = gameEngine;
	}
	
	public void setListOfPlayersPanel(ListOfPlayersPanel listOfPlayersPanel) {
		this.listOfPlayersPanel = listOfPlayersPanel;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent event) {
		String playerName = mainPanel.getPlayerName();
		String initialPoints = mainPanel.getInitialPoints();
		String playerBet = mainPanel.getPlayerBet();
		
		int bet = 0;
		int points = 0;
		
		try {
			if (playerName == null) {
				throw new Exception("Player name is required");
			}
			
			if (playerName.trim().length() == 0) {
				throw new Exception("Player name cannot be empty");
			}
			
			if (initialPoints == null || initialPoints.trim().length() == 0) {
				throw new Exception("Initial points cannot be empty");
			}
			
			if (playerBet == null || playerBet.trim().length() == 0) {
				throw new Exception("Invalid bet");
			}
			
			// these may throw NumberFormatException
			points = Integer.parseInt(initialPoints);
			bet = Integer.parseInt(playerBet);
			
			
			if (bet <= 0) {
				throw new Exception("Bet must be greater than 0");
			}
			
			if (points <= 0) {
				throw new Exception("Initial points must be greater than 0");
			}
			
			// sets the next player ID. Depends on number of players
			int numberOfPlayers = gameEngine.getAllPlayers().size();
			String nextPlayerId = (numberOfPlayers + 1) + "";
			
			// Add new player
			Player player = new SimplePlayer(nextPlayerId, playerName, points);
			player.placeBet(bet);
			gameEngine.addPlayer(player);
			
			// Update JList
			if (listOfPlayersPanel != null) {
				listOfPlayersPanel.addPlayer(player);
			}
			
			// clear input
			mainPanel.clearInput();
			
		}
		catch (NumberFormatException e) {
			String message = String.format("'%s' is not a valid number.", playerBet);
			mainPanel.displayErrorMessage(message);
		}
		catch (Exception e) {
			mainPanel.displayErrorMessage(e.getMessage());
		}
	}
	
	
}
