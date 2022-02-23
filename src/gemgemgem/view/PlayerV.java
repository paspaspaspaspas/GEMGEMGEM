package gemgemgem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import gemgemgem.EnumCards;
import gemgemgem.EnumImagesUtility;

/**
 * Rappresenta la view della zona di competenza esclusiva di ciascun giocatore.
 * 
 * @author pasos
 *
 */
public class PlayerV extends JComponent implements MouseListener {

	// ATTRIBUTI
	private int cardsPerPlayerSide = 5;
	private int dimCards;
	
	public CardV[] cards;
	private int player;

	// COSTRUTTORE
	public PlayerV(int player) {
		this.player = player;
		setPreferredSize(new Dimension(125, 500));
		cards = new CardV[cardsPerPlayerSide];
		for (int i = 0; i < cardsPerPlayerSide; i++) {
			if (i == 0) {
				cards[i] = new IconCardV(player);
			} else if (i == cardsPerPlayerSide - 1)
				cards[i] = new DeckCardV(player);
			else
				cards[i] = new BenchCardV(player == 1 ? MatchV.matchModel.getP1Cards().get(i)
						: MatchV.matchModel.getP2Cards().get(i));
			this.add(cards[i]);
		}

	}

	// METODI
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setCellsPositions();

		// AGGIUNGO QUI I MOUSE LISTENER PERCHè HO BISOGNO CHE LA BOARDV SIA GIà STATA
		// INIZIALIZZATA ALTRIMENTI BOARDV.CARDS == NULL.

		for (CardV[] cardRow : BoardV.cards) {
			for (CardV card : cardRow) {
				if (card instanceof BoardCardV) {
					card.addMouseListener(this);
				}
			}
		}
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
		EnumCards card;
		do {
			int i = rand.nextInt(EnumCards.values().length);
			card = EnumCards.values()[i];
		}while(card.getPlayer() != -1);
		card.setPlayer(1);
		return card;
	}
	
	/**
	 * 
	 * 
	 * ???
	 * 
	 * 
	 */
	public void aggiornaModel() {
		HashMap<Integer, EnumCards> playerCards = new HashMap<>();
		for(int i = 1; i <= 3; i++) {
			playerCards.put(i, cards[i].getCard());
		}
		if(this.player == 1) MatchV.matchModel.setP1Cards(playerCards);
		else MatchV.matchModel.setP2Cards(playerCards);
		System.out.println("PlayerCards aggiornate");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (CardV card : cards) {
			if (card instanceof BenchCardV && card.getCard() == null && MatchV.selectedCard.getImage() == null) {
				card.setCard(estrai());
				card.setImage(card.getCard().getImage());
				card.repaint();
			}
		}

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

	public HashMap<Integer, EnumCards> getCards() {
		HashMap<Integer, EnumCards> cardsToLoad = new HashMap<>();
		for(int i = 1; i <= 3; i++) {
			cardsToLoad.put(i, cards[i].getCard());
		}
		return cardsToLoad;
	}
}
