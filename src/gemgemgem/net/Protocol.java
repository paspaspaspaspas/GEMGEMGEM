package gemgemgem.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import gemgemgem.EntryPoint;
import gemgemgem.EnumCards;
import gemgemgem.EnumTriangle;
import gemgemgem.controller.MatchC;

public class Protocol {

	protected MatchC match;
	protected HashMap<String, Consumer<Event>> commandMap;
	
	public Protocol(MatchC match) {
		this.match = match;
		commandMap = new HashMap<>();
		commandMap.put("PLACE", e -> e.getSender().placeCard(e.getParameters()));
		commandMap.put("PUSH", e -> e.getSender().pushCard(e.getParameters()));
		commandMap.put("DRAW", e -> e.getSender().drawCard(e.getParameters()));
	}

	/*
	 * PLACE X Y CARD
	 */
	protected void placeCard(ArrayList<String> parameters) {
		match.placeCard(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)),EnumCards.valueOf(parameters.get(2)));
	}

	/*
	 * PUSH X Y TRIANGLE CARD
	 */
	protected void pushCard(ArrayList<String> parameters) {
		match.executeMove(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)),EnumTriangle.valueOf(parameters.get(2)), EnumCards.valueOf(parameters.get(3)));
	}

	/*
	 * DRAW X CARD PLAYER
	 */
	protected void drawCard(ArrayList<String> parameters) {
		match.placeCard(Integer.parseInt(parameters.get(0)), EnumCards.valueOf(parameters.get(1)),Integer.parseInt(parameters.get(2)));
	}

	public void processRequest(String message) {
			Event e = Event.parseEvent(this, message);
			Consumer<Event> commandExe = commandMap.get(e.getCommand().toUpperCase());
			commandExe.accept(e);
	}

}
