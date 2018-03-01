package modeltests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.DicePairImpl;
import model.GameEngineCallbackImpl;
import model.GameEngineImpl;
import model.GameEngineImpl.DicePairFactory;
import model.SimplePlayer;
import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

public class GameEngineImplTests {
	
	
	// Should these be an integration test???
	
	
	// System under test (SUT)
	GameEngineImpl gameEngine;
	
	
	// @Mock
	DicePair winningDice;
	DicePair losingDice;
	DicePair mockDice;
	DicePairFactory mockWinningDiceFactory;
	DicePairFactory mockLosingDiceFactory;
	DicePairFactory mockDiceFactory;
	
	
	// Do not mock these!
	Player[] samplePlayers;
	
	
	@Before
	public void setUp() throws Exception {
		
		winningDice = new DicePair() {
			@Override public int getNumFaces() { return GameEngine.NUM_FACES; }
			@Override public int getDice2() { return 6; }
			@Override public int getDice1() { return 6; }
		};
		
		losingDice = new DicePair() {
			@Override public int getNumFaces() { return GameEngine.NUM_FACES; }
			@Override public int getDice2() { return 1; }
			@Override public int getDice1() { return 1; }
		};
		
		mockDice = new DicePair() {
			@Override public int getNumFaces() { return GameEngine.NUM_FACES; }
			@Override public int getDice2() { return 3; }
			@Override public int getDice1() { return 3; }
		};
		
		mockDiceFactory = new DicePairFactory() {
			@Override public DicePair createDicePair() { return mockDice; }
		};
		
		mockWinningDiceFactory = new DicePairFactory() {
			@Override public DicePair createDicePair() { return winningDice; }
		};
		
		mockLosingDiceFactory = new DicePairFactory() {
			@Override public DicePair createDicePair() { return losingDice; }
		};
		
		samplePlayers = new Player[] {
			new SimplePlayer("P001", "Joshua Orozco", 1000),
			new SimplePlayer("P002", "Caspar Ryan", 500)
		};
		
		gameEngine = new GameEngineImpl(mockDiceFactory);
	}

	
	@After
	public void tearDown() throws Exception {
		
	}

	
	@Test
	public void test_gameEngine_placeBet_on_player_updates_players_bet() {
		// ARRANGE
		int bet = 1000;
		Player player = samplePlayers[0];
		
		// ACT
		gameEngine.placeBet(player, bet);
		
		// ASSERT
		assertEquals(bet, player.getBet());
	}
	
	
	@Test
	public void test_gameEngine_addPlayer() {
		gameEngine.addPlayer(samplePlayers[0]);
		gameEngine.addPlayer(samplePlayers[1]);
		
		assertEquals(2, gameEngine.getAllPlayers().size());
	}
	
	
	@Test
	public void test_gameEngine$rollplayer_updates_players_rollResult() {
		
		// we don't care about callbacks
		
		// ARRANGE
		Player player = samplePlayers[1];
		int currentBet = 100;
		gameEngine.placeBet(player, currentBet);
		gameEngine.addPlayer(player);
		
		// ACT
		gameEngine.rollPlayer(player, 1, 1, 1);
		
		// ASSERT
		assertNotNull(player.getRollResult());
	}
	
	
	@Test(timeout=5000)
	public void test_when_house_wins_the_players_points_is_deducted() {
		
		// we don't care about callbacks
		
		// ARRANGE
		Player player = samplePlayers[1];
		int currentPoints = player.getPoints();
		int currentBet = 100;
		gameEngine.placeBet(player, currentBet);
		gameEngine.addPlayer(player);
		gameEngine.rollPlayer(player, 1, 1, 1);
		
		// ACT
		gameEngine.setDicePairFactory(mockWinningDiceFactory);
		gameEngine.rollHouse(1, 1, 1); // guarantees the house will win
		
		// ASSERT
		int expectedPoints = currentPoints - currentBet;
		assertEquals(expectedPoints, player.getPoints());
	}
	
	
	@Test
	public void test_when_players_wins_the_players_points_is_increased() {
		
		// we don't care about callbacks
		
		// ARRANGE
		Player player = samplePlayers[1];
		int currentPoints = player.getPoints();
		int currentBet = 100;
		gameEngine.placeBet(player, currentBet);
		gameEngine.addPlayer(player);
		gameEngine.rollPlayer(player, 1, 1, 1);
		
		// ACT
		gameEngine.setDicePairFactory(mockLosingDiceFactory);
		gameEngine.rollHouse(1, 1, 1); // guarantees the house will lose
		
		// ASSERT
		int expectedPoints = currentPoints + currentBet;
		assertEquals(expectedPoints, player.getPoints());
	}
	
	
	@Test
	public void test_when_player_vs_house_is_draw_the_players_points_remains_the_same() {
		
		// we don't care about callbacks
		
		// ARRANGE
		Player player = samplePlayers[1];
		int currentPoints = player.getPoints();
		int currentBet = 100;
		gameEngine.placeBet(player, currentBet);
		gameEngine.addPlayer(player);
		gameEngine.rollPlayer(player, 1, 1, 1);
		
		// ACT
		gameEngine.setDicePairFactory(mockDiceFactory);
		gameEngine.rollHouse(1, 1, 1); // guarantees draw
		
		// ASSERT
		int expectedPoints = currentPoints;
		assertEquals(expectedPoints, player.getPoints());
	}
	
	
	@Test
	public void test_existing_player_can_be_retrieved() {
		gameEngine.addPlayer(samplePlayers[0]);
		gameEngine.addPlayer(samplePlayers[1]);
		
		Player foundPlayer = gameEngine.getPlayer(samplePlayers[1].getPlayerId());
		assertNotNull(foundPlayer);
	}
	
	
	@Test
	public void test_nonexisting_player_cannot_be_retrieved() {
		gameEngine.addPlayer(samplePlayers[0]);
		gameEngine.addPlayer(samplePlayers[1]);
		
		Player foundPlayer = gameEngine.getPlayer("P003");
		assertNull(foundPlayer);
	}
	
	
	@Test
	public void test_existing_player_can_be_removed() {
		gameEngine.addPlayer(samplePlayers[0]);
		gameEngine.addPlayer(samplePlayers[1]);
		
		gameEngine.removePlayer(samplePlayers[1]);
		assertEquals(1, gameEngine.getAllPlayers().size());
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
