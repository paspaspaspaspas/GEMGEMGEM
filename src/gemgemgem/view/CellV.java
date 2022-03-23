package gemgemgem.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * It contains the characteristics of any cell (colors, image, visibility...).
 * </br>
 * </br>
 * These variables will be modified to be unique in relation of which class will
 * be extending CellV.
 * 
 * @author pas
 *
 */
public class CellV extends JComponent implements MouseListener, MouseMotionListener {

	// ATTRIBUTES
	private Color mainColor;
	private Color sidesColor;
	protected BufferedImage image;
	private boolean isVisible = true;

	// CONSTRUCTORS
	public CellV(Color mainColor, Color sidesColor, boolean isClickable, BufferedImage image) {
		this.setMainColor(mainColor);
		this.setSidesColor(sidesColor);
		this.image = image;
		/**
		 * Not all the cells are clickable so not all the cell will implement
		 * mouseListeners.
		 */
		if (isClickable) {
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}
	}

	/**
	 * This constructor is used only for the corner of the board. Since they are
	 * actually cells in the matrix but don't need to be represented, they are
	 * created in a unique and separate way.
	 */
	public CellV() {
		this.isVisible = false;
	}

	// GETTERS AND SETTERS
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Color getMainColor() {
		return mainColor;
	}

	public void setMainColor(Color mainColor) {
		this.mainColor = mainColor;
	}

	public Color getSidesColor() {
		return sidesColor;
	}

	public void setSidesColor(Color sidesColor) {
		this.sidesColor = sidesColor;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	// METODI
	/**
	 * This method is going to draw the view of each cell based on the information
	 * stored such as colors or images.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(4f));

		/*
		 * If the cell has a card placed on it (and consequently an image) it will be
		 * draw, otherwise the basic cell view is represented.
		 */
		if (this.image != null) {
			g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		} else {
			g2.setColor(getMainColor());
			g2.fillRect(0, 0, getWidth(), getHeight());
		}
		g2.setColor(getSidesColor());
		/**
		 * Sides need to be a little larger that the body.
		 */
		g2.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
	}

	/**
	 * This method will be implemented by the clickable cells in their own unique
	 * way.
	 */
	public void mouseClicked(MouseEvent e) {
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

	/**
	 * Unused
	 */
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	/**
	 * When the player is moving the pointer around the screen, either he has a card
	 * selected or not, this method let the view know the mouse position at all
	 * times.
	 */
	public void mouseMoved(MouseEvent e) {
		int l = Math.min(MatchV.frame.getContentPane().getHeight(), MatchV.frame.getContentPane().getWidth());
		if (MatchV.selectedCard.isSelected()) {
			MatchV.selectedCard.setBounds((int) (MatchV.frame.getContentPane().getMousePosition().getX() - l / 30),
					(int) (MatchV.frame.getContentPane().getMousePosition().getY() - l / 30), l / 15, l / 15);
			MatchV.selectedCard.setSidesColor(Color.ORANGE);
		} else {
			MatchV.selectedCard.setBounds((int) (MatchV.frame.getContentPane().getMousePosition().getX() - l / 100),
					(int) (MatchV.frame.getContentPane().getMousePosition().getY() - l / 100), l / 50, l / 50);
		}
		MatchV.selectedCard.repaint();
	}

}
