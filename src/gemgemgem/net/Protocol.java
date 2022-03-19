package gemgemgem.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import gemgemgem.EnumCards;
import gemgemgem.EnumTriangle;
import gemgemgem.controller.MatchC;

/**
 * This class represents the general behavior that both the server and the
 * client will adopt.</br></br>
 * 
 * The behavior is summarized into a restricted number commands that can be
 * executed saved in an HashMap. This class describes also the methods that
 * correspond to each request.
 * 
 * @author pas
 *
 */
public class Protocol {

	// ATTRIBUTES
	protected MatchC match;
	protected HashMap<String, Consumer<Event>> commandMap;

	// CONSTRUCTOR
	public Protocol(MatchC match) {
		this.match = match;
		commandMap = new HashMap<>();
		commandMap.put("PLACE", e -> e.getSender().placeCard(e.getParameters()));
		commandMap.put("PUSH", e -> e.getSender().pushCard(e.getParameters()));
		commandMap.put("DRAW", e -> e.getSender().drawCard(e.getParameters()));
	}

	// METHODS
	/**
	 * When the "PLACE" command is received, this method set the card described by
	 * the parameters in her place through the help of the match's controller.</br>
	 * </br>
	 * 
	 * The parameters are: X coordinate, Y coordinate, card</br>
	 * 
	 * @param parameters : ArrayList<String> - It contains all the informations
	 *                   about the card that has to be set
	 */
	protected void placeCard(ArrayList<String> parameters) {
		match.placeCard(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)),
				EnumCards.valueOf(parameters.get(2)));
	}

	/**
	 * When the "PUSH" command is received, this method set the card described by
	 * the parameters in her place and slide all the necessary already placed cards
	 * through the help of the match's controller.</br>
	 * </br>
	 * 
	 * The parameters are: X coordinate, Y coordinate, direction of the sliding,
	 * card</br>
	 * 
	 * @param parameters : ArrayList<String> - It contains all the informations
	 *                   about the card that has to be set
	 */
	protected void pushCard(ArrayList<String> parameters) {
		match.executeMove(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)),
				EnumTriangle.valueOf(parameters.get(2)), EnumCards.valueOf(parameters.get(3)));
	}

	/**
	 * When the "DRAW" command is received, this method set the card described by
	 * the parameters in her place through the help of the match's controller.</br>
	 * </br>
	 * 
	 * The parameters are: X coordinate, card, player's index</br>
	 * 
	 * @param parameters : ArrayList<String> - It contains all the informations
	 *                   about the card that has to be set
	 */
	protected void drawCard(ArrayList<String> parameters) {
		match.placeCard(Integer.parseInt(parameters.get(0)), EnumCards.valueOf(parameters.get(1)),
				Integer.parseInt(parameters.get(2)));
	}

	/**
	 * It process a received request.</br></br>
	 * 
	 * Given a string, it generates the corresponding Event object; then gives it to
	 * a Consumer that executes the task described by it.
	 * 
	 * @param message : String - the request not parsed
	 */
	public void processRequest(String message) {
		Event e = Event.parseEvent(this, message);
		Consumer<Event> commandExe = commandMap.get(e.getCommand().toUpperCase());
		commandExe.accept(e);
	}

}
