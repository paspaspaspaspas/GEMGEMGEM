package gemgemgem.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import gemgemgem.EntryPoint;
import gemgemgem.controller.MatchC;
import gemgemgem.net.Client;
import gemgemgem.net.Server;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ServerV {

	static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerV window = new ServerV();
					window.frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerV() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnClient = new JButton("JOIN a lobby");
		btnClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
				Client c = new Client();
				Thread cThread = new Thread();
				cThread.run();
			}
		});
		frame.getContentPane().add(btnClient, BorderLayout.WEST);

		JButton btnServer = new JButton("CREATE a lobby");
		btnServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Server s = new Server();
				Thread sThread = new Thread();
				sThread.run();
			}

		});
		frame.getContentPane().add(btnServer, BorderLayout.EAST);
	}

	
}
