package gemgemgem.view;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Rappresenta la view di una carta generica posizionata sulla panchina di un giocatore.
 * 
 * @author pasos
 *
 */
public class BenchCardV extends CardV{
	
	//COSTRUTTORE
	public BenchCardV(BufferedImage image) {
		super(Color.YELLOW, Color.ORANGE, true, image);
	}

}
