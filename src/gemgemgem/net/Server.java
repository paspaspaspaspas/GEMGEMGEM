package gemgemgem.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {

        int port = 1234;

        System.out.println("");
        
        try (
                ServerSocket server = new ServerSocket(port);
               
            ) {
        	while(true) {
        		Socket client = server.accept();
        		Protocol p = new Protocol(client);
        		Thread clientThread = new Thread(p);
        		clientThread.start();
        	}
            
        } catch (IOException ex) {
            System.err.println("Errore di comunicazione: " + ex);
        }
        
        System.out.println("Exit...");
        
    }
}
