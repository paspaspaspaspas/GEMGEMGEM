package gemgemgem.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gemgemgem.model.MatchM;
import gemgemgem.view.EnumTriangle;
import gemgemgem.view.MatchV;

public class MatchC {
	private static MatchM model;
	private static MatchV view;
	
	public MatchC() {
		model = new MatchM();
		view = new MatchV(model.generateInfo(), this);
	}
	
	public void executeMove(int i, int j, EnumTriangle direction) {
		if(model.moveIsAllowed(i, j, direction)) {
			model.moveCards(i, j, direction, model.getSelectedCard());
			view.reload(model.generateInfo());
		}
	}

	public void placeCard(int i, int j) {
		model.placeCard(i, j);
		view.reload(model.generateInfo());
	}

	public void pickUpCard(int i) {
		model.pickUpCard(i);
		view.reload(model.generateInfo());	
	}
	
	
}
