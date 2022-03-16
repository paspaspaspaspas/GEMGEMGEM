package gemgemgem.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import gemgemgem.controller.MatchC;

public class ServerProtocol extends Protocol {

	Server server;

	public ServerProtocol(MatchC match, Server server) {
		super(match);
		this.server = server;
		this.commandMap.put("SYNCH", e -> ((ServerProtocol) e.getSender()).synch());
		this.commandMap.put("CLOSE", e -> ((ServerProtocol) e.getSender()).close());
		match.setProtocol(this);
		match.setTurn(true);
	}

	private void synch() {
		ArrayList<String> inizializationInfos = match.getInitializationInfos();
		for(String s : inizializationInfos) {
			send(s);
		}
	}

	private void close() {
		server.setRunning(false);
	}
	
	public void send(String message) {
		server.sendMessage(message);
	}

}
