package views;

import javax.swing.JPanel;
import controllers.RollHouseListener;
import model.interfaces.GameEngine;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Alternative to using a JToolbar so that buttons will be clearly obvious.
 * @author Joshua Orozco
 *
 */
public class ToolbarPanel extends JPanel {

	private static final long serialVersionUID = -4570551623994814099L;
	private JButton rollHouseButton;
	private JButton addNewPlayerButton;
	private AddPlayerFormDialog dialog;

	public ToolbarPanel(GameEngine gameEngine) {
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		addNewPlayerButton = new JButton("Add new player");
		addNewPlayerButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(addNewPlayerButton);
		
		rollHouseButton = new JButton("Roll house");
		rollHouseButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(rollHouseButton);

		setupHandlers();
	}
	
	
	/**
	 * Sets the dialog that will be displayed when "Add new player" button is clicked
	 * @param dialog
	 */
	public void setAddPlayerFormDialog(AddPlayerFormDialog dialog) {
		this.dialog = dialog;
	}
	
	
	/**
	 * Sets the listener that will be called when "Roll house" is called
	 * @param rollHouseListener
	 */
	public void setRollHouseListener(RollHouseListener rollHouseListener) {
		rollHouseButton.addMouseListener(rollHouseListener);
	}

	
	/**
	 * Sets up the button handlers
	 */
	public void setupHandlers() {
		addNewPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialog == null) {
					System.err.println("Dialog not found");
					return;
				}
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
	}

}
