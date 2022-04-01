package gemgemgem.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import gemgemgem.EnumImagesUtility;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * These class represents the view of the final screen that appears after a game
 * is completed. It announces the result of the last game and lets the player
 * decide to play again or quit the game.
 * 
 * @author pas
 *
 */
public class EndV {

	//ATTRIBUTES
	public static JFrame frame;
	private static JLabel resultLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EndV window = new EndV();
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
	public EndV() {
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
		};;
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		resultLbl = new JLabel("");
		resultLbl.setBounds(0, 162, 716, 124);
		resultLbl.setFont(new Font("Century Schoolbook", Font.PLAIN, 30));
		resultLbl.setBackground(new Color(255, 255, 0));
		resultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(resultLbl);

		JButton rematchBtn = new JButton("PLAY AGAIN");
		rematchBtn.setBounds(0, 413, 716, 59);
		rematchBtn.setFont(new Font("Brush Script MT", Font.PLAIN, 40));
		rematchBtn.setBackground(new Color(173, 255, 47));
		rematchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MatchV.frame.dispose();
				NetV.main(null);
				if (NetV.getServer() != null) {
					try {
						NetV.getServer().getSocketServer().close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		panel.add(rematchBtn);
	}

	/**
	 * After receiving the result of the match, it saves it on the label to be showed on the screen.
	 * 
	 * @param result : String - the result of the match.
	 */
	public static void setResult(String result) {
		resultLbl.setText(result);
	}

}
