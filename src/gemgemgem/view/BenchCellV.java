package gemgemgem.view;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * It represents the view of a generic cell that constitutes the bench of
 * either of the players.
 * 
 * @author pas
 *
 */
public class BenchCellV extends CellV {

	// COSTRUCTOR
	public BenchCellV(BufferedImage image, int player) {
		super(Color.YELLOW, Color.ORANGE, player == 1 ? true : false, image);
	}
}
