package gemgemgem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Rappresenta la view della zona di competenza esclusiva di ciascun giocatore.
 * 
 * @author pasos
 *
 */
public class PlayerV extends JComponent {

	// ATTRIBUTI
	private int cardsPerPlayerSide = 5;
	private int dimCards;
	public CardV[] cards;

	// COSTRUTTORE
	public PlayerV(int player) {
		setPreferredSize(new Dimension(125, 500));
		cards = new CardV[cardsPerPlayerSide];
		if (player == 0) {
			for (int i = 0; i < cardsPerPlayerSide; i++) {
				if (i == 0) {
					cards[i] = new IconCardV(EnumImagesUtility.ICON1.getImage());
				} else if (i == cardsPerPlayerSide - 1)
					cards[i] = new DeckCardV(EnumImagesUtility.BLUE_BACK.getImage());
				else
					cards[i] = new BenchCardV(estrai().getImage());
				this.add(cards[i]);
			}
		} else {
			for (int i = 0; i < cardsPerPlayerSide; i++) {
				if (i == 0) {
					cards[i] = new IconCardV(EnumImagesUtility.ICON2.getImage());
				} else {	
					cards[i] = new DeckCardV(EnumImagesUtility.RED_BACK.getImage());
				}
				this.add(cards[i]);
			}
		}
	}

	// METODI
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setCellsPositions();
	}

	/**
	 * Mi permette di posizionare opportunamente le varie carte nella zona di
	 * competenza di ciascun player
	 */
	private void setCellsPositions() {
		dimCards = (int) (Math.min(this.getWidth() / 2, this.getHeight() / 6));
		int paddingX = (this.getWidth() - dimCards) / 2;
		int paddingY = (this.getHeight() - dimCards * cardsPerPlayerSide) / 3;
		for (int i = 0; i < cardsPerPlayerSide; i++) {
			// Queste condizioni mi servono per separare le 3 zone nel lato di ciascun
			// player
			if (i == 4) {
				cards[i].setBounds(paddingX, i * dimCards + (paddingY * 2), dimCards, dimCards);
			} else if (i != 0) {
				cards[i].setBounds(paddingX, i * dimCards + (paddingY * 3 / 2), dimCards, dimCards);
			} else {
				cards[i].setBounds(paddingX, i * dimCards + paddingY, dimCards, dimCards);
			}
		}
	}
	
	public static EnumCards estrai() {
		Random rand = new Random();
		int i = rand.nextInt(EnumCards.values().length);
		return EnumCards.values()[i];
	}
}
