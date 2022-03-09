package gemgemgem.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import gemgemgem.controller.MatchC;

public class Server implements Runnable {
	
	MatchC match = new MatchC();
	boolean isRunning = true;
	PrintWriter out;
	BufferedReader in;
	
	@Override
	public void run() {
		
		System.out.println("[System] : THE SERVER IS STARTING");
		try (	
				ServerSocket s = new ServerSocket(1234);
				Socket clientSocket = s.accept();
				) {
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine = "SYNCH";
			ServerProtocol p = new ServerProtocol(match, this);
			p.processRequest(inputLine);
			while (isRunning && (inputLine = in.readLine()) != null) {
			        p.processRequest(inputLine);
			    }
		} catch (IOException e) {
			System.err.println("[System] : COMMUNICATION ERROR");
		}
	}
	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	public void sendMessage(String message) {
		out.println(message);
		
	}
	
}