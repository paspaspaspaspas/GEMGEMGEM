package gemgemgem.view;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Rappresenta la view di una carta generica ancora posizionata nel mazzo.
 * 
 * @author pasos
 *
 */
public class DeckCardV extends CardV{
	
	//COSTRUTTORE
	public DeckCardV(BufferedImage back) {
		super(Color.BLACK, Color.LIGHT_GRAY, false, back);
	}
}
