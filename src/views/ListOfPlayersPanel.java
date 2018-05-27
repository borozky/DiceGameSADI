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
import java.util.Collection;
import java.util.List;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.interfaces.Player;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;


/**
 * Panel that holds information about list of {@link Player}s
 * 
 * @author user
 *
 */
public class ListOfPlayersPanel extends JPanel {
	
	private static final long serialVersionUID = -436717720103498232L;

	/**
	 * Contains methods that will be called when a player in the list is selected
	 * 
	 * @author user
	 */
	public interface Delegate {
		/**
		 * Called when a player is selected
		 * @param player
		 */
		void onPlayerSelected(Player player);
		
		/**
		 * Called when a player that is currently selected is deselected
		 * @param player
		 */
		void onPlayerDeselected(Player player);
	}
	
	
	/**
	 * Model for list of players
	 * @author user
	 *
	 */
	class PlayerListModel extends AbstractListModel<Player> {
		private static final long serialVersionUID = -3598471683962619009L;
		
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
		
		public List<Player> getPlayers() {
			return players;
		}

		@Override
		public int getSize() {
			return players.size();
		}

		/**
		 * Will call {@link Player}'s <code>toString()</code> method to 
		 * display player in the list
		 */
		@Override
		public Player getElementAt(int index) {
			return players.get(index);
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
	private JList<Player> playerList;
	private PlayerListModel playerListModel;
	private Delegate delegate;

	
	public ListOfPlayersPanel(List<Player> players) {
		setBorder(new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(5, 5, 5, 5)));
		
		playerListModel = new PlayerListModel(players);
		
		setPreferredSize(new Dimension(120, 231));
		setSize(new Dimension(120, 178));
		setLayout(new BorderLayout(0, 0));
		
		// num of players
		JLabel playerCountLabel = new JLabel(String.format("Players: %d", playerListModel.getSize()));
		playerCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(playerCountLabel, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);
		
		// list of players
		playerList = new JList<Player>();
		playerList.setBorder(new EmptyBorder(0, 5, 0, 0));
		playerList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		playerList.setModel(playerListModel);
		scrollPane.add(playerList);
		scrollPane.setViewportView(playerList);
		
		playerList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Player player = playerList.getSelectedValue();
				if (delegate != null && !e.getValueIsAdjusting()) {
					delegate.onPlayerSelected(player);
				}
			}
		});
	}
	
	/**
	 * Adds a delegate to be called upon.
	 * @param delegate
	 */
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}
	
	/**
	 * Adds new player in the list
	 * @param player
	 */
	public void addPlayer(Player player) {
		playerListModel.addPlayer(player);
		playerList.repaint();
		playerList.revalidate();
	}
	
	/**
	 * Refresh players in the list.
	 * @param players
	 */
	public void reloadPlayers(Collection<Player> players) {
		playerListModel.setPlayers(new ArrayList<>(players));
	}

}
