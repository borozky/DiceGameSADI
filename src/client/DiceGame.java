package client;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import views.GameEngineCallbackGUI;

/**
 * Main class of this project; contains the main() method
 * <p>This class should run in a separate thread in order to launch the UI properly.
 * <p>Before the UI is initialized, 2 players will be inserted inside the {@link GameEngine} class<ul>
 * <li>The Roller, which will have initial points of 1000</li>
 * <li>The Loser, which will have initial points of 500</li>
 * </ul>
 * 
 * @author Joshua Orozco
 *
 */
public class DiceGame implements Runnable {
	 
	/**
	 * Default look and feel for this project. 
	 * Other look and feels are Metal, Nimbus, CDE/Motif, Windows, Windows Classic
	 */
	public static final String LOOKANDFEEL = "Windows";
	
	
	final GameEngine gameEngine;
	
	/**
	 * @param gameEngine
	 * @throws IllegalArgumentException If gameEngine is null
	 */
	public DiceGame(GameEngine gameEngine) {
		if (gameEngine == null) {
			throw new IllegalArgumentException("Game engine cannot be null");
		}
		this.gameEngine = gameEngine;
	}
	
	
	@Override
	public void run() {
		GameEngineCallbackGUI callbackGUI = new GameEngineCallbackGUI(gameEngine);
		callbackGUI.setVisible(true);
		gameEngine.addGameEngineCallback(callbackGUI);
	}
	
	
	/**
	 * Entry point
	 */
	public static void main(String[] args) {
		setupLookAndFeel();
		
		final GameEngine gameEngine = new GameEngineImpl();
		
		// add 2 players
		gameEngine.addPlayer(new SimplePlayer("1", "The Roller", 1000));
		gameEngine.addPlayer(new SimplePlayer("2", "The Loser", 500));
		
		// run the game on separate thread
		SwingUtilities.invokeLater(new DiceGame(gameEngine));
	}
	
	
	/**
	 * Sets up the look and feel.
	 * Will use "Windows" by default.
	 */
	private static void setupLookAndFeel() {
		try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (LOOKANDFEEL.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
		catch (ClassNotFoundException 
        		| InstantiationException 
        		| IllegalAccessException
        		| UnsupportedLookAndFeelException e) {
        	System.err.println(e.getMessage());
        }

	}
}
