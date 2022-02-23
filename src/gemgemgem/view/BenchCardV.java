package gemgemgem.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gemgemgem.EnumCards;

/**
 * Rappresenta la view di una carta generica posizionata sulla panchina di un giocatore.
 * 
 * @author pasos
 *
 */
public class BenchCardV extends CardV implements MouseListener{
	
	//COSTRUTTORE
	public BenchCardV(EnumCards card) {
		super(Color.YELLOW, Color.ORANGE, card.getPlayer() == 1 ? true : false, card);
	}

	public void mouseClicked(MouseEvent e) {
		if (this.card != null && MatchV.selectedCard.getImage() != null) {
			return;
		} else if (this.card != null) {
			MatchV.selectedCard.selected(this.card.getImage(), this.card);
			this.card = null;
		} else if (MatchV.selectedCard.getImage() != null) {
			this.card = MatchV.selectedCard.getCard();
			MatchV.selectedCard.deselected();
		}
		this.repaint();
	}
}
