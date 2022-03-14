package gemgemgem.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Consumer;

import gemgemgem.controller.MatchC;

public class Client implements Runnable {

	MatchC match;
	PrintWriter out;
	BufferedReader in;
	boolean isRunning = true;

	public Client() {
		this.match = new MatchC();
	}

	public void run() {
		try (Socket socket = new Socket("localhost", 1234);) {
			
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String inputLine;
			ClientProtocol p = new ClientProtocol(match, this);
			
			/*
			 * The just connected client requires from the server to get
			 * synchronized with all the necessary informations.
			 */
			out.println("SYNCH");
			
			while (isRunning && (inputLine = in.readLine()) != null) {
				p.processRequest(inputLine);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void sendMessage(String message) {
		out.println(message);
	}

}
