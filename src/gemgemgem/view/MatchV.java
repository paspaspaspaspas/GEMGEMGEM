package gemgemgem.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gemgemgem.EnumCards;
import gemgemgem.EnumImagesUtility;
import gemgemgem.controller.MatchC;
import gemgemgem.model.MatchM;
import gemgemgem.model.ModelInfo;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JButton;

/**
 * Racchiude tutti gli elementi che fanno parte della componente View del
 * progetto.
 * 
 * @author pasos
 *
 */
public class MatchV implements MouseMotionListener {

	// ATTRIBUTI
	public static JFrame frame;
	private JPanel panel;

	private static BoardV board;
	private static PlayerV player1;
	private static PlayerV player2;
	static SelectedCardV selectedCard;
	
	protected static ModelInfo info;
	protected static MatchC controller;

	/**
	 * Create the application.
	 */
	public MatchV(ModelInfo info, MatchC controller) {
		this.info = info;
		this.controller = controller;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		frame.setBounds(250, 30, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		selectedCard = new SelectedCardV(info.getSelectedCard());
		selectedCard.setBounds(1, 1, 1, 1);
		frame.getContentPane().add(selectedCard);

		panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(EnumImagesUtility.TABLE.getImage(), 0, 0, null);
            }
        };
		panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		frame.getContentPane().add(panel);

		player1 = new PlayerV(1);
		player1.setBounds(0, 0, (int) (frame.getWidth() * 0.2), frame.getHeight());
		panel.add(player1);
		
		board = new BoardV();
		board.setBounds((int) (frame.getWidth() * 0.2), 0, (int) (frame.getWidth() * 0.6), frame.getHeight());
		panel.add(board);
		
		player2 = new PlayerV(2);
		player2.setBounds((int) (frame.getWidth() * 0.8), 0, (int) (frame.getWidth() * 0.2), frame.getHeight());
		panel.add(player2);

		frame.getContentPane().addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

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

	public void repaint() {
		frame.repaint();
	}

	public void reload(ModelInfo info) {
		player1.reload(info.getImagesP1());
		player2.reload(info.getImagesP2());
		board.reload(info.getImagesBoard());
		selectedCard.reload(info.getSelectedCard());
	}

}
