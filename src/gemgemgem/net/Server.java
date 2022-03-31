package gemgemgem.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import gemgemgem.UtilityClass;
import gemgemgem.controller.MatchC;

/**
 * It describes the behavior of a server in a server-client architecture.</br>
 * </br>
 * 
 * This class allows the two player to communicate, one acting as the server,
 * initialized by this class, and the other one, as the client.</br>
 * 
 * It implements the Runnable interface in order to be able to assign it to a
 * specific Thread.
 * 
 * @author pasos
 *
 */
public class Server implements Runnable {

	// ATTRIBUTES
	private MatchC match;
	private int port;

	private ServerSocket socketS;
	private boolean isRunning = true;
	private boolean alreadyMatched = false;
	private PrintWriter out;
	private BufferedReader in;

	// CONSTRUCTORS
	public Server(int port) {
		/**
		 * The match corresponding to the player is initialize here in order to let the
		 * player see the view as he create the server. Otherwise the match wouldn't be
		 * visible until the "client player" joined.
		 */
		this.match = new MatchC();
		this.port = port;
	}

	// GETTERS AND SETTERS
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public ServerSocket getSocketServer() {
		return socketS;
	}

	// METHODS
	/**
	 * It is the implementation of the run() method of the Runnable interface.</br>
	 * 
	 * It create a ServerSocket which will accept a client that will act as the
	 * second player.</br>
	 * 
	 * Utilizing a PrintWriter and a BufferedReader it is able to communicate with
	 * the client.
	 */
	public void run() {
		System.out.println(UtilityClass.SERVER_STARTING);
		try (ServerSocket s = new ServerSocket(port); ) {
			/**
			 * Thank to this condition the server does not accept more than one client
			 */
			if(!alreadyMatched) {
				Socket clientSocket = s.accept();
				alreadyMatched = true;
				this.out = new PrintWriter(clientSocket.getOutputStream(), true);
				this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				socketS = s;

				String inputLine;
				ServerProtocol p = new ServerProtocol(match, this);

				while (isRunning && (inputLine = in.readLine()) != null) {
					p.processRequest(inputLine);
				}
			}
			
		} catch (IOException e) {
			System.err.println(UtilityClass.COMMUNICATION_ERROR);
		} finally {
			/*
			 * Closing the resources
			 */
			out.close();
			try {
				in.close();
			} catch (IOException e) {
				System.err.println(UtilityClass.COMMUNICATION_ERROR);
			}
		}
	}

	/**
	 * It send a message to the Client instance is linked to.
	 * 
	 * @param message : String - the message that is needed to be sent
	 */
	public void sendMessage(String message) {
		out.println(message);
	}

}
