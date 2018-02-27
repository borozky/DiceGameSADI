package modeltests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.SimplePlayer;

public class SimplePlayerTests {

	public SimplePlayer simplePlayer;

	
	@Before
	public void setUp() throws Exception {
		simplePlayer = new SimplePlayer("1", "Existing player 1", 2);
	}

	
	@After
	public void tearDown() throws Exception {
		
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void test_new_player_with_negative_bet_is_not_allowed() {
		simplePlayer = new SimplePlayer("1", "Sample player 1", -1);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_new_player_with_0_bet_is_not_allowed() {
		simplePlayer = new SimplePlayer("2", "Sample player 2", 0);
	}
	
	
	@Test
	public void test_new_player_with_bet_above_0_is_allowed() {
		try {
			simplePlayer = new SimplePlayer("2", "Sample player 2", 1);
		} catch(Exception e) {
			fail("Player with right bet should be allowed");
		}
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_setting_negative_points_on_existing_player_is_not_allowed() {
		simplePlayer.setPoints(-1);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_setting_0_points_on_existing_player_is_not_allowed() {
		simplePlayer.setPoints(0);
	}
	
	
	@Test
	public void test_setting_points_above_0_is_allowed() {
		try {
			simplePlayer.setPoints(1);
		} catch(Exception e) {
			fail("Setting points 1 and above should not throw exception and should be allowed");
		}
	}
	
	
	// model.SimplePlayer placeBet() will return false if invalid bet is detected
	@Test
	public void test_placing_bet_below_0_on_existing_player_returns_false() {
		// ACT
		boolean result = simplePlayer.placeBet(-1);
		
		// ASSERT
		assertFalse(result);
	}
	
	
	@Test
	public void test_placing_0_bet_on_existing_player_returns_false() {
		// ACT
		boolean result = simplePlayer.placeBet(0);
		
		// ASSERT
		assertFalse(result);
	}
	
	
	@Test
	public void test_placing_above_0_bet_is_allowed() {
		try {
			simplePlayer.placeBet(1);
		} catch(Exception e) {
			fail("Setting a proper bet above 0 should not throw exception and should be allowed");
		}
	}
	
}
