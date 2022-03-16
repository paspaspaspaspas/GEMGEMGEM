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
import gemgemgem.UtilityClass;
import gemgemgem.controller.MatchC;

/**
 * It is the model class in the MVC architecture of this program.
 * 
 * First of all, it contains all the information describing the state of the
 * game and the methods needed to refresh it.
 * 
 * @author pas
 *
 */
public class MatchM {

	// ATTRIBUTES
	private MatchC controller;
	private HashMap<Integer, EnumCards> p1Cards;
	private HashMap<List<Integer>, EnumCards> boardCards = new HashMap<>();
	private HashMap<Integer, EnumCards> p2Cards;
	private ArrayList<Integer[]> gems;
	private EnumCards selectedCard = null;

	// CONSTRUCTOR
	public MatchM(MatchC controller) {
		p1Cards = generatePlayerCards(1);
		p2Cards = generatePlayerCards(2);
		gems = generateGems();
		this.controller = controller;
	}

	// GETTERS AND SETTERS
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

	// METHODS

	/**
	 * As an instance of the MatchM class is created, it generates the player's
	 * cards and set them.
	 * 
	 * @param player : int - index of the player whom cards are getting generated
	 * @return playerCards : HashMap<Integer, EnumCards> - the cards generated
	 */
	private HashMap<Integer, EnumCards> generatePlayerCards(int player) {
		HashMap<Integer, EnumCards> cards = new HashMap<>();
		for (int i = 0; i < 3; i++) {
			cards.put(i, drawCard(player));
		}
		return cards;
	}

	/**
	 * An an instance of the MatchM is created, it generates the positions of all
	 * the gems in this game.
	 * 
	 * @return gemPositions : ArrayList<Integer[]> - All the gems positions
	 */
	private ArrayList<Integer[]> generateGems() {
		Random random = new Random();
		ArrayList<Integer[]> gems = new ArrayList<>();
		do {
			boolean isNew = true;
			int x = random.nextInt(3) + 1;
			int y = random.nextInt(3) + 1;
			/*
			 * I can't use the .contains() method since Integer[] is not an immutable class.
			 * Hence the hashcode of two Integer arrays, even if they contains the same
			 * values, are different. This leads the .contains() method to consider them as
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

	/**
	 * Creates a ModelInfo's object that describes all the informations contained in
	 * the model.
	 * 
	 * It is used by the controller to synchronize the view to the model.
	 * 
	 * @return informations : ModelInfo - The description of the state of the game
	 */
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

	/**
	 * It generate and return a card from the EnumCards' pool.
	 * 
	 * If the card it has already been drawn it is considered not valid.
	 * 
	 * @param player : int - index of the player which is drawing.
	 * @return card - EnumCards : card drew
	 */
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

	/**
	 * It execute a move that pushes cards refreshing the attibutes that describes
	 * the state of the game.
	 * 
	 * Since with a single placement of a card could move more than one card, if it
	 * is the case, the method call itself recursively.
	 * 
	 * 
	 * @param x         : int - first coordinate where the card is going to be
	 *                  placed
	 * @param y         : int - second coordinate where the card is going to be
	 *                  placed
	 * @param direction : EnumTriangle - the direction in which the cards will slide
	 * @param newCard   : EnumCards - the card that is going to occupy the position
	 */
	public void moveCards(int x, int y, EnumTriangle direction, EnumCards newCard) {
		if (newCard == null) {
			newCard = this.selectedCard;
		}
		switch (direction) {
		case UP:
			if (y < 5 && boardCards.get(getKey(x, y + 1)) != null) {
				moveCards(x, y + 1, EnumTriangle.UP, boardCards.get(getKey(x, y)));
			}
			boardCards.put(getKey(x, y + 1), boardCards.get(getKey(x, y)));
			break;
		case RIGHT:
			if (x > 0 && boardCards.get(getKey(x - 1, y)) != null) {
				moveCards(x - 1, y, EnumTriangle.RIGHT, boardCards.get(getKey(x, y)));
			}
			boardCards.put(getKey(x - 1, y), boardCards.get(getKey(x, y)));
			break;
		case DOWN:
			if (y > 0 && boardCards.get(getKey(x, y - 1)) != null) {
				moveCards(x, y - 1, EnumTriangle.DOWN, boardCards.get(getKey(x, y)));
			}
			boardCards.put(getKey(x, y - 1), boardCards.get(getKey(x, y)));
			break;
		default:
			if (x < 5 && boardCards.get(getKey(x + 1, y)) != null) {
				moveCards(x + 1, y, EnumTriangle.LEFT, boardCards.get(getKey(x, y)));
			}

			boardCards.put(getKey(x + 1, y), boardCards.get(getKey(x, y)));
		}
		boardCards.put(getKey(x, y), newCard);
		replaceCard();
		selectedCard = null;
	}

	/**
	 * It places a card either on the board or back in the player's bench.
	 * 
	 * @param x    : int - first coordinate where the card is going to be placed
	 * @param y    : int - second coordinate where the card is going to be placed.
	 *             (If y == -1 then the card is destined to the player bench and not
	 *             to the board)
	 * @param card : EnumCards - card that is going to be placed
	 */
	public void placeCard(int x, int y, EnumCards card) {
		EnumCards cardToPlace = card == null ? selectedCard : card;
		if (y != -1) {
			boardCards.put(getKey(x, y), cardToPlace);
			if (card == null) {
				replaceCard();
			}
		} else {
			// - 1 because in the view the icon is counted as a card
			p1Cards.put(x - 1, cardToPlace);
		}
		selectedCard = null;
	}

	/**
	 * It places a card in the bench of one of the player.
	 * 
	 * This method is separated from the placeCard(int, int, EnumCards) because is
	 * only used at the start of the match to synchronize the two players and
	 * doesn't let place cards in the board.
	 * 
	 * @param x      : int - coordinate where the card is going to be placed on the
	 *               bench
	 * @param card   : EnumCards - card that is going to be placed
	 * @param player : int - index of the player whom bench are considering
	 */
	public void placeCard(int x, EnumCards card, int player) {
		card.setPlayer(player);
		if (player == 1) {
			p1Cards.put(x, card);
		} else {
			p2Cards.put(x, card);
		}
	}

	/**
	 * After a card has been picked up from the bench and placed on the board, this
	 * method draws a new card to replace the old one.
	 */
	private void replaceCard() {
		for (Map.Entry<Integer, EnumCards> benchSpot : p1Cards.entrySet()) {
			if (benchSpot.getValue() == null) {
				p1Cards.put(benchSpot.getKey(), drawCard(1));
				controller.send(String.format(UtilityClass.DRAW_COMMAND, benchSpot.getKey(),
						benchSpot.getValue().toString(), 2));
			}
		}
	}

	/**
	 * This method lets the player pick up a card from his bench making it become
	 * the selected card.
	 * 
	 * @param i : int - index of the card that is going to be picked up
	 */
	public void pickUpCard(int i) {
		// I have to adjust the index since in the view the icon is counted as a card
		// and here it is not
		int adjustedIndex = i - 1;
		this.selectedCard = p1Cards.get(adjustedIndex);
		p1Cards.put(adjustedIndex, null);
	}

	/**
	 * After receiving a list of items of any class, it returns an unmodifiable list
	 * that contains all of them.
	 * 
	 * @param <T>   - any class
	 * @param items : T - all the elements of contained in the key wanted
	 * @return key : List<T> - an immutable list
	 */
	public <T> List<T> getKey(T... items) {
		/*
		 * I had to use an immutable class when I needed arrays as keys of a hashmap, I
		 * had to create a method to retrieve the key given the elements of that array.
		 */
		List<T> mutableList = new ArrayList<>();
		for (T item : items) {
			mutableList.add(item);
		}
		return Collections.unmodifiableList(mutableList);
	}

	/**
	 * Generates a description of all the informations needed to synchronize an
	 * other player's match with this one.
	 * 
	 * @return description : ArrayList<String> - all the informations needed
	 */
	public ArrayList<String> getInitializationInfos() {
		ArrayList<String> data = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			data.add(String.format(UtilityClass.DRAW_COMMAND, i, p1Cards.get(i).toString(), 2));
			data.add(String.format(UtilityClass.DRAW_COMMAND, i, p2Cards.get(i).toString(), 1));
		}
		for (int i = 0; i < 3; i++) {
			data.add(String.format(UtilityClass.GEM_COMMAND, i, gems.get(i)[0], gems.get(i)[1]));
		}
		return data;
	}

	/**
	 * Given some coordinates, saves those as the position of a gem
	 * 
	 * @param nGem : int - the gem whose position are the coordinates referring to
	 * @param x : int - first coordinate of the gem's position
	 * @param y : int - second coordinate of the gem's position
	 */
	public void setGem(int nGem, int x, int y) {
		gems.set(nGem, new Integer[] { x, y });

	}

}
