package controllers.interfaces;

import controllers.AddNewPlayerListener;
import model.interfaces.GameEngine;
import model.interfaces.Player;

/**
 * Internal events of {@link AddNewPlayerListener} class, such as when player 
 * is added, or when a validation error has occurred, will call the methods 
 * of this interface.
 * 
 * @author user
 *
 */
public interface AddNewPlayerDelegate {
	
	/**
	 * Called when new player is successfully added
	 * 
	 * @param gameEngine
	 * @param player
	 */
	public void onAddNewPlayerSuccess(GameEngine gameEngine, Player player);
	
	/**
	 * Called when a failed to add new player.
	 * @param gameEngine
	 * @param reason
	 */
	public void onAddNewPlayerFailure(GameEngine gameEngine, String reason);
}
