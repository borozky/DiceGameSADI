package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import controllers.interfaces.AddNewPlayerDataSource;
import controllers.interfaces.AddNewPlayerDelegate;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import views.AddPlayerFormDialog;

/**
 * Listener for add new player events.
 * 
 * <p>This listener will fire if the "OK" button is pressed in the 
 * {@link AddPlayerFormDialog}.
 * 
 * <p>Note: After initializing this class, you have to call the 
 * <code>setDataSource()</code> method since click handlers requires the 
 * <code>playerName</code> and <code>initialBetPoints</code> from a data source
 * 
 * @author user
 *
 */
public class AddNewPlayerListener extends MouseAdapter {
	
	
	/**
	 * The game engine
	 */
	private GameEngine gameEngine;
	
	
	/**
	 * Internal events such as when player is added, 
	 * or when an error has occurred will be handled by these delegates.
	 * Use the method <code>addDelegate()</code> to add new handlers
	 */
	private List<AddNewPlayerDelegate> delegates = new ArrayList<>();
	
	
	/**
	 * Source of data for this listener.
	 * Rather than getting a reference manually from a Swing component, 
	 * supply an implementation of {@link AddNewPlayerDataSource} to get data for this listener
	 */
	private AddNewPlayerDataSource dataSource = null;
	
	
	public AddNewPlayerListener(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	
	/**
	 * Add delegate to this listener.
	 * 
	 * @param delegate
	 */
	public void addDelegate(AddNewPlayerDelegate delegate) {
		delegates.add(delegate);
	}
	
	
	/**
	 * Sets the data source for this listener (Required).
	 * <p>Rather than getting a reference manually from a Swing component, 
	 * supply an implementation of {@link AddNewPlayerDataSource} to get data for this listener
	 * 
	 * @param dataSource
	 */
	public void setDataSource(AddNewPlayerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent event) {
		
		// if setDataSource is not called, dataSource will be null.
		String playerName = dataSource.getPlayerName();
		String initialPoints = dataSource.getInitialPoints();
		
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
			
			// these may throw NumberFormatException
			points = Integer.parseInt(initialPoints);
			
			if (points <= 0) {
				throw new Exception("Initial points must be greater than 0");
			}
			
			// sets the next player ID. Depends on number of players
			int numberOfPlayers = gameEngine.getAllPlayers().size();
			String nextPlayerId = (numberOfPlayers + 1) + "";
			
			// Add new player
			Player player = new SimplePlayer(nextPlayerId, playerName, points);
			gameEngine.addPlayer(player);
			
			// on success
			for (AddNewPlayerDelegate delegate : delegates) {
				delegate.onAddNewPlayerSuccess(gameEngine, player);
			}
			
		} 
		catch (NumberFormatException e) {
			String message = String.format("'%s' is not a valid number.", initialPoints);
			for (AddNewPlayerDelegate delegate : delegates) {
				delegate.onAddNewPlayerFailure(gameEngine, message);
			}
		}
		catch (Exception e) {
			for (AddNewPlayerDelegate delegate : delegates) {
				delegate.onAddNewPlayerFailure(gameEngine, e.getMessage());
			}
		}
		
		
	}
}
