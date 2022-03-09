package gemgemgem.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import gemgemgem.controller.MatchC;

public class ClientProtocol extends Protocol{

	private static HashMap<String, Consumer<Event>> commandMap;
	static {
		commandMap = new HashMap<>();
		commandMap.put("PLACE", e -> e.getSender().placeCard(e.getParameters()));
		commandMap.put("PUSH", e -> e.getSender().pushCard(e.getParameters()));
		commandMap.put("DRAW", e -> e.getSender().drawCard(e.getParameters()));
		commandMap.put("INIZIALIZE", e -> ((ClientProtocol) e.getSender()).inizialize(e.getParameters()));
		commandMap.put("CLOSE", e -> ((ClientProtocol) e.getSender()).close());
	}
	
	Client client;
	
	public ClientProtocol(MatchC match, Client client) {
		super(match);
		this.client = client;
	}
	
	private void inizialize(ArrayList<String> parameters) {
		
		
	}

	public void close() {
		client.setRunning(false);
	}
	
}
