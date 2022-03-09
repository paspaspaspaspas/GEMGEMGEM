package gemgemgem.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import gemgemgem.EnumImagesUtility;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;

public class MenuV {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuV window = new MenuV();
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
	public MenuV() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 150, 720, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(EnumImagesUtility.BACKGROUND.getImage(), 0, 0, null);
            }
        };
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel title = new JLabel("GEMGEMGEM");
		title.setForeground(Color.BLACK);
		title.setFont(new Font("Curlz MT", Font.BOLD | Font.ITALIC, 66));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title, BorderLayout.CENTER);
		
		JButton playButton = new JButton("PLAY");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ServerV.frame.setVisible(true);
			}
		});
		playButton.setBackground(Color.GREEN);
		playButton.setFont(new Font("Curlz MT", Font.BOLD, 29));
		panel.add(playButton, BorderLayout.SOUTH);
		
		JButton tutorialButton = new JButton("Help");
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
		tutorialButton.setBackground(Color.GREEN);
		panel.add(tutorialButton, BorderLayout.NORTH);
	}

}
