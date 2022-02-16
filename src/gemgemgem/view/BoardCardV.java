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


/**
 * Rappresenta la view una carta generica posizionata sul terreno di gioco.
 * 
 * @author pasos
 *
 */
public class BoardCardV extends CardV implements MouseListener, MouseMotionListener {

	boolean mouseOverCard = false;
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

		// QUESTA CONDIZIONE SI PUO' MIGLIORARE?
		if (mouseOverCard && this.getImage() != null && MatchV.selectedCard.getImage() != null) {
			g2.setColor(Color.BLACK);
			double mouseX = mousePosition.getX();
			double mouseY = mousePosition.getY();

			drawTriangleHovered(g2, mouseX, mouseY);

		}

	}

	private void drawTriangleHovered(Graphics2D g2, double mouseX, double mouseY) {
		Point p1;
		Point p2;
		Point p3 = new Point(this.getWidth() / 2, this.getHeight() / 2);
		
		if(mouseX < mouseY && mouseX < this.getHeight() - mouseY) {
			p1 = new Point(0, 0);
			p2 = new Point(0, getHeight());
		} else if (mouseX > mouseY && mouseX < this.getHeight() - mouseY) {
			p1 = new Point(0, 0);
			p2 = new Point(getWidth(), 0);
		}else if(mouseX > mouseY && mouseX > this.getHeight() - mouseY){		
			p1 = new Point(getWidth(), 0);
			p2 = new Point(getWidth(), getHeight());
		}else {
			p1 = new Point(0, getHeight());
			p2 = new Point(getWidth(), getHeight());
		}
		drawTriangle(g2, p1, p2, p3);
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
