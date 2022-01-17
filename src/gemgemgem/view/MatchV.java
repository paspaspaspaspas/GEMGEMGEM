package gemgemgem.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class MatchV implements MouseMotionListener{

	private JFrame frame;
	private JPanel panel;
	
	private BoardV board;
	private PlayerV player1;
	private PlayerV player2;
	private SelectedCardV selectedCard;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MatchV window = new MatchV();
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
	public MatchV() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(50, 50, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		selectedCard = new SelectedCardV();
		selectedCard.setBounds(100, 100, 100, 100);
		frame.getContentPane().add(selectedCard);
		
		panel = new JPanel();
		panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		frame.getContentPane().add(panel);

		player1 = new PlayerV();
		player1.setBounds(0, 0, (int)(frame.getWidth() * 0.2), frame.getHeight());
		panel.add(player1);
		
		board = new BoardV();
		board.setBounds((int)(frame.getWidth() * 0.2), 0, (int)(frame.getWidth() * 0.6), frame.getHeight());
		panel.add(board);
		
        player2 = new PlayerV();
        player2.setBounds((int)(frame.getWidth() * 0.8), 0, (int)(frame.getWidth() * 0.2), frame.getHeight());
        panel.add(player2);
        
        frame.getContentPane().addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		selectedCard.setBounds((int)(e.getPoint().getX()-50), (int)(e.getPoint().getY()-50), 100, 100);
		selectedCard.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}

}
