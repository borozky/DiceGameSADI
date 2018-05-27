package controllers.interfaces;

import controllers.RollPlayerListener;
import model.interfaces.GameEngine;
import model.interfaces.Player;


/**
 * Methods of this interface will be called when internal events of 
 * {@link RollPlayerListener} has been fired.
 * 
 * @author user
 * 
 */
public interface RollPlayerDelegate {
	
	/**
	 * Called when player's dice has finished rolling.
	 * @param gameEngine
	 * @param player
	 * @param bet
	 */
	public void onRollPlayerFinished(GameEngine gameEngine, Player player, int bet);
	
	/**
	 * Called before player's dice starts to roll.
	 * @param gameEngine
	 * @param player
	 * @param bet
	 */
	public void onRollPlayerStarted(GameEngine gameEngine, Player player, int bet);
	
	/**
	 * Called when for some reason there's an error before or when the dice was rolling
	 * 
	 * @param gameEngine
	 * @param player
	 * @param bet
	 * @param reason
	 */
	public void onRollPlayerError(GameEngine gameEngine, Player player, int bet, String reason);
}
