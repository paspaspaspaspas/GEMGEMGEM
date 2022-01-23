package gemgemgem.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

public class CardV extends JComponent implements MouseListener, MouseMotionListener {

	// private int player;
	private Color mainColor;
	private Color sidesColor;
	// private BufferedImage(?) image;
	private boolean isVisible = true;

	public CardV(Color mainColor, Color sidesColor, boolean isClickable) {
		this.setMainColor(mainColor);
		this.setSidesColor(sidesColor);

		if (isClickable) {
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}
	}

	/**
	 * Costruttore realizzato ad hoc per le carte che occupano le posizioni angolari
	 * della board
	 * 
	 * @param isVisible: boolean
	 */
	public CardV(boolean isVisible) {
		this.isVisible = isVisible;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(4f));

		g2.setColor(getMainColor());
		g2.fillRect(0, 0, getWidth(), getHeight());
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

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("I'm clickable!");

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
		// TODO Auto-generated method stub
		if (MatchV.selectedCard.isSelected()) {
			MatchV.selectedCard.setBounds((int) (MatchV.frame.getContentPane().getMousePosition().getX() - 50),
					(int) (MatchV.frame.getContentPane().getMousePosition().getY() - 50), 100, 100);
		} else {
			MatchV.selectedCard.setBounds((int) (MatchV.frame.getContentPane().getMousePosition().getX() - 10),
					(int) (MatchV.frame.getContentPane().getMousePosition().getY() - 10), 20, 20);
		}
		MatchV.selectedCard.repaint();
	}

}
