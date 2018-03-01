package model;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

/**
 * 
 * Skeleton example implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see model.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
	
	// NO BUSINESS LOGIC ALLOWED HERE
	
	
	private Logger logger = Logger.getLogger("assignment1");

	public GameEngineCallbackImpl() {
		// FINE shows rolling output, INFO only shows result
		logger.setLevel(Level.FINE);
	}

	
	@Override
	public void intermediateResult(Player player, DicePair dicePair, GameEngine gameEngine) {
		log(player.getPlayerName(), "ROLLING", dicePair);
	}

	
	@Override
	public void result(Player player, DicePair result, GameEngine gameEngine) {
		log(player.getPlayerName(), "*RESULT*", result);
	}

	
	@Override
	public void intermediateHouseResult(DicePair dicePair, GameEngine gameEngine) {
		log("The House", "ROLLING", dicePair);
	}

	
	@Override
	public void houseResult(DicePair result, GameEngine gameEngine) {
		log("The House", "*HOUSE RESULT*", result);
		
		displayResults(gameEngine.getAllPlayers());
	}
	
	
	/**
	 * Helper method to log dice result
	 * 
	 * @param playerName
	 * @param title
	 * @param dicePair
	 */
	private void log(String playerName, String title, DicePair dicePair) {
		String log = String.format("%s: %s %s", playerName, title, dicePair);
		
		// logger.log(logger.getLevel(), log);
		System.out.println(log);
	}
	
	
	private void displayResults(Collection<Player> players) {
		for (Player player : players) {
			// logger.log(logger.getLevel(), player);
			System.out.println(player);
		}
	}

}
