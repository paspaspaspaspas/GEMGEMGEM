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
	private BufferedImage image;
	private boolean isVisible = true;
	private EnumCards card;

	// COSTRUTTORI
	public CardV(Color mainColor, Color sidesColor, boolean isClickable, EnumCards card) {
		this.setMainColor(mainColor);
		this.setSidesColor(sidesColor);
		this.card = card;
		this.image = this.card != null ? this.card.getImage() : null;
		if (isClickable) {
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}
	}
	
	public CardV(Color mainColor, Color sidesColor, boolean isClickable, BufferedImage image) {
		this.setMainColor(mainColor);
		this.setSidesColor(sidesColor);
		this.card = null;
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
	
	public EnumCards getCard() {
		return card;
	}

	public void setCard(EnumCards card) {
		this.card = card;
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
		//QUESTA CONDIZIONE VUOTA è NECESSARIA PERCHè NEL CASO IN CUI
		//ENTRAMBE LE IMMAGINI NON SONO NULLE HO UNA TRANSIZIONE DELLE CARTE
		//E QUESTO VIENE GESTITO DALLA BOARD
		if (this.image != null && MatchV.selectedCard.getImage() != null) {
			return;
		} else if (this.image != null) {
			MatchV.selectedCard.selected(this.image, this.card);
			this.image = null;
			this.card = null;
		} else if (MatchV.selectedCard.getImage() != null) {
			this.image = MatchV.selectedCard.getImage();
			this.card = MatchV.selectedCard.getCard();
			MatchV.selectedCard.deselected();
		}
		// Questo repaint è importante altrimenti alcune zone della carta non vengono
		// aggiornate
		// qualora le carte in PlayerV e BoardV siano di dimensioni diverse
		this.repaint();
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
	/**
	 * Permette di tenere traccia di dove è posizionato il muose RISPETTO ALLO
	 * SCHERMO quando si trova su un oggetto della classe CardV. In questo modo
	 * posso rappresentare e muovere la carta selezionata o il puntatore qualore
	 * nessuna carta sia stata precedentemente scelta.
	 */
	public void mouseMoved(MouseEvent e) {
		// Questo valore e i suoi derivati hanno solo fini estetici
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
