package model;

import model.interfaces.DicePair;

public class DicePairImpl implements DicePair {
	
	private static final int NUMFACES_STANDARD_CASINO_DICE = 6;
	
	private int dice1;
	private int dice2;
	private int numFaces;
	
	
	public DicePairImpl() {
		this(NUMFACES_STANDARD_CASINO_DICE);
	}
	
	
	public DicePairImpl(int numFaces) {
		this.numFaces = numFaces;
		this.dice1 = getRandomInt(1, numFaces);
		this.dice2 = getRandomInt(1, numFaces);
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
	
	
	private int getRandomInt(int min, int max) {
		return min + ((int) Math.floor(Math.random() * max));
	}

}