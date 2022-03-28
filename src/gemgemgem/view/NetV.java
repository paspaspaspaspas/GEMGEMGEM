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

/**
 * This class represents the window that lets the player create or join a lobby.
 * 
 * @author pas
 *
 */
public class NetV {

	//ATTRIBUTES
	static JFrame frame;
	static JPanel panel;
	private static Server s = null;
	private JTextField txtIpAdress;
	private JTextField txtPort;

	

	//CONSTRUCTOR
	/**
	 * Create the application.
	 */
	public NetV() {
		initialize();
	}
	
	//GETTERS AND SETTERS
	public static Server getServer() {
		return s;
	}

	//METHODS
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NetV window = new NetV();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
				String ip = parseIp(txtIpAdress.getText());
				int port = parsePort(txtPort.getText());
				Client c = new Client(ip, port);
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
				int port = parsePort(txtPort.getText());
				s = new Server(port);
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
		
		JLabel lblNewLabel = new JLabel("Joining a server? IP:");
		panel_1.add(lblNewLabel);
		
		txtIpAdress = new JTextField();
		txtIpAdress.setText("default");
		txtIpAdress.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(txtIpAdress);
		txtIpAdress.setColumns(10);
		
		JLabel portLbl = new JLabel("Set port: ");
		panel_1.add(portLbl);
		
		txtPort = new JTextField();
		txtPort.setText("default");
		txtPort.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(txtPort);
		txtPort.setColumns(10);
	}
	
	
	/**
	 * Given the desired IP address to connect to this method parses it.
	 * @param ipAddress : String - IP address to parse
	 * @return ipAddressParsed : String - IP address to connect to
	 */
	private String parseIp(String ipAddress) {
		if(ipAddress.equals("default")) {
			return "127.0.0.1";
		} else {
			return ipAddress;
		}
	}
	
	/**
	 * Given the desired port to open to connection or to connect to this method parses it.
	 * @param port : String - the port as a String
	 * @return portParsed : int - port number to connect to
	 */
	private int parsePort(String port) {
		if(port.equals("default")) {
			return 1234;
		}else {
			return Integer.parseInt(port);
		}
	}

	
}
