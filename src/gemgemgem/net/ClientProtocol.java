package gemgemgem.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import gemgemgem.controller.MatchC;

public class ClientProtocol extends Protocol{
	
	Client client;
	
	public ClientProtocol(MatchC match, Client client) {
		super(match);
		this.client = client;
		this.commandMap.put("GEM", e -> ((ClientProtocol) e.getSender()).setGem(e.getParameters()));
		this.commandMap.put("CLOSE", e -> ((ClientProtocol) e.getSender()).close());
		match.setProtocol(this);
		match.setTurn(false);
	}
	
	private void setGem(ArrayList<String> parameters) {	
		match.setGem(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
	}

	public void close() {
		client.setRunning(false);
	}
	
	public void send(String message) {
		client.sendMessage(message);
	}
	
}
