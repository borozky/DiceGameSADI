package model;

import model.interfaces.DicePair;
import model.interfaces.GameEngine;

public class DicePairImpl implements DicePair {
	
	private int dice1;
	private int dice2;
	private int numFaces;
	
	/**
	 * Minimum number of dots in a dice
	 */
	public static final int MIN_DOTS = 1;
	
	
	/**
	 * Create a dice pair (dice1 & dice2) with 6 faces for each dice.<br/>
	 * @param numFaces
	 */
	public DicePairImpl() {
		this(GameEngine.NUM_FACES);
	}
	
	
	/**
	 * Create a dice pair - dice1 & dice2. <br/>
	 * Values for dice1 and dice2 are generated automatically upon initialization
	 * 
	 * @param numFaces
	 */
	public DicePairImpl(int numFaces) {
		this.numFaces = numFaces;
		this.dice1 = getRandomInt(MIN_DOTS, numFaces);
		this.dice2 = getRandomInt(MIN_DOTS, numFaces);
	}
	
	
	@Override
	public int getDice1() {
		return dice1;
	}

	
	@Override
	public int getDice2() {
		return dice2;
	}

	
	@Override
	public int getNumFaces() {
		return numFaces;
	}
	
	
	/**
	 * Helper method for generate random numbers between min and max, inclusive.<br/>
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	private int getRandomInt(int min, int max) {
		return min + ((int) Math.floor(Math.random() * max));
	}
	
	
	@Override
	public String toString() {
		return String.format("Dice 1: %s,  Dice 2: %s .. Total: %s", 
			dice1, 
			dice2, 
			dice1 + dice2
		);
	}

}