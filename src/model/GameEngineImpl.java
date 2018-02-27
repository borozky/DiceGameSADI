package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

public class GameEngineImpl implements GameEngine {
	
	List<Player> players = new ArrayList<>();
	
	// used a collection here because of the word 'add' in addGameEngineCallback() method
	List<GameEngineCallback> gameEngineCallbacks = new ArrayList<>();
	

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
		// 5 pre-attempts before the result is called
		// because in OutputTrace.txt, there are 5 attempts before the *RESULT*
		int numOfPreAttempts = GameEngine.NUM_FACES - 1;
		
		try {
			// pre-attempts
			Thread.sleep(initialDelay);
			for(int i = 0; i < numOfPreAttempts; i++) {
				for (GameEngineCallback callback : gameEngineCallbacks) {
					DicePair dicePair = new DicePairImpl();
					callback.intermediateResult(player, dicePair, this);
				}
				Thread.sleep(delayIncrement);
			}
			
			// result
			Thread.sleep(finalDelay);
			for (GameEngineCallback callback : gameEngineCallbacks) {
				DicePair dicePair = new DicePairImpl();
				callback.result(player, dicePair, this);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void rollHouse(int initialDelay, int finalDelay, int delayIncrement) {
		
		// 5 pre-attempts before the result is called
		// because in OutputTrace.txt, there are 5 attempts before the *RESULT*
		int numOfPreAttempts = GameEngine.NUM_FACES - 1;
		
		try {
			
			// pre-attempts
			Thread.sleep(initialDelay);
			for(int i = 0; i < numOfPreAttempts; i++) {
				for (GameEngineCallback callback : gameEngineCallbacks) {
					DicePair dicePair = new DicePairImpl();
					callback.intermediateHouseResult(dicePair, this);
					Thread.sleep(delayIncrement);
				}
			}
			
			// result
			for (GameEngineCallback callback : gameEngineCallbacks) {
				DicePair dicePair = new DicePairImpl();
				Thread.sleep(finalDelay);
				callback.houseResult(dicePair, this);
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

}
