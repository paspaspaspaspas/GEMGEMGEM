package gemgemgem.view;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gemgemgem.EnumImagesUtility;
import gemgemgem.controller.MatchC;
import gemgemgem.model.ModelInfo;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * This class is the View class of the MVC architecture.
 * 
 * This class contains all the components that form the view of the project. It
 * receives informations from the controller whenever the view needs to be
 * refreshed.
 * 
 * @author pas
 *
 */
public class MatchV implements MouseMotionListener {

	// ATTRIBUTES
	public static JFrame frame;
	private JPanel panel;

	private static BoardV board;
	private static PlayerV player1;
	private static PlayerV player2;
	public static SelectedCardV selectedCard;

	protected static ModelInfo info;
	protected static MatchC controller;

	// CONSTRUCTOR
	/**
	 * Create the application.
	 */
	public MatchV(ModelInfo info, MatchC controller) {
		this.info = info;
		this.controller = controller;
		initialize();
	}

	// METHODS
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

	/**
	 * Unused
	 */
	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * Whenever the player move the pointer on the screen this method is able to
	 * determine the position of the mouse and draw either the selected card or the
	 * pointer in the right position.
	 */
	public void mouseMoved(MouseEvent e) {
		int l = Math.min(frame.getWidth(), frame.getHeight());
		if (selectedCard.isSelected()) {
			selectedCard.setBounds((int) (e.getPoint().getX() - l / 30), (int) (e.getPoint().getY() - l / 30), l / 15,
					l / 15);
			selectedCard.setSidesColor(Color.MAGENTA);
		} else {
			selectedCard.setBounds((int) (e.getPoint().getX() - l / 80), (int) (e.getPoint().getY() - l / 80), l / 40,
					l / 40);
		}
		selectedCard.repaint();
	}

	/**
	 * It returns the view of the cells that form the board. In this case the board
	 * includes the graveyard too since we are referring to the view.
	 * 
	 * @return boardCells : CellsV[][] - the view of the cells
	 */
	public static CellV[][] getBoardCells() {
		return board.getCells();
	}

	/**
	 * It returns the view of the cells that form the player's bench. The icon and
	 * the deck back are also included since we are referring to the bench's view.
	 * 
	 * @return playerCells : CellsV[] - the view of the cells
	 */
	public static CellV[] getPlayerCells() {
		return player1.getCells();
	}

	/**
	 * It repaints the view
	 */
	public void repaint() {
		frame.repaint();
	}

	/**
	 * After receiving informations from the controller, this method implements them
	 * in the various components.
	 * 
	 * @param info : ModelInfo - informations passed by the controller
	 */
	public void reload(ModelInfo info) {
		player1.reload(info.getImagesP1());
		player2.reload(info.getImagesP2());
		board.reload(info.getImagesBoard());
		selectedCard.reload(info.getSelectedCard());
	}

	/**
	 * When the turn change this method refresh the pointer:</br>
	 * - GREEN = it's the player's turn</br>
	 * - RED = it's the adversary's turn
	 * 
	 * @param turn : boolean - it represents either it's the player's turn or not
	 */
	public void turnPassed(boolean turn) {
		if (turn)
			selectedCard.setMainColor(Color.GREEN);
		else
			selectedCard.setMainColor(Color.RED);
		selectedCard.repaint();
	}

	/**
	 * When the game is terminated, the pointer disappear to underline to the player
	 * that he is not able to interact with the board anymore.
	 */
	public void removePointer() {
		selectedCard.setVisible(false);
	}

}
