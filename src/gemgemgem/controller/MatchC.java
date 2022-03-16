package gemgemgem.controller;

import java.util.ArrayList;

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
 * This class acts as the controller of the MVC architecture of the program.
 * 
 * It links together the model and the view: it gets informations for the former
 * and listen and refresh the latter.
 * 
 * The communication with the model happens directly trough the invocation of
 * specific methods and the responses are given thanks to the return values.
 * With the view the link is more complex: the controller refresh the data of
 * the view with a specific method, but the information that come from the view
 * to the controller are vehiculated with event's listeners.
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
	 * the three elements are indipendent.
	 */
	public MatchC() {
		model = new MatchM(this);
		// The view is inizialize with the info of the model that i just created.
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
	 * allowed by the rules and, if so, it execute it.
	 * 
	 * In case that is not allowed it print a message on the console that signals to
	 * the player the impossibility of making the move happen.
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
	 * @param direction : EnumTriangle - rapresents the direction in which the card
	 *                  that wants to be placed is going to push
	 * @param card      : EnumCards - the card that want to be placed
	 */
	public void executeMove(int x, int y, EnumTriangle direction, EnumCards card) {
		turn = true;
		/*
		 * I need the user's selected card only if the player is the one making the
		 * move. If the move has already been checked from the other player the card I
		 * have selected doesn't mean anything in this resolution.
		 */
		EnumCards selectedCard = model.getSelectedCard() != null ? model.getSelectedCard() : null;
		if (model.pushIsAllowed(x, y, direction, selectedCard) || card != null) {
			/*
			 * Only if it has been the player to execute the move I'm going to send this
			 * information to the protocol.
			 */
			if (card == null)
				send(String.format(UtilityClass.PUSH_COMMAND, x, y, direction.toString(), model.getSelectedCard().toString()));
			model.moveCards(x, y, direction, card);
			reload();
			isGameOver();
		} else {
			System.out.println(UtilityClass.MOVE_NOT_ALLOWED);
		}

	}

	/**
	 * It places a card in given coordinates. This method is only used when the cell
	 * selected in empty. In case this wasn't true, it prints out an error.
	 * 
	 * @param x    : int - first coordinate of the cell where i want to place the
	 *             card
	 * @param y    : int - second coordinate of the cell where i want to place the
	 *             card
	 * @param card : EnumCards - card that need to be placed
	 */
	public void placeCard(int x, int y, EnumCards card) {
		turn = true;
		if (model.placeIsAllowed(x, y) || card != null) {
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
	 * the match.
	 * 
	 * It has to be implemented in a different method because it has to adjurn the
	 * player value of the EnumCard placed.
	 * 
	 * @param x      : int - coordinate of the card that need to be placed on the
	 *               bench
	 * @param card   : EnumCards - card that need to be placed
	 * @param player : int - player that drew that card
	 */
	public void placeCard(int x, EnumCards card, int player) {
		turn = true;
		model.placeCard(x, card, player);
		reload();
	}

	/**
	 * If it is the player's turn this method checks if the card that has been
	 * clicked it is selectable at the moment.
	 * 
	 * In case one of these two conditions are not met, it prints out an error
	 * message.
	 * 
	 * @param i : int - index of the card that the player want to pick up
	 */
	public void pickUpCard(int i) {
		if (turn) {
			if (model.pickUpIsAllowed()) {
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
	 * It reload the new informations that have been generated by the model in the
	 * view.
	 */
	public static void reload() {
		view.reload(model.generateInfo());
	}

	/**
	 * Finds out which protocol if being used by the controller and send it a
	 * message with the informations of a single move executed. The turn is ended
	 * after a message is sent.
	 * 
	 * @param message : String - informations about a move
	 */
	public void send(String message) {
		if (sProtocol == null) {
			cProtocol.send(message);
		} else {
			sProtocol.send(message);
		}
		turn = false;
	}

	/**
	 * Checks if the game is ended and in that case make the program progress to the
	 * next portion of the game: the winner announcement and the play again request.
	 * 
	 * As it checks if the game is over it also determines the winner.
	 */
	private static void isGameOver() {
		int winner = model.isGameOver();
		String result = "";
		switch (winner) {
		case 1:
			result = UtilityClass.WIN_MESSAGE;
			break;
		case 0:
			result = UtilityClass.DRAW_MESSAGE;
			break;
		case 2:
			result = UtilityClass.LOSS_MESSAGE;
			break;
		default:
			break;
		}
		if (winner != -1) {
			MatchV.frame.setVisible(false);
			EntryPoint.endScreen.frame.setVisible(true);
			EndV.setResult(result);
		}
	}

	/**
	 * Retrieve the informations from the model needed to initialize the match on a
	 * different client
	 * 
	 * @return initialization informations : ArrayList<String> - All the infomations
	 *         needed at the creation of the other player's match
	 */
	public ArrayList<String> getInitializationInfos() {
		return model.getInitializationInfos();
	}

	/**
	 * After receiving the coordinates of a gem it communicates the position to the
	 * model.
	 * 
	 * @param nGem : int - Which gem coordinates are being passed
	 * @param x : int - First coordinate of the gem on the board
	 * @param y : int - Second coordinate of the gem on the board
	 */
	public void setGem(int nGem, int x, int y) {
		model.setGem(nGem, x, y);
		reload();
	}

}
