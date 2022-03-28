package gemgemgem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gemgemgem.EntryPoint;
import gemgemgem.EnumCards;
import gemgemgem.EnumTriangle;
import gemgemgem.UtilityClass;
import gemgemgem.model.MatchM;
import gemgemgem.net.ClientProtocol;
import gemgemgem.net.ServerProtocol;
import gemgemgem.view.EndV;
import gemgemgem.view.MatchV;

/**
 * This class acts as the controller of the MVC architecture of the
 * program.</br>
 * 
 * It links together the model and the view: it gets informations for the former
 * and listen and refresh the latter.</br>
 * </br>
 * 
 * The communication with the model happens directly trough the invocation of
 * specific methods and the responses are given thanks to the return values.
 * With the view the link is more complex: the controller refresh the data of
 * the view with a specific method, but the information that come from the view
 * to the controller are vehiculated with event's listeners.</br>
 * </br>
 * 
 * The controller it also communicates with the net protocol connected to it,
 * trading informations and messages.
 * 
 * @author pas
 *
 */
public class MatchC {

	// ATTRIBUTES
	private static MatchM model;
	private static MatchV view;

	/*
	 * Throughout the class the value of turn is either true or false indicating if
	 * the player has to play or just to wait for the adversary. The turn is set to
	 * true as I receive informations from the other player indicating that he has
	 * just execute a move. It is set to false when this player sends informations
	 * about a move that has just been done.
	 */
	private boolean turn;

	private ServerProtocol sProtocol = null;
	private ClientProtocol cProtocol = null;

	// CONSTRUCTOR
	/*
	 * When a MatchC object is created it generates the model and the view that he
	 * will coordinate. From this point on though, even if they are communicating,
	 * the three elements are independent.
	 */
	public MatchC() {
		model = new MatchM(this);
		// The view is initialize with the info of the model that i just created.
		view = new MatchV(model.generateInfo(), this);
	}

	// GETTERS AND SETTERS
	public MatchM getModel() {
		return model;
	}

	public void setModel(MatchM model) {
		this.model = model;
	}

	public void setProtocol(ServerProtocol protocol) {
		this.sProtocol = protocol;
	}

	public void setProtocol(ClientProtocol protocol) {
		this.cProtocol = protocol;
	}

	public boolean getTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	// METHODS

	/**
	 * Checks if a move that is going to push some cards on the board is actually
	 * allowed by the rules and, if so, it execute it.</br>
	 * 
	 * In case that is not allowed it print a message on the console that signals to
	 * the player the impossibility of making the move happen.</br>
	 * </br>
	 * 
	 * This method is both used after the request of a move from the view and from
	 * the protocol. There is a difference though: in the second case the move is
	 * not tested, but just execute since it has been already tested from the other
	 * player.
	 * 
	 * @param x         : int - first coordinate of the cell where i want to place
	 *                  the card
	 * @param y         : int - second coordinate of the cell where i want to place
	 *                  the card
	 * @param direction : EnumTriangle - represents the direction in which the card
	 *                  that wants to be placed is going to push
	 * @param card      : EnumCards - the card that want to be placed
	 */
	public void executeMove(int x, int y, EnumTriangle direction, EnumCards card) {
		passTurn(true);
		/*
		 * I need the user's selected card only if the player is the one making the
		 * move. If the move has already been checked from the other player the card I
		 * have selected doesn't mean anything in this resolution.
		 */
		EnumCards selectedCard = model.getSelectedCard() != null ? model.getSelectedCard() : null;
		if (pushIsAllowed(x, y, direction, selectedCard) || card != null) {
			/*
			 * Only if it has been the player to execute the move I'm going to send this
			 * information to the protocol.
			 */
			if (card == null)
				send(String.format(UtilityClass.PUSH_COMMAND, x, y, direction.toString(),
						model.getSelectedCard().toString()));
			model.moveCards(x, y, direction, card);
			reload();
			isGameOver();
		} else {
			System.out.println(UtilityClass.MOVE_NOT_ALLOWED);
		}

	}

	/**
	 * Before executing a move and modify the model, thanks to this method, the
	 * controller checks if the move is actually allowed.</br>
	 * </br>
	 * 
	 * In order to do so it confronts the strength of the pushing arrow of the card
	 * that the player is trying to place with the opposing arrows of the card
	 * already placed. Since with a single move the player is able to move a
	 * sequence of cards this method is recursive: even if the placement is allowed
	 * in a certain position if the next cell is not empty the methods calls itself
	 * with the same card but in the new position.
	 * 
	 * @param x         : int - first coordinate of the cell where i want to place
	 *                  the card
	 * @param y         : int - second coordinate of the cell where i want to place
	 *                  the card
	 * @param direction : EnumTriangle - represents the direction in which the card
	 *                  that wants to be placed is going to push
	 * @param card      : EnumCards - the card that want to be placed
	 * @return isAllowed : boolean - the value represents if the move is allowed or
	 *         it is illegal
	 */
	public static boolean pushIsAllowed(int x, int y, EnumTriangle direction, EnumCards card) {
		if (card == null)
			return false;

		boolean isAllowed = true;
		HashMap<List<Integer>, EnumCards> boardCards = model.getBoardCards();
		switch (direction) {
		case UP:
			if (y == 4)
				return false;
			if (y < 5 && boardCards.get(model.getKey(x, y + 1)) != null) {
				isAllowed = pushIsAllowed(x, y + 1, EnumTriangle.UP, card);
			}
			return (card.getArrows()[EnumTriangle.UP.getIndex()] > boardCards.get(model.getKey(x, y))
					.getArrows()[EnumTriangle.DOWN.getIndex()] && isAllowed) ? true : false;

		case RIGHT:
			if (x == 0)
				return false;
			if (x > 0 && boardCards.get(model.getKey(x - 1, y)) != null) {
				isAllowed = pushIsAllowed(x - 1, y, EnumTriangle.RIGHT, card);
			}
			return (card.getArrows()[EnumTriangle.RIGHT.getIndex()] > boardCards.get(model.getKey(x, y))
					.getArrows()[EnumTriangle.LEFT.getIndex()] && isAllowed) ? true : false;

		case DOWN:
			if (y == 0)
				return false;
			if (y > 0 && boardCards.get(model.getKey(x, y - 1)) != null) {
				isAllowed = pushIsAllowed(x, y - 1, EnumTriangle.DOWN, card);
			}
			return (card.getArrows()[EnumTriangle.DOWN.getIndex()] > boardCards.get(model.getKey(x, y))
					.getArrows()[EnumTriangle.UP.getIndex()] && isAllowed) ? true : false;

		default:
			if (x == 4)
				return false;
			if (x < 5 && boardCards.get(model.getKey(x + 1, y)) != null) {
				isAllowed = pushIsAllowed(x + 1, y, EnumTriangle.LEFT, card);
			}
			return (card.getArrows()[EnumTriangle.LEFT.getIndex()] > boardCards.get(model.getKey(x, y))
					.getArrows()[EnumTriangle.RIGHT.getIndex()] && isAllowed) ? true : false;
		}

	}

	/**
	 * It places a card in given coordinates.</br>
	 * This method is only used when the cell selected in empty. In case this wasn't
	 * true, it prints out an error.
	 * 
	 * @param x    : int - first coordinate of the cell where i want to place the
	 *             card
	 * @param y    : int - second coordinate of the cell where i want to place the
	 *             card
	 * @param card : EnumCards - card that need to be placed
	 */
	public void placeCard(int x, int y, EnumCards card) {
		passTurn(true);
		if (placeIsAllowed(x, y) || card != null) {
			/*
			 * I send this piece of information only if i didn't receive it from the other
			 * player and I actually placed a card on the board and NOT back to my own bench
			 */
			if (card == null && y != -1)
				send(String.format(UtilityClass.PLACE_COMMAND, x, y, model.getSelectedCard().toString()));
			model.placeCard(x, y, card);
			reload();
			isGameOver();
		} else {
			System.out.println(UtilityClass.WRONG_PLACEMENT);
		}

	}

	/**
	 * This is an handler for a case that would not work with the standard placeCard
	 * method. It works the same way, but it is only used when the request is coming
	 * from the other player and it simulates the draw of the adversary. It is also
	 * used when the two players are synchronizing the informations at the start of
	 * the match.</br>
	 * </br>
	 * 
	 * It has to be implemented in a different method because it has to adjourn the
	 * player value of the EnumCard placed.
	 * 
	 * @param x      : int - coordinate of the card that need to be placed on the
	 *               bench
	 * @param card   : EnumCards - card that need to be placed
	 * @param player : int - player that drew that card
	 */
	public void placeCard(int x, EnumCards card, int player) {
		passTurn(true);
		model.placeCard(x, card, player);
		isGameOver();
		reload();

	}

	/**
	 * This method checks if a card can be placed in a certain position.</br>
	 * 
	 * The placements is considered invalid if the player tries to put the card onto
	 * a gem even if the gem has not been claimed yet.
	 * 
	 * @param x : int - first coordinate of the card that need to be placed
	 * @param y : int - second coordinate of the card that need to be placed
	 * @return isAllowed : boolean - the value represents if the placement is
	 *         allowed or is illegal
	 */
	public boolean placeIsAllowed(int x, int y) {
		if (y != -1) {
			boolean isThereAGem = false;
			for (Integer[] gem : model.getGems()) {
				if (gem[0] == x && gem[1] == y) {
					isThereAGem = true;
					break;
				}
			}
			return !isThereAGem;
		} else {
			return model.getP1Cards().get(x - 1) == null ? true : false;
		}
	}

	/**
	 * If it is the player's turn this method checks if the card that has been
	 * clicked it is selectable at the moment.</br>
	 * 
	 * In case one of these two conditions are not met, it prints out an error
	 * message.
	 * 
	 * @param i : int - index of the card that the player want to pick up
	 */
	public void pickUpCard(int i) {
		if (turn) {
			if (pickUpIsAllowed()) {
				model.pickUpCard(i);
				reload();
			} else {
				System.out.println(UtilityClass.TWO_CARD_SELECTED);
			}
		} else {
			System.out.println(UtilityClass.WRONG_TURN);
		}
	}

	/**
	 * If the player has already selected is not able to pick up another one.</br>
	 * 
	 * This method checks if this condition is respected.
	 * 
	 * @return isAbleToPickUp : boolean - is the player able to pick up a card at
	 *         the moment
	 */
	public boolean pickUpIsAllowed() {
		return model.getSelectedCard() == null ? true : false;
	}

	/**
	 * It reload the new informations that have been generated by the model in the
	 * view.
	 */
	public static void reload() {
		view.reload(model.generateInfo());
	}

	/**
	 * Finds out which protocol if being used by the controller and send it a
	 * message with the informations of a single move executed. </br>
	 * The turn is ended after a message is sent.
	 * 
	 * @param message : String - informations about a move
	 */
	public void send(String message) {
		if (sProtocol == null) {
			cProtocol.send(message);
		} else {
			sProtocol.send(message);
		}
		passTurn(false);
	}

	/**
	 * This method make the state of the game advance a turn.
	 * 
	 * @param nextTurn : boolean - it represents which player will the next turn be
	 */
	protected void passTurn(boolean nextTurn) {
		view.turnPassed(turn = nextTurn);
	}

	/**
	 * Checks if the game is ended and in that case make the program progress to the
	 * next portion of the game: the winner announcement and the play again
	 * request.</br>
	 * 
	 * As it checks if the game is over it also determines the winner.
	 */
	public void isGameOver() {
		/*
		 * Checks if there are any available moves to the player with the cards that are
		 * given to him at the moment
		 */
		boolean availableMoves = availableMoves();

		/*
		 * Check if all the gems are been claimed
		 */
		boolean areGemsAvailable = areGemsAvailable();

		if (!availableMoves || !areGemsAvailable) {
			endGame();
		}
	}

	/**
	 * Checks if there are still available moves to the player with the cards at his
	 * disposal.
	 * 
	 * @return availableMoves : boolean - there are available moves
	 */
	private boolean availableMoves() {
		for (Map.Entry<Integer, EnumCards> card : model.getP1Cards().entrySet()) {
			for (int i = 1; i < 4; i++) {
				for (int j = 1; j < 4; j++) {
					for (EnumTriangle direction : EnumTriangle.values()) {
						if (!model.getBoardCards().containsKey(model.getKey(i, j))) {
							if (!isThereAGem(i, j)) {
								return true;
							}
						} else if (pushIsAllowed(i, j, direction, card.getValue())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if there are any gems that have not been taken yet.
	 * 
	 * @return areGemsAvailable : boolean - there are gems not taken yet
	 */
	private boolean areGemsAvailable() {
		for (Integer[] gem : model.getGems()) {
			if (model.getBoardCards().get(model.getKey(gem[0], gem[1])) == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The game is over. This method declares the winner and move to the next
	 * window.
	 */
	private void endGame() {
		String result = "";
		switch (declareWinner()) {
		case 1:
			result = UtilityClass.WIN_MESSAGE;
			break;
		case 0:
			result = UtilityClass.DRAW_MESSAGE;
			break;
		case 2:
			result = UtilityClass.LOSS_MESSAGE;
			break;
		}
		passTurn(false);
		EntryPoint.endScreen.frame.setVisible(true);
		EndV.setResult(result);
	}

	/**
	 * Checks if in a certain position on the board there is a gem
	 * 
	 * @param x : int - first coordinate
	 * @param y : int - second coordinate
	 * @return isThereAGem : boolean
	 */
	private static boolean isThereAGem(int x, int y) {
		for (Integer[] gem : model.getGems()) {
			if (gem[0] == x && gem[1] == y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Based on the number of gems claimed by each player at the moment this method
	 * is called it determines which one is the winner.
	 * 
	 * @return winner : int - the index of the winning player
	 */
	private static int declareWinner() {
		int p1Points = 0;
		int p2Points = 0;
		for (Integer[] gem : model.getGems()) {
			if (model.getBoardCards().get(model.getKey(gem[0], gem[1])) != null) {
				if (model.getBoardCards().get(model.getKey(gem[0], gem[1])).getPlayer() == 1) {
					p1Points++;
				} else {
					p2Points++;
				}
			}
		}
		if (p1Points > p2Points) {
			return 1;
		} else if (p1Points == p2Points) {
			return 0;
		} else {
			return 2;
		}

	}

	/**
	 * Retrieve the informations from the model needed to initialize the match on a
	 * different client
	 * 
	 * @return initialization informations : ArrayList<String> - All the informations
	 *         needed at the creation of the other player's match
	 */
	public ArrayList<String> getInitializationInfos() {
		ArrayList<String> data = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			data.add(String.format(UtilityClass.DRAW_COMMAND, i, model.getP1Cards().get(i).toString(), 2));
			data.add(String.format(UtilityClass.DRAW_COMMAND, i, model.getP2Cards().get(i).toString(), 1));
		}
		for (int i = 0; i < 3; i++) {
			data.add(String.format(UtilityClass.GEM_COMMAND, i, model.getGems().get(i)[0], model.getGems().get(i)[1]));
		}
		return data;
	}

	/**
	 * After receiving the coordinates of a gem it communicates the position to the
	 * model.
	 * 
	 * @param nGem : int - Which gem coordinates are being passed
	 * @param x    : int - First coordinate of the gem on the board
	 * @param y    : int - Second coordinate of the gem on the board
	 */
	public void setGem(int nGem, int x, int y) {
		model.setGem(nGem, x, y);
		reload();
	}

}
