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
import gemgemgem.model.MatchM;
import gemgemgem.model.ModelInfo;
import gemgemgem.net.Protocol;
import gemgemgem.view.MatchV;

public class MatchC {

	private static MatchM model;
	private static MatchV view;

	public MatchC() {
		model = new MatchM();
		view = new MatchV(model.generateInfo(), this);
	}

	public void executeMove(int i, int j, EnumTriangle direction, EnumCards card) {
		if (model.pushIsAllowed(i, j, direction) || card != null) {
			model.moveCards(i, j, direction, card);
			reload();
			isGameOver();
		} else {
			System.out.println("[System] : THIS CARD ISN'T STRONG ENOUGH");
		}
	}

	public void placeCard(int i, int j, EnumCards card) {
		if (model.placeIsAllowed(i, j) || card != null) {
			model.placeCard(i, j, card);
			reload();
			isGameOver();
		} else {
			System.out.println("[System] : YOU CAN'T PLACE A CARD HERE");
		}
	}

	public void placeCard(int x, EnumCards card, int player) {
		model.placeCard(x, card, player);
		reload();
	}

	public void pickUpCard(int i) {
		if (model.pickUpIsAllowed()) {
			model.pickUpCard(i);
			reload();
			isGameOver();
		} else {
			System.out.println("[System] : YOU CAN'T HAVE 2 CARDS SELECTED");
		}
	}

	private static void isGameOver() {
		int winner = model.isGameOver();
		if (winner != -1) {
			System.out.printf("[System] : Player %d won. Nice", winner);
		}
	}

	public static void reload() {
		view.reload(model.generateInfo());
	}

	public ArrayList<String> getData() {
		return model.getData();
	}

	public MatchM getModel() {
		return model;
	}

	public void setModel(MatchM model) {
		this.model = model;
	}

	private void createClient() {
		try {
			Socket socket = new Socket("localhost", 1234);
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(
		        new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
