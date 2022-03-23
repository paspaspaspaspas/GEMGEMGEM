package gemgemgem.view;

import java.awt.Color;

import gemgemgem.EnumImagesUtility;

/**
 * It represents the view of a generic cell that constitutes the deck back of
 * either of the players.
 * 
 * @author pas
 *
 */
public class DeckCellV extends CellV {

	// CONSTRUCTOR
	public DeckCellV(int player) {
		super(Color.BLACK, Color.LIGHT_GRAY, false,
				player == 1 ? EnumImagesUtility.BLUE_BACK.getImage() : EnumImagesUtility.RED_BACK.getImage());
	}
}
