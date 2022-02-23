package gemgemgem.view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import gemgemgem.EnumImagesUtility;

/**
 * Rappresenta la view di una carta che indica l'icona di un determinato
 * giocatore
 * 
 * @author pasos
 *
 */
public class IconCardV extends CardV {

	// COSTRUTTORE
	public IconCardV(int player) {
		super(Color.WHITE, Color.LIGHT_GRAY, false,
				player == 1 ? EnumImagesUtility.ICON1.getImage() : EnumImagesUtility.ICON2.getImage());
	}

}
