package model;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

/**
 * 
 * Skeleton example implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see model.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
	private Logger logger = Logger.getLogger("assignment1");

	public GameEngineCallbackImpl()
	{
		// FINE shows rolling output, INFO only shows result
		logger.setLevel(Level.FINE);
	}

	
	@Override
	public void intermediateResult(Player player, DicePair dicePair, GameEngine gameEngine)
	{
		String log = String.format("%s: %s Dice 1: %s,  Dice 2: %s .. Total: %s", 
			player.getPlayerName(),
			"ROLLING",
			dicePair.getDice1(),
			dicePair.getDice2(),
			dicePair.getDice1() + dicePair.getDice2()
		);
		//logger.log(Level.FINE, log);
		System.out.println(log);
	}

	
	@Override
	public void result(Player player, DicePair result, GameEngine gameEngine)
	{
		String log = String.format("%s: %s Dice 1: %s,  Dice 2: %s .. Total: %s", 
			player.getPlayerName(),
			"*RESULT*",
			result.getDice1(),
			result.getDice2(),
			result.getDice1() + result.getDice2()
		);
		System.out.println(log);
	}

	
	@Override
	public void intermediateHouseResult(DicePair dicePair, GameEngine gameEngine) {
		String log = String.format("%s: %s Dice 1: %s,  Dice 2: %s .. Total: %s", 
			"The House",
			"ROLLING",
			dicePair.getDice1(),
			dicePair.getDice2(),
			dicePair.getDice1() + dicePair.getDice2()
		);
		System.out.println(log);
	}

	
	@Override
	public void houseResult(DicePair result, GameEngine gameEngine) {
		String log = String.format("%s: %s Dice 1: %s,  Dice 2: %s .. Total: %s", 
			"The House",
			"*HOUSE RESULT*",
			result.getDice1(),
			result.getDice2(),
			result.getDice1() + result.getDice2()
		);
		System.out.println(log);
	}

}
