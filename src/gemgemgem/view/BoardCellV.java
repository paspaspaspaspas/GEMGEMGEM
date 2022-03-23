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
import java.awt.image.BufferedImage;

import gemgemgem.EnumTriangle;

/**
 * Represents the view of a generic cell that constitutes the board.
 * 
 * This class is a bit more complex than the other cells' view since it has to
 * act based on the mouse position when it is hovered. In fact it will
 * represents the direction of the push if a card is going to be placed in a
 * determinate position.
 * 
 * @author pas
 */
public class BoardCellV extends CellV implements MouseListener, MouseMotionListener {

	// ATTRIBUTES
	boolean mouseOverCell = false;
	boolean hasGem = false;
	Point mousePosition;

	// CONSTRUCTOR
	public BoardCellV(BufferedImage image) {
		super(Color.CYAN, Color.BLUE, true, image);
	}

	// GETTERS AND SETTERS
	public boolean hasGem() {
		return hasGem;
	}

	public void setHasGem(boolean hasGem) {
		this.hasGem = hasGem;
	}

	// METHODS
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(4f));

		/**
		 * If a gem is placed on the cell it has to be drawn.
		 */
		if (hasGem) {
			g2.setColor(Color.GREEN);
			g2.fillOval(this.getWidth() / 3, this.getHeight() * 3 / 10, this.getWidth() / 3, this.getHeight() * 2 / 5);
			g2.setColor(Color.BLUE);
			g2.drawOval(this.getWidth() / 3, this.getHeight() * 3 / 10, this.getWidth() / 3, this.getHeight() * 2 / 5);
		}

		/*
		 * Only if the player has selected a card and want to place it on top of another
		 * one already placed making it slide it is necessary to draw the directions of
		 * an eventual push.
		 */
		if (mouseOverCell && this.getImage() != null && MatchV.selectedCard.getImage() != null) {
			drawTriangleHovered(g2, mousePosition);
		}

	}

	/**
	 * Based on the mouse position, this method is able to determine which portion
	 * of the cell is hovered and draw the subsequent triangle.
	 * 
	 * @param g2            : Graphics2D - it lets the method draw the triangle
	 * @param mousePosition : Point - the coordinates of the mouse on the cell
	 */
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

	/**
	 * Thanks to the mouse position, it determines which EnumTriangle is being
	 * considered and returns it.
	 * 
	 * The different sections of the cell, each one linked to a different triangle,
	 * are the four zones created by the two diagonals of the cell.
	 * 
	 * @param mousePosition : Point - the coordinates of the mouse on the cell.
	 * @return triangle : EnumTriangle - which triangle is being hovered
	 */
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

	/**
	 * Given the vertices of a triangle, this method draws it.
	 * 
	 * @param g2 : Graphics2D - object used to draw
	 * @param p1 : Point - coordinates of the first vertex
	 * @param p2 : Point - coordinates of the second vertex
	 * @param p3 : Point - coordinates of the third vertex
	 */
	private void drawTriangle(Graphics2D g2, Point p1, Point p2, Point p3) {
		int[] xPoints = { (int) p1.getX(), (int) p2.getX(), (int) p3.getX() };
		int[] yPoints = { (int) p1.getY(), (int) p2.getY(), (int) p3.getY() };
		g2.setColor(Color.RED);
		g2.fillPolygon(xPoints, yPoints, 3);
	}

	/**
	 * When the mouse is not hovering the cell anymore this method refresh the card.
	 * This way the triangle representing the direction of the push disappears as
	 * the mouse leaves the cell.
	 */
	public void mouseExited(MouseEvent e) {
		mouseOverCell = false;
		this.repaint();
	}

	/**
	 * When the mouse is moved onto this cell, this method acknowledges its presence
	 * and gets the mouse position in order to be able to draw the right triangle.
	 */
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		mouseOverCell = true;
		mousePosition = e.getPoint();
		this.repaint();
	}

}
