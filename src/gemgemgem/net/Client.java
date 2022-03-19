package gemgemgem.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import gemgemgem.UtilityClass;
import gemgemgem.controller.MatchC;

/**
 * It describes the behavior of a client in a server-client architecture.</br></br>
 * 
 * This class allows the two player to communicate, one acting as the server
 * and one, initialized by this class, as the client.</br>
 * 
 * It implements the Runnable interface in order to be able to assign it to a
 * specific Thread.
 * 
 * @author pas
 *
 */
public class Client implements Runnable {

	// ATTIBUTES
	MatchC match;
	String ip;
	int port;

	PrintWriter out;
	BufferedReader in;
	boolean isRunning = true;

	// CONSTRUCTOR
	public Client(String ip, int port) {
		this.match = new MatchC();
		this.ip = ip;
		this.port = port;
	}

	// GETTERS AND SETTERS
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	// METHODS
	/**
	 * It is the implementation of the run() method of the Runnable interface.</br>
	 * 
	 * It links the client, through a socket, to a socket server, that will be
	 * linked to the other player.</br>
	 * 
	 * Utilizing a PrintWriter and a BufferedReader it is able to communicate with
	 * the server.
	 * 
	 */
	public void run() {
		try (Socket socket = new Socket(ip, port);) {

			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String inputLine;
			ClientProtocol p = new ClientProtocol(match, this);

			/*
			 * The just connected client requires from the server to get synchronized with
			 * all the necessary informations.
			 */
			out.println("SYNCH");

			/*
			 * As long as this client is still linked to the server and it is receiving
			 * informations it keeps processing them.
			 */
			while (isRunning && (inputLine = in.readLine()) != null) {
				p.processRequest(inputLine);
			}
		} catch (IOException e1) {
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
	 * It send a message to the server the Client instance is linked to.
	 * 
	 * @param message : String - the message that is needed to be sent
	 */
	public void sendMessage(String message) {
		out.println(message);
	}

}
