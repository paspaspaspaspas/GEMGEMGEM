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

	MatchC match = new MatchC();
	boolean isRunning = true;

	@Override

	public void run() {
		try (Socket socket = new Socket("localhost", 1234);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
			String inputLine, outputLine;
			ClientProtocol p = new ClientProtocol(match, this);
			
			while (isRunning && (inputLine = in.readLine()) != null) {
			        p.processRequest(inputLine);
			        out.println(outputLine);
			        if (outputLine.equals("CLOSE"))
			            break;
			    }
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
