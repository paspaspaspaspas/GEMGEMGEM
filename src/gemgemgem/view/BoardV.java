package gemgemgem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * Rappresenta la view del terreno di gioco. Al momento dell'inizializzazione la
 * board � completamente vuota, pertanto riempita con carte generiche.
 * 
 * @author pasos
 *
 */
public class BoardV extends JComponent implements MouseListener {

	// ATTRIBUTI
	private int cardsPerSides = 5;
	private int dimCard;
	public CardV[][] cards;

	// COSTRUTTORE
	public BoardV() {
		setPreferredSize(new Dimension(500, 500));
		cards = new CardV[cardsPerSides][cardsPerSides];
		/*
		 * All'interno del terreno di gioco stesso sono presenti diversi tipi di carte
		 * che vengono specificati nel costruttore. Nonostante ci� la board, al momento
		 * dell'inizializzazione, � riempita con diverse istanze di carte base.
		 */
		for (int i = 0; i < cardsPerSides; i++) {
			for (int j = 0; j < cardsPerSides; j++) {
				if ((i == 0 && j == 0) || (i == 0 && j == cardsPerSides - 1) || (i == cardsPerSides - 1 && j == 0)
						|| (i == cardsPerSides - 1 && j == cardsPerSides - 1))
					cards[i][j] = new CardV(false);
				else if (i == 0 || j == 0 || i == cardsPerSides - 1 || j == cardsPerSides - 1)
					cards[i][j] = new GraveyardCardV();
				else
					cards[i][j] = new BoardCardV();
				this.add(cards[i][j]);
			}
		}

		for (CardV[] cardRow : cards) {
			for (CardV card : cardRow) {
				if (card instanceof BoardCardV) {
					card.addMouseListener(this);
				}
			}
		}
	}

	// METODI
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setCards();
	}

	/**
	 * Permette di posizionare accuratamente ciascuna carta sul terreno di gioco.
	 */
	private void setCards() {
		dimCard = (int) (Math.min(this.getWidth(), this.getHeight()) / 6);
		int paddingX = (this.getWidth() - dimCard * cardsPerSides) / 2;
		int paddingY = (this.getHeight() - dimCard * cardsPerSides) / 2;
		for (int i = 0; i < cardsPerSides; i++) {
			for (int j = 0; j < cardsPerSides; j++) {
				if (cards[i][j].isVisible())
					cards[i][j].setBounds(i * dimCard + paddingX, j * dimCard + paddingY, dimCard, dimCard);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		BoardCardV cardClicked = (BoardCardV) e.getComponent();
		System.out.println("1");
		if (MatchV.selectedCard.getImage() != null && cardClicked.getImage() != null) {
			System.out.println("2");
			//Non � necessario controllare carte che non siano BoardCardV
			for (int i = 1; i < cardsPerSides - 1; i++) {
				for (int j = 1; j < cardsPerSides - 1; j++) {
					if (cardClicked == cards[i][j]) {
						System.out.println("3");
						cards[i][j - 1].setImage(cards[i][j].getImage());
						cards[i][j].setImage(MatchV.selectedCard.getImage());
						MatchV.selectedCard.deselected();
					}
				}
			}
		}
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

}
