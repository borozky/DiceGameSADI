package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import controllers.interfaces.RollPlayerDataSource;
import controllers.interfaces.RollPlayerDelegate;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import views.MainPanel;

/**
 * Handles click events for "Roll" button inside {@link MainPanel}. 
 * On click, this handler spawns a new thread to handle player's dice roll.
 * 
 * <p>Note: After the constructor is called, you should call the 
 * <code>setDataSource()</code> method to get the data from eg. {@link MainPanel}
 * 
 * @author user
 */
public class RollPlayerListener extends MouseAdapter {
	
	/**
	 * Internal process that handles player's dice rolls.
	 * 
	 * @author user
	 *
	 */
	public class RollingProcess implements Runnable {
		
		private GameEngine gameEngine;
		private Player player;
		private int bet;
		private List<RollPlayerDelegate> delegates;
		
		public RollingProcess(GameEngine gameEngine, Player player, int bet, List<RollPlayerDelegate> delegates) {
			this.gameEngine = gameEngine;
			this.player = player;
			this.bet = bet;
			this.delegates = delegates;
		}
		

		@Override
		public void run() {
			
			// roll started event
			for (RollPlayerDelegate delegate : delegates) {
				delegate.onRollPlayerStarted(gameEngine, player, bet);
			}
			
			player.placeBet(bet);
			gameEngine.rollPlayer(player, 1, 100, 500);
			
			// roll finished event
			for (RollPlayerDelegate delegate : delegates) {
				delegate.onRollPlayerFinished(gameEngine, player, bet);
			}
			
		}
		
		
	}
	
	private GameEngine gameEngine;
	
	
	/**
	 * In this handler, internal events - such as when the roll has started, 
	 * or if there are some validation occurred, will be fired. 
	 * These delegates will handle them.
	 */
	private List<RollPlayerDelegate> delegates = new ArrayList<>();
	
	
	/**
	 * Get the source of data using the {@link RollPlayerDataSource} interface 
	 * instead of getting the data manually from Swing components
	 */
	private RollPlayerDataSource dataSource;
	
	
	public RollPlayerListener(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	
	/**
	 * Add delegates to this handler.
	 * 
	 * <p>In this handler, internal events - such as when the roll has started, 
	 * or if there are some validation occurred, will be fired. 
	 * Delegates that are passed into this method will be called
	 * 
	 * @param delegate
	 */
	public void addDelegate(RollPlayerDelegate delegate) {
		delegates.add(delegate);
	}
	
	
	/**
	 * Set the data source for this handler (required).
	 * 
	 * @param dataSource
	 */
	public void setDataSource(RollPlayerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		String betStr = dataSource.getBet();
		Player player = dataSource.getPlayer();
		
		int bet = 0;
		try {
			
			if (betStr == null || betStr.trim().isEmpty()) {
				throw new Exception("Bet cannot be empty.");
			}
			
			// will throw NumberFormatException if betStr is not parse-able to integer
			bet = Integer.parseInt(betStr);
			
			if (bet <= 0) {
				throw new Exception("Bet cannot be 0 or less");
			}
			
			if (bet > player.getPoints()) {
				throw new Exception("Bet cannot be higher than your current points");
			}
			
			if (player.getRollResult() != null) {
				throw new Exception("Player already rolled the dice. Cannot roll again.");
			}
			
			// start roll player process
			Runnable rollingProcess = new RollingProcess(gameEngine, player, bet, delegates);
			Thread thread = new Thread(rollingProcess);
			thread.start();
			
		} 
		// Bet is not a number
		catch (NumberFormatException e) {
			for (RollPlayerDelegate d : delegates) {
				d.onRollPlayerError(gameEngine, player, bet, e.getMessage());
			}
		}
		// A validation error occurred
		catch (Exception e) {
			for (RollPlayerDelegate d : delegates) {
				d.onRollPlayerError(gameEngine, player, bet, e.getMessage());
			}
		}
	
		
	}
	
}
