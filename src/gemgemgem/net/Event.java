package gemgemgem.net;

import java.util.ArrayList;

/**
 * This class instantiates the objects that describe the commands that need to
 * be executed.</br>
 * 
 * It is used to translate the string that are passed in the communication
 * between a server and a client into commands are requests.
 * 
 * @author pas
 *
 */
public class Event {

	// ATTRIBUTES
	private Protocol sender;
	private String command;
	private ArrayList<String> parameters;

	// CONSTRUCTOR
	private Event(Protocol sender, String command, ArrayList<String> parameters) {
		this.sender = sender;
		this.command = command;
		this.parameters = parameters;
	}

	// GETTERS AND SETTERS
	public Protocol getSender() {
		return sender;
	}

	public String getCommand() {
		return command;
	}

	public ArrayList<String> getParameters() {
		return parameters;
	}

	// METHODS
	/**
	 * After receiving a request in form of a string, it parses it into an Event
	 * object that will be used by a Consumer to execute the aforementioned request.
	 * 
	 * @param sender  : Protocol - the protocol who is making the request
	 * @param request : String - a string containing the command and the parameters
	 *                that will be executed
	 * @return event : Event - an instance of this class that contains all the
	 *         informations needed to make the request happen.
	 */
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

}
