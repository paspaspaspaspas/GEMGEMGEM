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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * Racchiude tutti gli elementi che fanno parte della componente View del
 * progetto.
 * 
 * @author pasos
 *
 */
public class MatchV implements MouseMotionListener {

	// ATTRIBUTI
	static JFrame frame;
	private JPanel panel;

	private BoardV board;
	private PlayerV player1;
	private PlayerV player2;
	static SelectedCardV selectedCard;

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
		selectedCard.setBounds(1, 1, 1, 1);
		frame.getContentPane().add(selectedCard);

		panel = new JPanel();
		panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		frame.getContentPane().add(panel);

		player1 = new PlayerV(0);
		player1.setBounds(0, 0, (int) (frame.getWidth() * 0.2), frame.getHeight());
		panel.add(player1);
		
		board = new BoardV();
		board.setBounds((int) (frame.getWidth() * 0.2), 0, (int) (frame.getWidth() * 0.6), frame.getHeight());
		panel.add(board);
		
		player2 = new PlayerV(1);
		player2.setBounds((int) (frame.getWidth() * 0.8), 0, (int) (frame.getWidth() * 0.2), frame.getHeight());
		panel.add(player2);

		frame.getContentPane().addMouseMotionListener(this);
		// frame.getContentPane().addMouseListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	/**
	 * Mi permette di tenere traccia della posizione del mouse rispetto alla
	 * finestra. In questo modo muovo la carta selezionata o il cursore qualora
	 * nessuna carta sia stata scelta in precedenza.
	 */
	public void mouseMoved(MouseEvent e) {
		int l = Math.min(frame.getWidth(), frame.getHeight());
		if (selectedCard.isSelected()) {
			selectedCard.setBounds((int) (e.getPoint().getX() - l / 30), (int) (e.getPoint().getY() - l / 30), l / 15,
					l / 15);
			selectedCard.setSidesColor(Color.MAGENTA);
		} else {
			selectedCard.setBounds((int) (e.getPoint().getX() - l / 100), (int) (e.getPoint().getY() - l / 100), l / 50,
					l / 50);
		}
		selectedCard.repaint();
	}

}
