package gemgemgem.net;

import java.util.ArrayList;

public class Event {

	private Protocol sender;
	private String command;
	private ArrayList<String> parameters;

	private Event(Protocol sender, String command, ArrayList<String> parameters) {
		this.sender = sender;
		this.command = command;
		this.parameters = parameters;
	}

	public static Event parseEvent(Protocol sender, String request) {
		String command = null;
		ArrayList<String> parameters = new ArrayList<String>();
		String[] tokens = request.split(" ");
		command = tokens[0];
		for (int i = 1; i < tokens.length; i++) {
			parameters.add(tokens[i]);
		}

		return new Event(sender, command, parameters);
	}

	public Protocol getSender() {
		return sender;
	}

	public String getCommand() {
		return command;
	}

	public ArrayList<String> getParameters() {
		return parameters;
	}
	
	

}
