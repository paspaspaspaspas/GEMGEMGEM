package gemgemgem.view;

import java.awt.Color;

import gemgemgem.EnumCards;

/**
 * Rappresenta la view di una carta generica che è stata spostata al cimitero.
 * 
 * @author pasos
 *
 */
public class GraveyardCardV extends CardV{
	
	//COSTRUTTORE
	public GraveyardCardV() {
		super(Color.GRAY, Color.DARK_GRAY, false, (EnumCards) null);
	}
}
