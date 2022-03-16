package gemgemgem.view;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gemgemgem.EntryPoint;
import gemgemgem.EnumImagesUtility;
import gemgemgem.controller.MatchC;
import gemgemgem.net.Client;
import gemgemgem.net.Server;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class ServerV {

	static JFrame frame;
	static JPanel panel;
	private static Server s = null;
	private JTextField txtIpAdress;
	private JTextField txtPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerV window = new ServerV();
					window.frame.setVisible(true);
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
		frame.setBounds(550, 250, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
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
		btnClient.setForeground(new Color(248, 248, 255));
		btnClient.setBackground(new Color(139, 69, 19));
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
		btnServer.setForeground(new Color(248, 248, 255));
		btnServer.setBackground(new Color(139, 69, 19));
		btnServer.setFont(new Font("Curlz MT", Font.PLAIN, 20));
		btnServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				s = new Server();
				Thread sThread = new Thread(s);
				sThread.start();
			}

		});
		panel.add(btnServer, BorderLayout.EAST);
		
		JButton tutorialButton = new JButton("Help");
		tutorialButton.setForeground(new Color(248, 248, 255));
		tutorialButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				    try {
						Desktop.getDesktop().browse(new URI("https://www.yachtclubgames.com/blog/joustus-instruction-manual"));
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		tutorialButton.setHorizontalAlignment(SwingConstants.RIGHT);
		tutorialButton.setBackground(new Color(128, 0, 0));
		panel.add(tutorialButton, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Joining a server? IP adress:");
		panel_1.add(lblNewLabel);
		
		txtIpAdress = new JTextField();
		txtIpAdress.setText("127.0.0.1");
		txtIpAdress.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(txtIpAdress);
		txtIpAdress.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Port: ");
		panel_1.add(lblNewLabel_1);
		
		txtPort = new JTextField();
		txtPort.setText("1234");
		txtPort.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(txtPort);
		txtPort.setColumns(10);
	}
	
	public static Server getServer() {
		return s;
	}

	
}
