package gemgemgem.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

/**
 * Rappresenta la view una carta generica posizionata sul terreno di gioco.
 * 
 * @author pasos
 *
 */
public class BoardCardV extends CardV implements MouseListener, MouseMotionListener {

	boolean mouseOverCard = false;
	boolean hasGem = false;
	Point mousePosition;

	// COSTRUTTORE
	public BoardCardV() {
		super(Color.CYAN, Color.BLUE, true, null);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(4f));
		
		if(hasGem) {
			g2.setColor(Color.GREEN);
			g2.fillRect(this.getWidth()/ 3, this.getHeight() / 3, this.getWidth()/ 3, this.getHeight() / 3);
		}

		// QUESTA CONDIZIONE SI PUO' MIGLIORARE?
		if (mouseOverCard && this.getImage() != null && MatchV.selectedCard.getImage() != null) {
			drawTriangleHovered(g2, mousePosition);
		}

	}
	
	public boolean hasGem() {
		return hasGem;
	}

	public void setHasGem(boolean hasGem) {
		this.hasGem = hasGem;
	}

	private void drawTriangleHovered(Graphics2D g2, Point mousePosition) {
		Point p1;
		Point p2;
		Point p3 = new Point(this.getWidth() / 2, this.getHeight() / 2);

		switch (whichTriangle(mousePosition)) {
		case LEFT:
			p1 = new Point(0, 0);
			p2 = new Point(0, getHeight());
			break;
		case UP:
			p1 = new Point(0, 0);
			p2 = new Point(getWidth(), 0);
			break;
		case RIGHT:
			p1 = new Point(getWidth(), 0);
			p2 = new Point(getWidth(), getHeight());
			break;
		default:
			p1 = new Point(0, getHeight());
			p2 = new Point(getWidth(), getHeight());
			break;

		}
		drawTriangle(g2, p1, p2, p3);
	}

	public EnumTriangle whichTriangle(Point mousePosition) {
		double x = mousePosition.getX();
		double y = mousePosition.getY();
		if (x < y && x < this.getHeight() - y) {
			return EnumTriangle.LEFT;
		} else if (x > y && x < this.getHeight() - y) {
			return EnumTriangle.UP;
		} else if (x > y && x > this.getHeight() - y) {
			return EnumTriangle.RIGHT;
		} else {
			return EnumTriangle.DOWN;
		}
	}

	private void drawTriangle(Graphics2D g2, Point p1, Point p2, Point p3) {
		int[] xPoints = { (int) p1.getX(), (int) p2.getX(), (int) p3.getX() };
		int[] yPoints = { (int) p1.getY(), (int) p2.getY(), (int) p3.getY() };
		g2.setColor(Color.RED);
		g2.fillPolygon(xPoints, yPoints, 3);
	}

	public void mouseExited(MouseEvent e) {
		mouseOverCard = false;
		this.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		mouseOverCard = true;
		mousePosition = e.getPoint();
		this.repaint();
	}

}
