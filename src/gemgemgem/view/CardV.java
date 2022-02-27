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
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import gemgemgem.EnumCards;

/**
 * Rappresenta le caratteristiche base di una carta del gioco, comuni a
 * prescindere dal posizionamento della stessa.
 * 
 * @author pasos
 *
 */
public class CardV extends JComponent implements MouseListener, MouseMotionListener {

	// ATTRIBUTI
	private Color mainColor;
	private Color sidesColor;
	protected BufferedImage image;
	private boolean isVisible = true;
	

	// COSTRUTTORI
	
	public CardV(Color mainColor, Color sidesColor, boolean isClickable, BufferedImage image) {
		this.setMainColor(mainColor);
		this.setSidesColor(sidesColor);
		this.image = image;
		if (isClickable) {
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}
	}

	/**
	 * Costruttore realizzato ad hoc per le carte che occupano le posizioni angolari
	 * della board, in quanto queste non verranno visualizzate.
	 * 
	 * @param isVisible: boolean
	 */
	public CardV(boolean isVisible) {
		this.isVisible = isVisible;
	}

	// METODI
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(4f));

		/*
		 * Qualora la carta in questione abbia un'immagine questa viene disegnata,
		 * altrimenti la view base della carta è rappresentata
		 */
		if (this.image != null) {
			g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		} else {
			g2.setColor(getMainColor());
			g2.fillRect(0, 0, getWidth(), getHeight());
		}
		g2.setColor(getSidesColor());
		g2.drawRect(+2, +2, getWidth() - 4, getHeight() - 4);
	}

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

	@Override

	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
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
