package modeltests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.DicePairImpl;
import model.interfaces.DicePair;
import model.interfaces.GameEngine;

public class DicePairImplTests {

	public DicePairImpl dicePair;

	
	@Before
	public void setUp() throws Exception {
		dicePair = new DicePairImpl();
	}
	

	@After
	public void tearDown() throws Exception {}

	
	@Test
	public void smoketest_random_numbers() {
		List<Integer> integers = new ArrayList<>();
		for(int i = 0; i < 1000; i++) {
			int result = 1 + ((int) Math.floor(Math.random() * 6));
			integers.add(result);
		}
		
		for(int integer: integers) {
			if (integer > 6) {
				fail("Result should be no more than 6");
			}
		}
		
		for(int integer: integers) {
			if (integer <= 0) {
				fail("Result should be less than or equal to 0");
			}
		}
		
	}
	
	
	@Test
	public void test_default_dice_pair_has_6_faces() {
		int faces = dicePair.getNumFaces();
		assertEquals(GameEngine.NUM_FACES, faces);
	}
	
	// This may generate false positives!
	@Test(timeout=1000)
	public void test_dice1_is_above_0() {
		
		int numTrials = 1000000;
		
		for(int i = 0; i < numTrials ; i++) {
			DicePair dicePair = new DicePairImpl();
			int dice1 = dicePair.getDice1();
			if(dice1 > 0) {
				continue;
			}
			else {
				fail("Dice1 below or equal to 0 has been detected. Dice 1 should be above 0");
				return;
			}
		}
		
		assertTrue(true);
	}
	
	// This may generate false positives!
	@Test(timeout=1000)
	public void test_dice2_should_be_above_0() {
		
		int numTrials = 1000000;
		
		for(int i = 0; i < numTrials ; i++) {
			DicePair dicePair = new DicePairImpl();
			int dice2 = dicePair.getDice2();
			if(dice2 > 0) {
				continue;
			}
			else {
				fail("Dice2 below or equal to 0 has been detected. Dice 2 should be above 0");
				return;
			}
		}
		
		assertTrue(true);
	}
	
	// This may generate false positives!
	@Test(timeout=1000)
	public void test_dice1_should_be_below_or_equal_numFaces() {
		
		int numTrials = 1000000;
		
		for(int i = 0; i < numTrials ; i++) {
			DicePair dicePair = new DicePairImpl();
			int dice1 = dicePair.getDice1();
			int numFaces = dicePair.getNumFaces();
			
			if(dice1 <= numFaces) {
				continue;
			}
			else {
				fail("Dice1 above " + numFaces + " has been detected. Dice 2 should be below or equal to " + numFaces);
				return;
			}
		}
		
		assertTrue(true);
	}
	
	
	// This may generate false positives!
	@Test(timeout=1000)
	public void test_dice2_should_be_below_or_equal_numFaces() {
		int numTrials = 1000000;
		
		for(int i = 0; i < numTrials ; i++) {
			DicePair dicePair = new DicePairImpl();
			int dice2 = dicePair.getDice2();
			int numFaces = dicePair.getNumFaces();
			
			if(dice2 <= numFaces) {
				continue;
			}
			else {
				fail("Dice2 above " + numFaces + " has been detected. Dice 2 should be below or equal to " + numFaces);
				return;
			}
		}
		
		assertTrue(true);
	}
	
	
	// Brittle test
	@Test
	public void test_possibilities_for_dice1_should_all_be_covered() {
		
		int numTrials = 100000;
		int numFaces = dicePair.getNumFaces();
		int[] possibilities = new int[numFaces + 1]; // 1-6
		int minPossibleDiceNumber = 1;
		possibilities[0] = 0; // this will not be covered in assertion because numFaces will never be zero
		
		for (int n = 1; n <= numFaces; n++) {
			possibilities[n] = 0;
		}
		
		for (int i = 0; i < numTrials; i++) {
			DicePair dicePair = new DicePairImpl();
			int dice1 = dicePair.getDice1();
			possibilities[dice1] += 1;
		}
		
		for(int j = minPossibleDiceNumber; j <= numFaces; j++) {
			if(possibilities[j] == 0) {
				fail("Failure. Possibility of getting a " + j + " in dice 1 is zero.");
			}
			continue;
		}
		
		assertTrue(true);
		
	}
	

}
