package gemgemgem.view;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gemgemgem.EnumImagesUtility;

/**
 * Rappresenta la view di una carta generica ancora posizionata nel mazzo.
 * 
 * @author pasos
 *
 */
public class DeckCardV extends CardV {

	// COSTRUTTORE
	public DeckCardV(int player) {
		super(Color.BLACK, Color.LIGHT_GRAY, false,
				player == 1 ? EnumImagesUtility.BLUE_BACK.getImage() : EnumImagesUtility.RED_BACK.getImage());
	}
}
