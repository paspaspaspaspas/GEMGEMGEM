package gemgemgem.net;

import java.util.HashMap;
import java.util.function.Consumer;

import gemgemgem.controller.MatchC;

public class ServerProtocol extends Protocol {

	private static HashMap<String, Consumer<Event>> commandMap;
	static {
		commandMap = new HashMap<>();
		commandMap.put("PLACE", e -> e.getSender().placeCard(e.getParameters()));
		commandMap.put("PUSH", e -> e.getSender().pushCard(e.getParameters()));
		commandMap.put("DRAW", e -> e.getSender().drawCard(e.getParameters()));
		commandMap.put("SYNCH", e -> ((ServerProtocol) e.getSender()).synch());
		commandMap.put("CLOSE", e -> ((ServerProtocol) e.getSender()).close());
	}

	Server server;

	public ServerProtocol(MatchC match, Server server) {
		super(match);
		this.server = server;
	}

	public void sendMessage(String message) {
		server.sendMessage(message);
	}

	private void synch() {

	}

	private void close() {
		server.setRunning(false);
	}

}
