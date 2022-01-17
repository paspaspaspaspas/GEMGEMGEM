package gemgemgem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class BoardV extends JComponent {

	private int cardsPerSides = 5;
	private int dimCard;
	public CardV[][] cards;

	public BoardV() {
		setPreferredSize(new Dimension(500, 500));
		cards = new CardV[cardsPerSides][cardsPerSides];
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
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setCards();
	}

	private void setCards() {
		dimCard = (int)(Math.min(this.getWidth(), this.getHeight()) / 6);
		int paddingX = (this.getWidth() - dimCard * cardsPerSides) / 2;
		int paddingY = (this.getHeight() - dimCard * cardsPerSides) / 2;
		for (int i = 0; i < cardsPerSides; i++) {
			for (int j = 0; j < cardsPerSides; j++) {
				if(cards[i][j].isVisible())
					cards[i][j].setBounds(i * dimCard + paddingX, j * dimCard + paddingY, dimCard, dimCard);
			}
		}
	}
}
