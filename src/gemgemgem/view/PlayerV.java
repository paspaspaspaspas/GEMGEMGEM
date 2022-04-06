package gemgemgem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import gemgemgem.UtilityClass;

/**
 * Represents the view characterizing each player.
 * 
 * It is formed by an icon, 3 bench cells and a deck back. All these elemets are
 * saved in the same array of CellsV elements but each of them will be an
 * instance of a different CellV derived class (IconCellV, BenchCellV,
 * DeckCellV).
 * 
 * @author pas
 *
 */
public class PlayerV extends JComponent {

	// ATTRIBUTES
	private int cellsPerPlayerSide = UtilityClass.CELL_PER_SIDE;
	private int dimCells;
	public CellV[] cells;
	private int player;

	// CONSTRUCTOR
	public PlayerV(int player) {
		this.player = player;
		setPreferredSize(new Dimension(125, 500));
		cells = new CellV[cellsPerPlayerSide];
		createCells(player);
	}
	
	//GETTERS AND SETTERS
	public CellV[] getCells() {
		return cells;
	}

	//METHODS
	/**
	 * This method creates the cells of a player side based on the informations
	 * passed by the controller.
	 * 
	 * @param player : int - which player side is this method creating
	 */
	protected void createCells(int player) {
		/*
		 * Each player's side has 3 type of cells: icon cell, bench cells and deck back
		 * cell.
		 */
		for (int i = 0; i < cellsPerPlayerSide; i++) {
			if (i == 0) {
				cells[i] = new IconCellV();
			} else if (i == cellsPerPlayerSide - 1)
				cells[i] = new DeckCellV(player);
			else
				cells[i] = new BenchCellV(
						player == 1 ? MatchV.info.getImagesP1()[i - 1] : MatchV.info.getImagesP2()[i - 1], player);
			this.add(cells[i]);
		}
		
		/*
		 * With this for cycle i link the Listeners implemented in the controller to the
		 * cells on which the player will perform the actions.
		 * 
		 * This procedure is done here since this class contains all the wanted cells
		 * and knows if they are instance of the BenchCellV class (Not all the cells
		 * implement a MouseListener).
		 * 
		 * Furthermore please notice that a player should not be able to click on the enemy cards.
		 */
		if (player == 1) {
			for (CellV card : cells) {
				if (card instanceof BenchCellV) {
					card.addMouseListener(MatchV.controller);
				}
			}
		}
	}

	/**
	 * It draw the component
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setCellsPositions();
	}

	/**
	 * This method set the position of each cell based on the index that it has in
	 * the cells array.
	 */
	private void setCellsPositions() {
		dimCells = (int) (Math.min(this.getWidth() / 2, this.getHeight() / 6));
		int paddingX = (this.getWidth() - dimCells) / 2;
		int paddingY = (this.getHeight() - dimCells * cellsPerPlayerSide) / 3;
		for (int i = 0; i < cellsPerPlayerSide; i++) {
			/**
			 * I need these conditions in order to separate the 3 section of a player side:
			 * icon, bench cells and deck back.
			 */
			if (i == 4) {
				cells[i].setBounds(paddingX, i * dimCells + (paddingY * 2), dimCells, dimCells);
			} else if (i != 0) {
				cells[i].setBounds(paddingX, i * dimCells + (paddingY * 3 / 2), dimCells, dimCells);
			} else {
				cells[i].setBounds(paddingX, i * dimCells + paddingY, dimCells, dimCells);
			}
		}
	}

	/**
	 * After receiving new informations from the controller, this method implements
	 * them onto the view of a player's side.
	 * 
	 * @param images
	 */
	public void reload(BufferedImage[] images) {
		for (int i = 1; i < 4; i++) {
			cells[i].setImage(images[i - 1]);
		}
		this.repaint();
	}
}
