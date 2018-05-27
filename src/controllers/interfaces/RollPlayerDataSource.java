package controllers.interfaces;

import controllers.RollPlayerListener;
import model.interfaces.Player;

/**
 * Implement this interface and call the <code>setDataSource()</code>
 *  to supply data for the {@link RollPlayerListener}.
 *  
 * @author user
 *
 */
public interface RollPlayerDataSource {
	
	/**
	 * Gets the bet as the string value.
	 * By making the return type of this method as string give the 
	 * {@link RollPlayerListener} opportunity to throw exceptions when this 
	 * method returns a non-integer string
	 * 
	 * @return 
	 */
	public String getBet();
	
	/**
	 * Gets the player as the actual instance of {@link Player} class
	 * @return
	 */
	public Player getPlayer();
}
