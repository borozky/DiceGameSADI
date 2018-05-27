package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import controllers.interfaces.RollHouseDelegate;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import views.ToolbarPanel;


/**
 * Class that handles click events on "Roll House" button inside {@link ToolbarPanel}. 
 * 
 * @author user
 *
 */
public class RollHouseListener extends MouseAdapter {

	
	/**
	 * Internal process that runs when "Roll House" is clicked.
	 * @author user
	 *
	 */
	class RollHouseProcess implements Runnable {
		
		private GameEngine gameEngine;
		private List<RollHouseDelegate> delegates = new ArrayList<>();
		public RollHouseProcess(GameEngine gameEngine, List<RollHouseDelegate> delegates) {
			this.gameEngine = gameEngine;
			this.delegates = delegates;
		}

		@Override
		public void run() {
			
			// roll house started
			for (RollHouseDelegate d : delegates) {
				d.onRollHouseStarted(gameEngine);
			}
			
			gameEngine.rollHouse(1, 100, 500);
			
			// reset player's roll result
			for (Player p : gameEngine.getAllPlayers()) {
				p.setRollResult(null);
			}
			
			// roll house ended
			for (RollHouseDelegate d : delegates) {
				d.onRollHouseEnded(gameEngine);
			}
			
		}
		
	}
	

	private GameEngine gameEngine;
	
	
	/**
	 * These delegates will handle all internal events fired in this class
	 */
	private List<RollHouseDelegate> delegates = new ArrayList<>();
	
	
	public RollHouseListener(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	
	/**
	 * Add a delegate for this class
	 * @param delegate
	 */
	public void addDelegate(RollHouseDelegate delegate) {
		delegates.add(delegate);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent event) {
		
		try {
			
			/**
			 * Before rolling the house
			 * - check if players has not set up their bets
			 * - check if there are players that have not rolled their dice
			 */
			
			int numPlayersWithBet = 0;
			int numPlayersWithRollResult = 0;
			for (Player p : gameEngine.getAllPlayers()) {
				if (p.getBet() > 0) {
					numPlayersWithBet += 1;
				}
				
				if (p.getRollResult() != null) {
					numPlayersWithRollResult += 1;
				}
			}
			
			
			if (numPlayersWithBet == 0) {
				throw new Exception("There are no players who have set their bets");
			}
			
			if (numPlayersWithRollResult == 0) {
				throw new Exception("There are no players that have rolled a dice.");
			}
			
			// start rolling the house dice
			Runnable houseRollProcess = new RollHouseProcess(gameEngine, delegates);
			Thread thread = new Thread(houseRollProcess);
			thread.start();
			
		}
		catch (Exception e) {
			
			// when something fails, call all delegates
			for (RollHouseDelegate d : delegates) {
				d.onRollHouseFailed(gameEngine, e.getMessage());
			}
		}
	}
	
}
