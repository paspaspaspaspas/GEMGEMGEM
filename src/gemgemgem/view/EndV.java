package gemgemgem.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class EndV {

	public JFrame frame;
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		resultLbl = new JLabel("");
		resultLbl.setBackground(new Color(255, 255, 0));
		resultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(resultLbl, BorderLayout.CENTER);
		
		JButton rematchBtn = new JButton("PLAY AGAIN");
		rematchBtn.setBackground(new Color(173, 255, 47));
		rematchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ServerV.frame.setVisible(true);
				if(ServerV.getServer() != null) {
					try {
						ServerV.getServer().getSocketServer().close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		panel.add(rematchBtn, BorderLayout.SOUTH);
	}
	
	public static void setResult(String result) {
		resultLbl.setText(result);
	}

}
