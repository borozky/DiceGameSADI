package client;

import java.awt.Font;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.GameEngineCallbackImpl;
import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import views.GameEngineCallbackGUI;

public class DiceGame implements Runnable {
	
	// other look and feels are Metal, Nimbus, CDE/Motif, Windows, Windows Classic
	public static final String LOOKANDFEEL = "Windows";
	public static final Object MAIN_THREAD_LOCK = new Object();
	
	final GameEngine gameEngine;
	
	public DiceGame(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	@Override
	public void run() {
		GameEngineCallbackGUI callbackGUI = new GameEngineCallbackGUI(gameEngine);
		callbackGUI.setVisible(true);
		gameEngine.addGameEngineCallback(callbackGUI);
	}
	
	// ENTRY POINT
	public static void main(String[] args) {
		setupLookAndFeel();
		
		final GameEngine gameEngine = new GameEngineImpl();
		gameEngine.addPlayer(new SimplePlayer("1", "The Roller", 1000));
		gameEngine.addPlayer(new SimplePlayer("2", "The Loser", 500));
		SwingUtilities.invokeLater(new DiceGame(gameEngine));
	}
	
	
	private static void setupLookAndFeel() {
		try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (DiceGame.LOOKANDFEEL.equals(info.getName())) {
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
