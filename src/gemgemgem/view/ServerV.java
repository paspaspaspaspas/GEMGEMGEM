package gemgemgem.view;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gemgemgem.EntryPoint;
import gemgemgem.EnumImagesUtility;
import gemgemgem.controller.MatchC;
import gemgemgem.net.Client;
import gemgemgem.net.Server;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ServerV {

	static JFrame frame;
	static JPanel panel;

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
		
		panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(EnumImagesUtility.TABLE.getImage(), 0, 0, null);
            }
        };
		panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		panel.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panel);

		JButton btnClient = new JButton("JOIN a lobby");
		btnClient.setFont(new Font("Curlz MT", Font.PLAIN, 20));
		btnClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Client c = new Client();
				Thread cThread = new Thread(c);
				cThread.start();
			}
		});
		panel.add(btnClient, BorderLayout.WEST);

		JButton btnServer = new JButton("CREATE a lobby");
		btnServer.setFont(new Font("Curlz MT", Font.PLAIN, 20));
		btnServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Server s = new Server();
				Thread sThread = new Thread(s);
				sThread.start();
			}

		});
		panel.add(btnServer, BorderLayout.EAST);
	}

	
}
