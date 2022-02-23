package gemgemgem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JComponent;

import gemgemgem.EnumCards;

/**
 * Rappresenta la view del terreno di gioco. Al momento dell'inizializzazione la
 * board è completamente vuota, pertanto riempita con carte generiche.
 * 
 * @author pasos
 *
 */
public class BoardV extends JComponent implements MouseListener {

	// ATTRIBUTI
	private int cardsPerSides = 5;
	private int dimCard;
	public static CardV[][] cards;
	public static BoardCardV[] cardsWithGems = new BoardCardV[3];

	// COSTRUTTORE
	public BoardV() {
		setPreferredSize(new Dimension(500, 500));
		cards = new CardV[cardsPerSides][cardsPerSides];
		/*
		 * All'interno del terreno di gioco stesso sono presenti diversi tipi di carte
		 * che vengono specificati nel costruttore. Nonostante ciò la board, al momento
		 * dell'inizializzazione, è riempita con diverse istanze di carte base.
		 */
		for (int i = 0; i < cardsPerSides; i++) {
			for (int j = 0; j < cardsPerSides; j++) {
				if ((i == 0 && j == 0) || (i == 0 && j == cardsPerSides - 1) || (i == cardsPerSides - 1 && j == 0)
						|| (i == cardsPerSides - 1 && j == cardsPerSides - 1))
					cards[i][j] = new CardV(false);
				else if (i == 0 || j == 0 || i == cardsPerSides - 1 || j == cardsPerSides - 1)
					cards[i][j] = new GraveyardCardV(MatchV.matchModel.getBoardCards() == null ? null
							: MatchV.matchModel.getBoardCards().get(new Integer[] { i, j }));
				else
					cards[i][j] = new BoardCardV(MatchV.matchModel.getBoardCards() == null ? null
							: MatchV.matchModel.getBoardCards().get(new Integer[] { i, j }));
				this.add(cards[i][j]);
			}
		}

		int[][] gems = createGems();
		for (int i = 0; i < 3; i++) {
			((BoardCardV) cards[gems[i][0] + 1][gems[i][1] + 1]).setHasGem(true);
			cardsWithGems[i] = ((BoardCardV) cards[gems[i][0] + 1][gems[i][1] + 1]);
		}

		for (CardV[] cardRow : cards) {
			for (CardV card : cardRow) {
				if (card instanceof BoardCardV) {
					card.addMouseListener(this);
				}
			}
		}
	}

	private int[][] createGems() {
		Random random = new Random();
		int[][] gems = { { -1, -1 }, { -1, -1 }, { -1, -1 } };
		int gemNumber = 0;
		do {
			int x = random.nextInt(3);
			int y = random.nextInt(3);
			boolean newGem = true;
			for (int i = 0; i < gemNumber; i++) {
				if (gems[i][0] == x && gems[i][1] == y) {
					newGem = false;
					break;
				}
			}
			if (newGem) {
				System.out.println(gemNumber);
				gems[gemNumber][0] = x;
				gems[gemNumber][1] = y;
				gemNumber++;
			}
		} while (gemNumber < 3);
		return gems;
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
		Point mousePosition = e.getPoint();
		if (MatchV.selectedCard.getCard() != null && cardClicked.getCard() != null) {
			for (int i = 1; i < cardsPerSides - 1; i++) {
				for (int j = 1; j < cardsPerSides - 1; j++) {
					if (cardClicked == cards[i][j]) {
						EnumTriangle direction = cardClicked.whichTriangle(mousePosition);
						if (moveIsAllowed(i, j, direction, MatchV.selectedCard.getCard())) {
							moveCards(i, j, direction, MatchV.selectedCard.getCard());
							MatchV.selectedCard.deselected();
							System.out.println("YOUR TURN IS OVER MATE cit. BoardV");
							MatchV.loadModel();
						} else {
							System.out.println("SIKE");
						}
					}
				}
			}

		}
		this.repaint();
		
		int winner;
		if((winner = checkWinner()) != -1) {
			System.out.printf("WINNER: PLAYER%d", winner);
		}

	}

	private int checkWinner () {
		boolean gameIsOver = true;
		int blueCards = 0;
		for(BoardCardV card : cardsWithGems) {
			if(card.getCard() == null) gameIsOver = false;
			else if(card.getCard().getPlayer() == 1) blueCards++;
		}
		if(gameIsOver) {
			return blueCards >= 2 ? 1 : 2;
		}
		return -1;
	}

	private boolean moveIsAllowed(int i, int j, EnumTriangle direction, EnumCards card) {
		boolean isAllowed = true;
		switch (direction) {
		case UP:
			if (j == 4)
				return false;
			if (j < 5 && cards[i][j + 1].getCard() != null) {
				isAllowed = moveIsAllowed(i, j + 1, EnumTriangle.UP, card);
			}
			return (card.getArrows()[EnumTriangle.UP.getIndex()] > cards[i][j].getCard().getArrows()[EnumTriangle.DOWN
					.getIndex()] && isAllowed) ? true : false;

		case RIGHT:
			if (i == 0)
				return false;
			if (i > 0 && cards[i - 1][j].getCard() != null) {
				isAllowed = moveIsAllowed(i - 1, j, EnumTriangle.RIGHT, card);
			}
			return (card.getArrows()[EnumTriangle.RIGHT.getIndex()] > cards[i][j].getCard()
					.getArrows()[EnumTriangle.LEFT.getIndex()] && isAllowed) ? true : false;

		case DOWN:
			if (j == 0)
				return false;
			if (j > 0 && cards[i][j - 1].getCard() != null) {
				isAllowed = moveIsAllowed(i, j - 1, EnumTriangle.DOWN, card);
			}
			return (card.getArrows()[EnumTriangle.DOWN.getIndex()] > cards[i][j].getCard().getArrows()[EnumTriangle.UP
					.getIndex()] && isAllowed) ? true : false;

		default:
			if (i == 4)
				return false;
			if (i < 5 && cards[i + 1][j].getCard() != null) {
				isAllowed = moveIsAllowed(i + 1, j, EnumTriangle.LEFT, card);
			}
			return (card.getArrows()[EnumTriangle.LEFT.getIndex()] > cards[i][j].getCard()
					.getArrows()[EnumTriangle.RIGHT.getIndex()] && isAllowed) ? true : false;
		}

	}

	public void moveCards(int i, int j, EnumTriangle direction, EnumCards newCard) {
		switch (direction) {
		case UP:
			if (j < 5 && cards[i][j + 1].getImage() != null) {
				moveCards(i, j + 1, EnumTriangle.UP, cards[i][j].getCard());
			}
			cards[i][j + 1].loadCard(cards[i][j].getCard());
			break;
		case RIGHT:
			if (i > 0 && cards[i - 1][j].getImage() != null) {
				moveCards(i - 1, j, EnumTriangle.RIGHT, cards[i][j].getCard());
			}
			cards[i - 1][j].loadCard(cards[i][j].getCard());
			break;
		case DOWN:
			if (j > 0 && cards[i][j - 1].getImage() != null) {
				moveCards(i, j - 1, EnumTriangle.DOWN, cards[i][j].getCard());
			}
			cards[i][j - 1].loadCard(cards[i][j].getCard());
			break;
		default:
			if (i < 5 && cards[i + 1][j].getImage() != null) {
				moveCards(i + 1, j, EnumTriangle.LEFT, cards[i][j].getCard());
			}

			cards[i + 1][j].loadCard(cards[i][j].getCard());
		}
		cards[i][j].loadCard(newCard);
	}

	/**
	 * 
	 * 
	 * ???
	 * 
	 * 
	 */
	public void aggiornaModel() {
		HashMap<Integer[], EnumCards> boardCards = new HashMap<>();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boardCards.put(new Integer[] { i, j }, cards[i][j].getCard());
			}
		}
		MatchV.matchModel.setBoardCards(boardCards);
		System.out.println("BoardCards aggiornate");
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

	public HashMap<Integer[], EnumCards> getCards() {
		HashMap<Integer[], EnumCards> cardsToLoad = new HashMap<>();
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				cardsToLoad.put(new Integer[] {i, j}, cards[i][j].getCard());
			}
		}
		
		return cardsToLoad;
	}

}
