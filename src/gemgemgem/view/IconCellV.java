package gemgemgem.view;

import java.awt.Color;

import gemgemgem.EnumImagesUtility;

/**
 * It represents the view of a generic cell that constitutes the icon of
 * either of the players.
 * 
 * @author pas
 *
 */
public class IconCellV extends CellV {

	// CONSTRUCTOR
	public IconCellV(int player) {
		super(Color.WHITE, Color.LIGHT_GRAY, false,
				player == 1 ? EnumImagesUtility.ICON1.getImage() : EnumImagesUtility.ICON2.getImage());
	}

}
