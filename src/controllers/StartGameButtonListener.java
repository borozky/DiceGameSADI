package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import views.MainPanel;

public class StartGameButtonListener extends MouseAdapter {

	private MainPanel mainPanel;
	private GameEngine gameEngine;
	
	public StartGameButtonListener(MainPanel mainPanel, GameEngine gameEngine) {
		this.mainPanel = mainPanel;
		this.gameEngine = gameEngine;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		Thread rollingProcess = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("GAME STARTED");
				
				for (Player player : gameEngine.getAllPlayers()) {
					player.placeBet(100);
					gameEngine.rollPlayer(player, 1, 100, 500);
				}
				gameEngine.rollHouse(1, 100, 500);
				
				mainPanel.enableInteraction();
			}
		});
		
		mainPanel.disableInteraction();
		rollingProcess.start();
	}
	
	
}
