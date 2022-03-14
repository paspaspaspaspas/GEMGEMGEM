package gemgemgem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import gemgemgem.EnumTriangle;

public class BoardV extends JComponent implements MouseListener {

	private int cardsPerSides = 5;
	private int dimCard;
	public static CardV[][] cards;

	public BoardV() {
		setPreferredSize(new Dimension(500, 500));
		cards = new CardV[cardsPerSides][cardsPerSides];
		for (int i = 0; i < cardsPerSides; i++) {
			for (int j = 0; j < cardsPerSides; j++) {
				if ((i == 0 && j == 0) || (i == 0 && j == cardsPerSides - 1) || (i == cardsPerSides - 1 && j == 0)
						|| (i == cardsPerSides - 1 && j == cardsPerSides - 1))
					cards[i][j] = new CardV(false);
				else if (i == 0 || j == 0 || i == cardsPerSides - 1 || j == cardsPerSides - 1)
					cards[i][j] = new GraveyardCardV(
							MatchV.info.getImagesBoard()[i][j] == null ? null : MatchV.info.getImagesBoard()[i][j]);
				else
					cards[i][j] = new BoardCardV(
							MatchV.info.getImagesBoard()[i][j] == null ? null : MatchV.info.getImagesBoard()[i][j]);
				this.add(cards[i][j]);
			}
		}

		setGems();

		for (CardV[] cardRow : cards) {
			for (CardV card : cardRow) {
				if (card instanceof BoardCardV) {
					card.addMouseListener(this);
				}
			}
		}
	}

	protected void setGems() {
		for(int i = 1; i < 4; i++) {
			for(int j = 1; j < 4; j++) {
				((BoardCardV) cards[i][j]).setHasGem(false);
			}
		}
		for (Integer[] gem : MatchV.info.getGems()) {
			((BoardCardV) cards[gem[0]][gem[1]]).setHasGem(true);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setCards();
	}

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
		for (int i = 1; i < cardsPerSides - 1; i++) {
			for (int j = 1; j < cardsPerSides - 1; j++) {
				if (cardClicked == cards[i][j]) {
					if (MatchV.selectedCard.getImage() != null && cardClicked.getImage() != null) {
						EnumTriangle direction = cardClicked.whichTriangle(mousePosition);
						MatchV.controller.executeMove(i, j, direction, null);
					} else if (MatchV.selectedCard.getImage() != null) {
						MatchV.controller.placeCard(i, j, null);
					}
				}
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

	public void reload(BufferedImage[][] images) {
		for(int i = 0; i < cardsPerSides; i++) {
			for(int j = 0; j < cardsPerSides; j++) {
				cards[i][j].setImage(images[i][j]);
			}
		}
		setGems();
		this.repaint();
	}

}
