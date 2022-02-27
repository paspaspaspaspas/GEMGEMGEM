package gemgemgem.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import gemgemgem.EnumCards;
import gemgemgem.view.EnumTriangle;

public class MatchM {
	private HashMap<Integer, EnumCards> p1Cards;
	private HashMap<List<Integer>, EnumCards> boardCards = new HashMap<>();
	private HashMap<Integer, EnumCards> p2Cards;
	private ArrayList<Integer[]> gems;
	private EnumCards selectedCard = null;

	public MatchM() {
		p1Cards = generatePlayerCards(1);
		p2Cards = generatePlayerCards(2);
		gems = generateGems();
	}
	
	public <T> List<T> getKey(T... items) {
		List<T> mutableList = new ArrayList<>();
		for (T item : items) {
			mutableList.add(item);
		}
		return Collections.unmodifiableList(mutableList);
	}
		
	private ArrayList<Integer[]> generateGems() {
		Random random = new Random();
		ArrayList<Integer[]> gems = new ArrayList<>();
		do {
			boolean isNew = true;
			int x = random.nextInt(3) + 1;
			int y = random.nextInt(3) + 1;
			/*
			 * I can't use the contains() method since Integer[] is not an immutable class.
			 * Hence the hashcode of two Integer arrays, even if they contains the same values, are
			 * different.
			 * This leads the contains() method to consider them as not the same value.
			 */
			for(Integer[] gemCoordinates : gems) {
				isNew = gemCoordinates[0] != x || gemCoordinates[1] != y;
				if(!isNew) break;
			}
			if(isNew) {
				gems.add(new Integer[] {x, y});
			}
		} while (gems.size() < 3);
		return gems;
	}

	public ModelInfo generateInfo() {
		BufferedImage[] imagesP1 = new BufferedImage[3];
		BufferedImage[][] imagesBoard = new BufferedImage[5][5];
		BufferedImage[] imagesP2 = new BufferedImage[3];
		BufferedImage selectedImage;
		for(Map.Entry<Integer, EnumCards> e : p1Cards.entrySet()) {
			imagesP1[e.getKey()] = e.getValue() != null ? e.getValue().getImage() : null;
		}
		for(Map.Entry<List<Integer>, EnumCards> e : boardCards.entrySet()) {
			imagesBoard[e.getKey().get(0)][e.getKey().get(1)] = e.getValue().getImage();
		}
		for(Map.Entry<Integer, EnumCards> e : p2Cards.entrySet()) {
			imagesP2[e.getKey()] = e.getValue().getImage();
		}
		selectedImage = selectedCard != null ? selectedCard.getImage() : null;
		return new ModelInfo(imagesP1, imagesBoard, imagesP2, gems, selectedImage);
		
	}

	private HashMap<Integer, EnumCards> generatePlayerCards(int player) {
		HashMap<Integer, EnumCards> cards = new HashMap<>();
		for (int i = 0; i < 3; i++) {
			cards.put(i, drawCard(player));
		}
		return cards;
	}

	private EnumCards drawCard(int player) {
		Random rand = new Random();
		EnumCards card;
		do {
			int index = rand.nextInt(EnumCards.values().length);
			card = EnumCards.values()[index];
		} while (card.getPlayer() != -1);
		card.setPlayer(player);
		return card;
	}

	public boolean moveIsAllowed(int i, int j, EnumTriangle direction) {
		boolean isAllowed = true;
		switch (direction) {
		case UP:
			if (j == 4)
				return false;
			if (j < 5 && boardCards.get(getKey(i, j+1)) != null) {
				isAllowed = moveIsAllowed(i, j + 1, EnumTriangle.UP);
			}
			return (selectedCard.getArrows()[EnumTriangle.UP.getIndex()] > boardCards.get(getKey(i,j))
					.getArrows()[EnumTriangle.DOWN.getIndex()] && isAllowed) ? true : false;

		case RIGHT:
			if (i == 0)
				return false;
			if (i > 0 && boardCards.get(getKey(i-1,j)) != null) {
				isAllowed = moveIsAllowed(i - 1, j, EnumTriangle.RIGHT);
			}
			return (selectedCard.getArrows()[EnumTriangle.RIGHT.getIndex()] > boardCards.get(getKey(i,j))
					.getArrows()[EnumTriangle.LEFT.getIndex()] && isAllowed) ? true : false;

		case DOWN:
			if (j == 0)
				return false;
			if (j > 0 && boardCards.get(getKey(i,j-1)) != null) {
				isAllowed = moveIsAllowed(i, j - 1, EnumTriangle.DOWN);
			}
			return (selectedCard.getArrows()[EnumTriangle.DOWN.getIndex()] > boardCards.get(getKey(i,j))
					.getArrows()[EnumTriangle.UP.getIndex()] && isAllowed) ? true : false;

		default:
			if (i == 4)
				return false;
			if (i < 5 && boardCards.get(getKey(i+1,j)) != null) {
				isAllowed = moveIsAllowed(i + 1, j, EnumTriangle.LEFT);
			}
			return (selectedCard.getArrows()[EnumTriangle.LEFT.getIndex()] > boardCards.get(getKey(i,j))
					.getArrows()[EnumTriangle.RIGHT.getIndex()] && isAllowed) ? true : false;
		}

	}
	
	public void moveCards(int i, int j, EnumTriangle direction, EnumCards newCard) {
		switch (direction) {
		case UP:
			if (j < 5 && boardCards.get(getKey(i,j+1)) != null) {
				moveCards(i, j + 1, EnumTriangle.UP, boardCards.get(getKey(i,j)));
			}
			boardCards.put(getKey(i,j+1), boardCards.get(getKey(i,j)));
			break;
		case RIGHT:
			if (i > 0 && boardCards.get(getKey(i-1,j)) != null) {
				moveCards(i-1, j, EnumTriangle.RIGHT, boardCards.get(getKey(i,j)));
			}
			boardCards.put(getKey(i-1,j), boardCards.get(getKey(i,j)));
			break;
		case DOWN:
			if (j > 0 && boardCards.get(getKey(i,j-1)) != null) {
				moveCards(i, j - 1, EnumTriangle.DOWN, boardCards.get(getKey(i,j)));
			}
			boardCards.put(getKey(i,j-1), boardCards.get(getKey(i,j)));
			break;
		default:
			if (i < 5 && boardCards.get(getKey(i+1,j)) != null) {
				moveCards(i + 1, j, EnumTriangle.LEFT, boardCards.get(getKey(i,j)));
			}

			boardCards.put(getKey(i+1,j), boardCards.get(getKey(i,j)));
		}
		boardCards.put(getKey(i,j), newCard);
		replaceCard();
		selectedCard = null;
	}
	
	public void placeCard(int i, int j) {
		if(j != -1) {
			boardCards.put(getKey(i,j), selectedCard);
			replaceCard();
		}else {
			p1Cards.put(i-1, selectedCard);
		}
		selectedCard = null;
	}

	private void replaceCard() {
		for(Map.Entry<Integer, EnumCards> benchSpot : p1Cards.entrySet()) {
			if(benchSpot.getValue() == null) {
				p1Cards.put(benchSpot.getKey(), drawCard(1));
			}
		}
	}
	
	public void pickUpCard(int i) {
		int adjustedIndex = i - 1;
		this.selectedCard = p1Cards.get(adjustedIndex);
		p1Cards.put(adjustedIndex, null);
	}

	public boolean isGameOver() {
		/*
		 * 
		 * DEVI AGGIUNGERE UN CONTROLLO PER DISPONIBILITà MOSSE LEGALI.
		 * 
		 */
		boolean isGameOver = true;
		for(Integer[] gem : gems) {
			if(boardCards.get(gem) == null) {
				isGameOver = false;
				break;
			}
		}
		return isGameOver;
	}

	public HashMap<Integer, EnumCards> getP1Cards() {
		return p1Cards;
	}

	public void setP1Cards(HashMap<Integer, EnumCards> p1Cards) {
		this.p1Cards = p1Cards;
	}

	public HashMap<List<Integer>, EnumCards> getBoardCards() {
		return boardCards;
	}

	public void setBoardCards(HashMap<List<Integer>, EnumCards> boardCards) {
		this.boardCards = boardCards;
	}

	public HashMap<Integer, EnumCards> getP2Cards() {
		return p2Cards;
	}

	public void setP2Cards(HashMap<Integer, EnumCards> p2Cards) {
		this.p2Cards = p2Cards;
	}

	public ArrayList<Integer[]> getGems() {
		return gems;
	}

	public void setGems(ArrayList<Integer[]> gems) {
		this.gems = gems;
	}

	public EnumCards getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(EnumCards selectedCard) {
		this.selectedCard = selectedCard;
	}


	

}
