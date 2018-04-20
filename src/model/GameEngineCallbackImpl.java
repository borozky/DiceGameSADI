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
	
	// NO BUSINESS LOGIC ALLOWED HERE (ie. Don't create/modify/delete entities)
	
	private Logger console = Logger.getLogger("assignment1");

	public GameEngineCallbackImpl() {
		console.setLevel(Level.INFO);
	}

	
	@Override
	public void intermediateResult(Player player, DicePair dicePair, GameEngine gameEngine) {
		displayRoll(player.getPlayerName(), "ROLLING", dicePair);
	}

	
	@Override
	public void result(Player player, DicePair result, GameEngine gameEngine) {
		displayRoll(player.getPlayerName(), "*RESULT*", result);
	}

	
	@Override
	public void intermediateHouseResult(DicePair dicePair, GameEngine gameEngine) {
		displayRoll("The House", "ROLLING", dicePair);
	}

	
	@Override
	public void houseResult(DicePair result, GameEngine gameEngine) {
		displayRoll("The House", "*RESULT*", result);
		
		displayResult(gameEngine.getAllPlayers());
	}
	
	
	/**
	 * Helper method to log dice result
	 * 
	 * @param playerName
	 * @param title
	 * @param dicePair
	 */
	private void displayRoll(String playerName, String title, DicePair dicePair) {
		String log = String.format("%s: %s %s", playerName, title, dicePair);
		
		console.log(console.getLevel(), log);
	}
	
	
	/**
	 * Display players results
	 * 
	 * @param players
	 */
	private void displayResult(Collection<Player> players) {
		for (Player player : players) {
			console.log(console.getLevel(), player.toString());
		}
	}

}
