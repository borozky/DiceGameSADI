package views;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import javax.swing.AbstractListModel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import model.SimplePlayer;
import model.interfaces.Player;

public class ListOfPlayersPanel extends JPanel {
	
	class PlayerListModel extends AbstractListModel<String> {
		
		private List<Player> players;
		
		public PlayerListModel() {
			this(new ArrayList<Player>());
		}
		
		public PlayerListModel(List<Player> players) {
			if (players == null) {
				throw new IllegalArgumentException("Players cannot be null");
			}
			
			this.players = players;
		}

		@Override
		public int getSize() {
			return players.size();
		}

		@Override
		public String getElementAt(int index) {
			Player player = players.get(index);
			return String.format("%s (%d)", player.getPlayerName(), player.getPoints());
		}
		
		public void addPlayer(Player player) {
			if (players == null) {
				players = new ArrayList<>();
			}
			
			players.add(player);
			fireIntervalAdded(this, 0, getSize() - 1);
		}
		
		public void removeAllPlayers() {
			players = null;
			players = new ArrayList<>();
			fireContentsChanged(this, 0, 0);
		}
		
		public void setPlayers(List<Player> players) {
			this.players = players;
			
			fireContentsChanged(this, 0, 0);
		}
	}
	
	private JScrollPane scrollPane;
	private JList<String> playerList;
	private PlayerListModel playerListModel;

	/**
	 * Create the panel.
	 */
	public ListOfPlayersPanel(List<Player> players) {
		
		playerListModel = new PlayerListModel(players);
		
		setPreferredSize(new Dimension(120, 10));
		setSize(new Dimension(120, 178));
		setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);
		
		playerList = new JList<String>();
		playerList.setBorder(new EmptyBorder(0, 5, 0, 0));
		playerList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		playerList.setModel(playerListModel);
		scrollPane.add(playerList);
		scrollPane.setViewportView(playerList);
	}
	
	public void addPlayer(Player player) {
		playerListModel.addPlayer(player);
		playerList.repaint();
		playerList.revalidate();
	}
	
	public void reloadPlayers(Collection<Player> players) {
		playerListModel.setPlayers(new ArrayList<>(players));
	}

}
