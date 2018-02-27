package model;

import model.interfaces.DicePair;
import model.interfaces.Player;

public class SimplePlayer implements Player {
	
	private String playerName;
	private String playerId;
	private int bet;
	
	private int points = 0;
	private DicePair rollResult = null;
	
	
	public SimplePlayer(String playerId, String playerName, int initialPoints) {
		this.playerId = playerId;
		setPlayerName(playerName);
		setPoints(initialPoints);
	}

	
	@Override
	public String getPlayerName() {
		return playerName;
	}

	
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	
	@Override
	public int getPoints() {
		return points;
	}

	
	@Override
	public void setPoints(int points) {
		if(points <= 0) {
			throw new IllegalArgumentException("Cannot set points of player: " + "\"" + playerName + "\" to " + points);
		}
		
		this.points = points;
	}

	
	@Override
	public String getPlayerId() {
		return playerId;
	}

	
	@Override
	public boolean placeBet(int bet) {
		if(bet <= 0) {
			return false;
		}
		
		this.bet = bet;
		return true;
	}

	
	@Override
	public int getBet() {
		return bet;
	}

	
	@Override
	public DicePair getRollResult() {
		return rollResult;
	}
	

	@Override
	public void setRollResult(DicePair rollResult) {
		this.rollResult = rollResult;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player p = (Player) obj;
			return getPlayerId().equals(p.getPlayerId());
		}
		
		return false;
	}
	
	
}
