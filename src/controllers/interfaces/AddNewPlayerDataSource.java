package controllers.interfaces;

import controllers.AddNewPlayerListener;

/**
 * Implement this interface to supply data to {@link AddNewPlayerListener} handler. 
 * @author Joshua Orozco
 *
 */
public interface AddNewPlayerDataSource {
	
	/**
	 * Gets the player name
	 * @return
	 */
	public String getPlayerName();
	
	
	/**
	 * Gets initial points as String value.
	 * 
	 * <p>This will be checked by {@link AddNewPlayerListener} 
	 * if this is parse-able to integer value.
	 * 
	 * @return
	 */
	public String getInitialPoints();
}
