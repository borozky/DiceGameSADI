package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

public class GameEngineImpl implements GameEngine {
	
	private List<Player> players = new ArrayList<>();
	
	// used a java.util.List here because of the word 'add' in addGameEngineCallback() method
	private List<GameEngineCallback> gameEngineCallbacks = new ArrayList<>();


	@Override
	public boolean placeBet(Player player, int bet) {
		if (bet <= 0) {
			return false;
		}
		
		return player.placeBet(bet);
	}

	
	@Override
	public void rollPlayer(Player player, int initialDelay, int finalDelay, int delayIncrement)
	{
		// 5 rolls before the result is called
		// because in OutputTrace.txt, there are 5 rolls before the *RESULT*
		int numDiceRolls = GameEngine.NUM_FACES - 1;
		
		try {
			
			// 5 rolls before the final. 
			// Delay first, then delay by {delayIncrements} ms after each subsequent rolls.
			Thread.sleep(initialDelay);
			for(int i = 0; i < numDiceRolls; i++) {
				DicePair dicePair = new DicePairImpl();
				for (GameEngineCallback callback : gameEngineCallbacks) {
					callback.intermediateResult(player, dicePair, this);
				}
				Thread.sleep(delayIncrement);
			}
			
			
			// Final roll (6th roll). 
			DicePair resultingDicePair = new DicePairImpl();
			player.setRollResult(resultingDicePair);
			
			Thread.sleep(finalDelay);
			
			for (GameEngineCallback callback : gameEngineCallbacks) {
				callback.result(player, resultingDicePair, this);
			}
			
			
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void rollHouse(int initialDelay, int finalDelay, int delayIncrement) {
		
		// 5 rolls before the final roll
		// because in OutputTrace.txt, there are 5 rolls before the *RESULT*
		int numDiceRolls = GameEngine.NUM_FACES - 1;
		
		try {
			
			// 5 rolls before the final. 
			// Delay first, then delay by {delayIncrements} ms after each subsequent rolls.
			Thread.sleep(initialDelay);
			for(int i = 0; i < numDiceRolls; i++) {
				DicePair dicePair = new DicePairImpl();
				for (GameEngineCallback callback : gameEngineCallbacks) {
					callback.intermediateHouseResult(dicePair, this);
				}
				Thread.sleep(delayIncrement);
			}
			
			
			// Final house roll. 
			// Update all players' points first before triggering all callbacks 
			DicePair houseDicePairResult = new DicePairImpl();
			updatePlayersBetPoints(this.players, houseDicePairResult);
			
			Thread.sleep(finalDelay);
			
			for (GameEngineCallback callback : gameEngineCallbacks) {
				callback.houseResult(houseDicePairResult, this);
			}
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public void addPlayer(Player player) {
		players.add(player);
	}

	
	@Override
	public Player getPlayer(String id) {
		for(Player p : players) {
			if (p.getPlayerId().equals(id)) {
				return p;
			}
		}
		return null;
	}
	

	@Override
	public boolean removePlayer(Player player) {
		/**
		 * Removes the player based on players' IDs
		 * @see SimplePlayer#equals
		 */
		return players.remove(player); 
	}

	
	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		gameEngineCallbacks.add(gameEngineCallback);
	}

	
	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		return gameEngineCallbacks.remove(gameEngineCallback);
	}
	

	@Override
	public Collection<Player> getAllPlayers() {
		return players;
	}
	
	
	/**
	 * Update players bet points
	 * 
	 * @param players
	 * @param houseDicePairResult
	 */
	private void updatePlayersBetPoints(List<Player> players, DicePair houseDicePairResult) {
		for (Player player : players) {
			
			DicePair rollResult = player.getRollResult();
			if (rollResult == null) {
				System.err.printf("Player \"%s\" has no roll result", player.getPlayerName());
				continue;
			}
			
			int sumPlayer = rollResult.getDice1() + rollResult.getDice2();
			int sumHouse = houseDicePairResult.getDice1() + houseDicePairResult.getDice2();
			int newPoints = player.getPoints();
			
			
			// Player wins vs house
			if (sumPlayer > sumHouse) {
				newPoints = player.getPoints() + player.getBet();
			} 
			
			// House wins
			else if (sumPlayer < sumHouse) {
				newPoints = player.getPoints() - player.getBet();
			} 
			
			// Draw (sumPlayer === sumHouse)
			else {
				continue;
			}
			
			player.setPoints(newPoints);
		}
	}
	
	

}
