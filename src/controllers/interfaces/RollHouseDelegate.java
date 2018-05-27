package controllers.interfaces;

import controllers.RollHouseListener;
import model.interfaces.GameEngine;

/**
 * Internal events of {@link RollHouseListener}, such as when rolling the 
 * house has started or finished will call methods of this interface.
 * 
 * @author user
 */
public interface RollHouseDelegate {
	
	
	/**
	 * Called when {@link RollHouseListener} has started rolling the house dice.
	 * 
	 * @param gameEngine
	 */
	public void onRollHouseStarted(GameEngine gameEngine);
	
	
	/**
	 * Called when something went wrong before or while rolling the house dice.
	 * 
	 * @param gameEngine
	 * @param reason
	 */
	public void onRollHouseFailed(GameEngine gameEngine, String reason);
	
	
	/**
	 * Called after house dice finished rolling.
	 * @param gameEngine
	 */
	public void onRollHouseEnded(GameEngine gameEngine);
}
