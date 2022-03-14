package gemgemgem.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gemgemgem.EnumCards;
import gemgemgem.EnumTriangle;
import gemgemgem.UtilityClass;
import gemgemgem.model.MatchM;
import gemgemgem.model.ModelInfo;
import gemgemgem.net.ClientProtocol;
import gemgemgem.net.Protocol;
import gemgemgem.net.ServerProtocol;
import gemgemgem.view.MatchV;

public class MatchC {

	private static MatchM model;
	private static MatchV view;
	private boolean turn;
	private ServerProtocol sProtocol = null;
	private ClientProtocol cProtocol = null;

	public MatchC() {
		model = new MatchM(this);
		view = new MatchV(model.generateInfo(), this);
	}

	public void executeMove(int i, int j, EnumTriangle direction, EnumCards card) {
		turn = true;
		EnumCards selectedCard = model.getSelectedCard() != null ? model.getSelectedCard() : null;
		if (model.pushIsAllowed(i, j, direction, selectedCard) || card != null) {
			if (card == null)
				send(String.format(UtilityClass.PUSH, i, j, direction.toString(), model.getSelectedCard().toString()));
			model.moveCards(i, j, direction, card);
			reload();
			isGameOver();
		} else {
			System.out.println("[System] : THIS CARD ISN'T STRONG ENOUGH");
		}

	}

	public void placeCard(int i, int j, EnumCards card) {
		turn = true;
		if (model.placeIsAllowed(i, j) || card != null) {
			/*
			 * I send this piece of information only if i didn't receive it from the other
			 * player and i actually placed a card on the board and not back to my own bench
			 */
			if (card == null && j != -1)
				send(String.format(UtilityClass.PLACE, i, j, model.getSelectedCard().toString()));
			model.placeCard(i, j, card);
			reload();
			isGameOver();
		} else {
			System.out.println("[System] : YOU CAN'T PLACE A CARD HERE");
		}

	}

	public void placeCard(int x, EnumCards card, int player) {
		turn = true;
		model.placeCard(x, card, player);
		reload();
	}

	public void pickUpCard(int i) {
		if (turn) {
			if (model.pickUpIsAllowed()) {
				model.pickUpCard(i);
				reload();
			} else {
				System.out.println("[System] : YOU CAN'T HAVE 2 CARDS SELECTED");
			}
		} else {
			System.out.println("[System] : IT IS NOT YOUR TURN");
		}
	}

	public void send(String message) {
		if (sProtocol == null) {
			cProtocol.send(message);
		} else {
			sProtocol.send(message);
		}
		turn = false;
	}

	private static void isGameOver() {
		int winner = model.isGameOver();
		switch (winner) {
		case 1:
			System.out.printf("[System] : YOU WON! CONGRATULATIONS!", winner);
			break;
		case 0:
			System.out.println("[System] : THIS A DRAW. COULD HAVE GONE WORSE");
			break;
		case 2:
			System.out.println("[System] : YOU LOST... BETTER LUCK NEXT TIME");
			break;
		default:
			break;
		}
	}

	public static void reload() {
		view.reload(model.generateInfo());
	}

	public MatchM getModel() {
		return model;
	}

	public void setModel(MatchM model) {
		this.model = model;
	}

	public void setProtocol(ServerProtocol protocol) {
		this.sProtocol = protocol;
	}

	public void setProtocol(ClientProtocol protocol) {
		this.cProtocol = protocol;
	}

	public boolean getTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public ArrayList<String> getInizializationInfos() {
		return model.getInizializationInfos();
	}

	public void setGem(int nGem, int x, int y) {
		model.setGem(nGem, x, y);
		reload();
	}

}
