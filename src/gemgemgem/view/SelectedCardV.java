package gemgemgem.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

/**
 * Rappresenta la view della carta selezionata dall'utente.
 * Qualora nessuna carta sia stata selezionata rappresenta un puntatore.
 * 
 * @author pasos
 *
 */
public class SelectedCardV extends CardV {

	//ATTRIBUTI
	private boolean isSelected;

	//COSTRUTTORE
	public SelectedCardV() {
		super(Color.PINK, Color.MAGENTA, false, null);
		this.isSelected = false;
	}

	//METODI
	protected void paintComponent(Graphics g) {
		if (isSelected) {
			super.paintComponent(g);
		} else {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(4f));
			g2.setColor(Color.GREEN);
			g2.fillOval(0, 0, this.getHeight(), this.getWidth());
		}

	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public void deselected() {
		this.setSelected(false);
		this.setImage(null);
	}

}
