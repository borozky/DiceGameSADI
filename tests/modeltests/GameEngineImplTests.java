package modeltests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.DicePair;
import model.interfaces.Player;

public class GameEngineImplTests {
	
	
	// Is this an integration test???
	
	
	// System under test (SUT)
	GameEngineImpl gameEngine;
	
	Player[] samplePlayers;
	
	
	@Before
	public void setUp() throws Exception {
		samplePlayers = new Player[] {
			new SimplePlayer("P001", "Joshua Orozco", 1000),
			new SimplePlayer("P002", "Caspar Ryan", 500)
		};
		
		gameEngine = new GameEngineImpl();
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
	
	
	@Test(timeout=5000)
	public void test_gameEngine_rollHouse() {
		
		// for now we don't care about callbacks
		
		// ARRANGE
		Player player = samplePlayers[0];
		gameEngine.placeBet(player, 100);
		gameEngine.addPlayer(player);
		
		
		// ACT
		gameEngine.rollPlayer(player, 1, 100, 20);
		gameEngine.rollHouse(1, 100, 20);
		
		
		List<Integer> possiblePoints = Arrays.asList(1100, 1000, 900);
		int resultPoints = player.getPoints();
		boolean isInPossiblePoints = possiblePoints.contains(resultPoints);
		assertTrue(isInPossiblePoints);
	}
	
	@Test
	public void test_when_house_wins_the_players_points_is_deducted() {
		
		// NO WAY TO TEST
		// because DicePairImpl is instantiated inside rollPlayer and rollHouse methods
		
	}
	
	@Test
	public void test_when_players_wins_the_players_points_is_increased() {
		
		// NO WAY TO TEST
		// because DicePairImpl is instantiated inside rollPlayer and rollHouse methods
		
	}
	
	@Test
	public void test_when_player_vs_house_is_draw_the_players_points_remains_the_same() {
		
		// NO WAY TO TEST
		// because DicePairImpl is instantiated inside rollPlayer and rollHouse methods
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
