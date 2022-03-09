package gemgemgem.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import gemgemgem.EnumCards;
import gemgemgem.EnumTriangle;

public class MatchM {
	
	public static final String PLACE = "PLACE %d %d %d";
	public static final String PUSH = "PUSH %d %d %s %s";
	public static final String DRAW = "DRAW %d %s %d";
	
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
			 * Hence the hashcode of two Integer arrays, even if they contains the same
			 * values, are different. This leads the contains() method to consider them as
			 * not the same value.
			 */
			for (Integer[] gemCoordinates : gems) {
				isNew = gemCoordinates[0] != x || gemCoordinates[1] != y;
				if (!isNew)
					break;
			}
			if (isNew) {
				gems.add(new Integer[] { x, y });
			}
		} while (gems.size() < 3);
		return gems;
	}

	public ModelInfo generateInfo() {
		BufferedImage[] imagesP1 = new BufferedImage[3];
		BufferedImage[][] imagesBoard = new BufferedImage[5][5];
		BufferedImage[] imagesP2 = new BufferedImage[3];
		BufferedImage selectedImage;
		for (Map.Entry<Integer, EnumCards> e : p1Cards.entrySet()) {
			imagesP1[e.getKey()] = e.getValue() != null ? e.getValue().getImage() : null;
		}
		for (Map.Entry<List<Integer>, EnumCards> e : boardCards.entrySet()) {
			imagesBoard[e.getKey().get(0)][e.getKey().get(1)] = e.getValue().getImage();
		}
		for (Map.Entry<Integer, EnumCards> e : p2Cards.entrySet()) {
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

	public boolean pushIsAllowed(int i, int j, EnumTriangle direction) {
		boolean isAllowed = true;
		switch (direction) {
		case UP:
			if (j == 4)
				return false;
			if (j < 5 && boardCards.get(getKey(i, j + 1)) != null) {
				isAllowed = pushIsAllowed(i, j + 1, EnumTriangle.UP);
			}
			return (selectedCard.getArrows()[EnumTriangle.UP.getIndex()] > boardCards.get(getKey(i, j))
					.getArrows()[EnumTriangle.DOWN.getIndex()] && isAllowed) ? true : false;

		case RIGHT:
			if (i == 0)
				return false;
			if (i > 0 && boardCards.get(getKey(i - 1, j)) != null) {
				isAllowed = pushIsAllowed(i - 1, j, EnumTriangle.RIGHT);
			}
			return (selectedCard.getArrows()[EnumTriangle.RIGHT.getIndex()] > boardCards.get(getKey(i, j))
					.getArrows()[EnumTriangle.LEFT.getIndex()] && isAllowed) ? true : false;

		case DOWN:
			if (j == 0)
				return false;
			if (j > 0 && boardCards.get(getKey(i, j - 1)) != null) {
				isAllowed = pushIsAllowed(i, j - 1, EnumTriangle.DOWN);
			}
			return (selectedCard.getArrows()[EnumTriangle.DOWN.getIndex()] > boardCards.get(getKey(i, j))
					.getArrows()[EnumTriangle.UP.getIndex()] && isAllowed) ? true : false;

		default:
			if (i == 4)
				return false;
			if (i < 5 && boardCards.get(getKey(i + 1, j)) != null) {
				isAllowed = pushIsAllowed(i + 1, j, EnumTriangle.LEFT);
			}
			return (selectedCard.getArrows()[EnumTriangle.LEFT.getIndex()] > boardCards.get(getKey(i, j))
					.getArrows()[EnumTriangle.RIGHT.getIndex()] && isAllowed) ? true : false;
		}

	}

	public void moveCards(int i, int j, EnumTriangle direction, EnumCards newCard) {
		if(newCard == null) {
			newCard = this.selectedCard;
		}
		switch (direction) {
		case UP:
			if (j < 5 && boardCards.get(getKey(i, j + 1)) != null) {
				moveCards(i, j + 1, EnumTriangle.UP, boardCards.get(getKey(i, j)));
			}
			boardCards.put(getKey(i, j + 1), boardCards.get(getKey(i, j)));
			break;
		case RIGHT:
			if (i > 0 && boardCards.get(getKey(i - 1, j)) != null) {
				moveCards(i - 1, j, EnumTriangle.RIGHT, boardCards.get(getKey(i, j)));
			}
			boardCards.put(getKey(i - 1, j), boardCards.get(getKey(i, j)));
			break;
		case DOWN:
			if (j > 0 && boardCards.get(getKey(i, j - 1)) != null) {
				moveCards(i, j - 1, EnumTriangle.DOWN, boardCards.get(getKey(i, j)));
			}
			boardCards.put(getKey(i, j - 1), boardCards.get(getKey(i, j)));
			break;
		default:
			if (i < 5 && boardCards.get(getKey(i + 1, j)) != null) {
				moveCards(i + 1, j, EnumTriangle.LEFT, boardCards.get(getKey(i, j)));
			}

			boardCards.put(getKey(i + 1, j), boardCards.get(getKey(i, j)));
		}
		boardCards.put(getKey(i, j), newCard);
		replaceCard();
		selectedCard = null;
	}

	public boolean placeIsAllowed(int i, int j) {
		if(j != -1) {		
			boolean isThereAGem = false;
			for(Integer[] gem : gems) {
				if(gem[0] == i && gem[1] == j) {
					isThereAGem = true;
					break;
				}
			}
			return !isThereAGem;
		}else {
			return p1Cards.get(i - 1) == null ? true : false;
		}
	}
	
	public void placeCard(int i, int j, EnumCards card) {
		EnumCards cardToPlace = card == null ? selectedCard : card;
		if (j != -1) {
			boardCards.put(getKey(i, j), cardToPlace);
			if(card == null) replaceCard();
		} else {
			p1Cards.put(i - 1, cardToPlace);
		}
		selectedCard = null;
	}

	private void replaceCard() {
		for (Map.Entry<Integer, EnumCards> benchSpot : p1Cards.entrySet()) {
			if (benchSpot.getValue() == null) {
				p1Cards.put(benchSpot.getKey(), drawCard(1));
			}
		}
	}

	public boolean pickUpIsAllowed() {
		return selectedCard == null ? true : false;
	}
	
	public void pickUpCard(int i) {
		int adjustedIndex = i - 1;
		this.selectedCard = p1Cards.get(adjustedIndex);
		p1Cards.put(adjustedIndex, null);
	}
	
	public void placeCard(int x, EnumCards card, int player) {
		if(player == 1) {
			p1Cards.put(x, card);
		}else {		
			p2Cards.put(x, card);
		}
	}

	public int isGameOver() {
		/*
		 * 
		 * DEVI AGGIUNGERE UN CONTROLLO PER DISPONIBILIT� MOSSE LEGALI.
		 * (di chi devi controllare le mosse???)
		 * 
		 */
		boolean isGameOver = true;
		for (Integer[] gem : gems) {
			if (boardCards.get(getKey(gem[0], gem[1])) == null) {
				isGameOver = false;
				break;
			}
		}
		if (isGameOver) {
			return declareWinner();
		}
		return -1;
	}

	private int declareWinner() {
		/*
		 * SE EVENTUALMENTE CONTROLLERAI LE MOSSE DISPONIBILI CI SAR� ANCHE LA POSSIBILIT� DI UN PAREGGIO
		 */
		int p1Points = 0;
		for (Integer[] gem : gems) {
			if (boardCards.get(getKey(gem[0], gem[1])).getPlayer() == 1) {
				p1Points++;
			}
		}
		return p1Points >= 2 ? 1 : 2;

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

	public ArrayList<String> getData() {
		ArrayList<String> data = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			data.add(String.format(DRAW, i, p1Cards.get(i).toString(), 2));
			data.add(String.format(DRAW, i, p2Cards.get(i).toString(), 1));
		}
		return data;
	}




}
