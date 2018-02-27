package client;

import model.GameEngineCallbackImpl;
import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;

/**
 * Simple console client for SADI assignment 1, 2018
 * NOTE: This code will not compile until you have implemented stubs for the supplied interfaces!
 * 
 * You must be able to compile your code WITHOUT modifying this class.
 * Additional testing should be done by copying and extending this class while ensuring this class still works.
 * 
 * @author Caspar Ryan
 * 
 */
public class SimpleTestClient
{
	public static void main(String args[])
	{
		// instantiate the GameEngine so we can make calls
		final GameEngine gameEngine = new GameEngineImpl();

		// create two test players (NOTE: you will need to implement the 3 arg contructor in SimplePlayer)
		Player[] players = new Player[]
		{ new SimplePlayer("1", "The Roller", 1000), new SimplePlayer("2", "The Loser", 500) };

		// register the callback for notifications (all logging output is done by GameEngineCallbackImpl)
		// see provided skeleton class GameEngineCallbackImpl.java
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());

		// main loop to add players place a bet and roll
		for (Player player : players)
		{
			gameEngine.placeBet(player, 100);
			gameEngine.addPlayer(player);
			gameEngine.rollPlayer(player, 1, 100, 20);
		}

		// all players have rolled so now house rolls (GameEngineCallBack is
		// called) and results are calculated
		gameEngine.rollHouse(1, 100, 20);
	}
}
