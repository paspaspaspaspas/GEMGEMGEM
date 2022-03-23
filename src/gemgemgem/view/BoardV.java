package gemgemgem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import gemgemgem.EnumTriangle;
import gemgemgem.UtilityClass;

/**
 * This class represents the board's view that is constituted by a 3x3 grid and
 * a graveyard.
 * 
 * Even if the two aforementioned zones are different are both saved with a 5x5
 * matrix that contains CellV objects. In relation of the position the CellV
 * instance will also be a GraveyardCellV or a BoardCellV.
 * 
 * This method is also responsible of communicating with the controller through
 * the MouseListener the action performed by the player onto the board.
 * 
 * @author pas
 *
 */
public class BoardV extends JComponent implements MouseListener {

	// ATTRIBUTES
	private int cellsPerSide = UtilityClass.CELL_PER_SIDE;
	private int dimCard;
	public static CellV[][] cells;

	// CONSTRUCTOR
	public BoardV() {
		setPreferredSize(new Dimension(500, 500));
		cells = new CellV[cellsPerSide][cellsPerSide];

		createCells();
		setGems();

		for (CellV[] cardRow : cells) {
			for (CellV card : cardRow) {
				if (card instanceof BoardCellV) {
					card.addMouseListener(this);
				}
			}
		}
	}

	// SETTERS AND GETTERS

	// METHODS
	/**
	 * Thanks to this method the elements on the board are created from scratch or
	 * from the informations given by the controller.
	 */
	protected void createCells() {
		for (int i = 0; i < cellsPerSide; i++) {
			for (int j = 0; j < cellsPerSide; j++) {
				/*
				 * The board contains 3 types of cells: -the corner cells (that don't need to be
				 * represented) -the graveyard cells -the board cells
				 */
				if ((i == 0 && j == 0) || (i == 0 && j == cellsPerSide - 1) || (i == cellsPerSide - 1 && j == 0)
						|| (i == cellsPerSide - 1 && j == cellsPerSide - 1))
					cells[i][j] = new CellV();
				else if (i == 0 || j == 0 || i == cellsPerSide - 1 || j == cellsPerSide - 1)
					cells[i][j] = new GraveyardCellV(
							MatchV.info.getImagesBoard()[i][j] == null ? null : MatchV.info.getImagesBoard()[i][j]);
				else
					cells[i][j] = new BoardCellV(
							MatchV.info.getImagesBoard()[i][j] == null ? null : MatchV.info.getImagesBoard()[i][j]);
				this.add(cells[i][j]);
			}
		}
	}

	/**
	 * This method place the gems on the correct cells based on the informations
	 * given by the controller.
	 */
	protected void setGems() {
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				((BoardCellV) cells[i][j]).setHasGem(false);
			}
		}
		for (Integer[] gem : MatchV.info.getGems()) {
			((BoardCellV) cells[gem[0]][gem[1]]).setHasGem(true);
		}
	}

	/**
	 * This method is responsible to paint the whole board.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setCards();
	}

	/**
	 * This method calculates the position and the dimension of each cell based on
	 * the indexes that it has in the cells matrix.
	 */
	private void setCards() {
		dimCard = (int) (Math.min(this.getWidth(), this.getHeight()) / (cellsPerSide + 1));
		int paddingX = (this.getWidth() - dimCard * cellsPerSide) / 2;
		int paddingY = (this.getHeight() - dimCard * cellsPerSide) / 2;
		for (int i = 0; i < cellsPerSide; i++) {
			for (int j = 0; j < cellsPerSide; j++) {
				/*
				 * I don't need to set the corner cells.
				 */
				if (cells[i][j].isVisible())
					cells[i][j].setBounds(i * dimCard + paddingX, j * dimCard + paddingY, dimCard, dimCard);
			}
		}
	}

	/**
	 * When the board need to be reloaded this method is called.
	 * 
	 * It refresh the images of each cell and the position of the gems. This second
	 * action is only used at the start of the program when the second player needs
	 * to be informed about the game state.
	 * 
	 * @param images : BufferedImage[][] - all the images of the cells
	 */
	public void reload(BufferedImage[][] images) {
		for (int i = 0; i < cellsPerSide; i++) {
			for (int j = 0; j < cellsPerSide; j++) {
				cells[i][j].setImage(images[i][j]);
			}
		}
		setGems();
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		BoardCellV cardClicked = (BoardCellV) e.getComponent();
		Point mousePosition = e.getPoint();
		for (int i = 1; i < cellsPerSide - 1; i++) {
			for (int j = 1; j < cellsPerSide - 1; j++) {
				if (cardClicked == cells[i][j]) {
					if (MatchV.selectedCard.getImage() != null && cardClicked.getImage() != null) {
						EnumTriangle direction = cardClicked.whichTriangle(mousePosition);
						MatchV.controller.executeMove(i, j, direction, null);
					} else if (MatchV.selectedCard.getImage() != null) {
						MatchV.controller.placeCard(i, j, null);
					}
				}
			}
		}
	}

	/**
	 * Unused
	 */
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Unused
	 */
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Unused
	 */
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Unused
	 */
	public void mouseExited(MouseEvent e) {

	}

}
