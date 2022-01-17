package gemgemgem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class PlayerV extends JComponent {

	private int cardsPerPlayerSide = 5;
	private int dimCards;
	public CardV[] cards;

	public PlayerV() {
		setPreferredSize(new Dimension(125, 500));
		cards = new CardV[cardsPerPlayerSide];
		for (int i = 0; i < cardsPerPlayerSide; i++) {
			if (i == 0)
				cards[i] = new IconCardV();
			else if (i == cardsPerPlayerSide - 1)
				cards[i] = new DeckCardV();
			else
				cards[i] = new BenchCardV();
			this.add(cards[i]);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setCellsPositions();
	}

	private void setCellsPositions() {
		dimCards = (int)(Math.min(this.getWidth() / 2, this.getHeight() / 6));
		int paddingX = (this.getWidth() - dimCards) / 2;
		int paddingY = (this.getHeight() - dimCards * cardsPerPlayerSide) / 3;
		for (int i = 0; i < cardsPerPlayerSide; i++) {
			//Queste condizioni mi servono per separare le 3 zone nel lato di ciascun player
			if(i == 4) {
				cards[i].setBounds(paddingX, i * dimCards + (paddingY * 2), dimCards, dimCards);
			} else if (i != 0){
				cards[i].setBounds(paddingX, i * dimCards + (paddingY * 3 / 2), dimCards, dimCards);
			} else {
				cards[i].setBounds(paddingX, i * dimCards + paddingY, dimCards, dimCards);
			}
		}
	}
}
