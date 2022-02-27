package gemgemgem.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import gemgemgem.EnumCards;

/**
 * Rappresenta la view di una carta generica posizionata sulla panchina di un giocatore.
 * 
 * @author pasos
 *
 */
public class BenchCardV extends CardV{
	
	//COSTRUTTORE
	public BenchCardV(BufferedImage image, int player) {
		super(Color.YELLOW, Color.ORANGE, player == 1 ? true : false, image);
	}
}
