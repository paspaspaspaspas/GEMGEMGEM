package gemgemgem.view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import gemgemgem.EnumImagesUtility;
import gemgemgem.UtilityClass;

/**
 * It represents the view of a generic cell that constitutes the icon of either
 * of the players.
 * 
 * @author pas
 *
 */
public class IconCellV extends CellV {

	// CONSTRUCTOR
	public IconCellV() {
		super(Color.WHITE, Color.LIGHT_GRAY, false, setIcon());
	}
	
	//METHODS
	
	/**
	 * It returns a random icon between the available icons.
	 * @return icon : BufferedImage - the icon's image
	 */
	private static BufferedImage setIcon() {
		Random r = new Random();
		int index = r.nextInt(UtilityClass.NUM_ICONS);
		return EnumImagesUtility.values()[index].getImage();
	}

}
