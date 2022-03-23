package gemgemgem.view;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * It represents the view of a generic cell that constitutes the graveyard.
 * 
 * @author pas
 *
 */
public class GraveyardCellV extends CellV{
	
	//CONSTRUCTOR
	public GraveyardCellV(BufferedImage image) {
		super(Color.GRAY, Color.DARK_GRAY, false, image);
	}
}
