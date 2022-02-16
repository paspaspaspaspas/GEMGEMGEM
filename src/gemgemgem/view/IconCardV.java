package gemgemgem.view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Rappresenta la view di una carta che indica l'icona di un determinato giocatore
 * 
 * @author pasos
 *
 */
public class IconCardV extends CardV{
	
	//COSTRUTTORE
	public IconCardV(BufferedImage icon) {
		super(Color.WHITE, Color.LIGHT_GRAY, false, icon);
	}

}
