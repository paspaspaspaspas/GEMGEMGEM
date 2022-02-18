package gemgemgem.model;

import java.util.ArrayList;

import gemgemgem.EnumCards;

public class MatchM {
	private ArrayList<EnumCards> p1Cards = new ArrayList<>();
	private ArrayList<EnumCards> boardCards = new ArrayList<>();
	private ArrayList<EnumCards> p2Cards = new ArrayList<>();

	public MatchM(ArrayList<EnumCards> p1Cards, ArrayList<EnumCards> boardCards, ArrayList<EnumCards> p2Cards) {
		this.p1Cards = p1Cards;
		this.boardCards = boardCards;
		this.p2Cards = p2Cards;
	}

	public ArrayList<EnumCards> getP1Cards() {
		return p1Cards;
	}

	public void setP1Cards(ArrayList<EnumCards> p1Cards) {
		this.p1Cards = p1Cards;
	}

	public ArrayList<EnumCards> getBoardCards() {
		return boardCards;
	}

	public void setBoardCards(ArrayList<EnumCards> boardCards) {
		this.boardCards = boardCards;
	}

	public ArrayList<EnumCards> getP2Cards() {
		return p2Cards;
	}

	public void setP2Cards(ArrayList<EnumCards> p2Cards) {
		this.p2Cards = p2Cards;
	}

}
