package gemgemgem.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * It represents the card selected by the player or, in case no card has been
 * selected, the mouse pointer.
 * 
 * @author pas
 *
 */
public class SelectedCardV extends CellV {

	// ATTRIBUTES
	private boolean isSelected;

	// CONSTRUCTOR
	public SelectedCardV(BufferedImage image) {
		super(Color.RED, Color.MAGENTA, false, image);
		this.isSelected = false;
	}

	// GETTERS AND SETTERS
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	// METHODS
	/**
	 * It draw the component in 2 different ways: the selected card if there is one
	 * or the mouse pointer.
	 */
	protected void paintComponent(Graphics g) {
		if (isSelected) {
			super.paintComponent(g);
		} else {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(4f));
			g2.setColor(this.getMainColor());
			g2.fillOval(0, 0, this.getHeight(), this.getWidth());
		}

	}

	/**
	 * It deselects the current card switching to the representation of the mouse pointer
	 */
	public void deselected() {
		this.setSelected(false);
		this.setImage(null);
	}

	/**
	 * Make the image passed the card selected
	 * @param image : BufferedImage - the image of the selected card
	 */
	public void selected(BufferedImage image) {
		this.setSelected(true);
		this.setImage(image);
	}

	/**
	 * After receiving new informations by the controller it implements them.
	 * @param selectedCard : BufferedImage - the image of the selected card
	 */
	public void reload(BufferedImage selectedCard) {
		if (selectedCard != null) {
			this.selected(selectedCard);
		} else {
			this.deselected();
		}
		this.repaint();
	}

}
