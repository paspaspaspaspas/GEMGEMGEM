package gemgemgem.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import gemgemgem.EnumCards;

public class MatchM {
	private HashMap<Integer, EnumCards> p1Cards = new HashMap<>();
	private HashMap<Integer[], EnumCards> boardCards = new HashMap<>();
	private HashMap<Integer, EnumCards> p2Cards = new HashMap<>();
	
	public MatchM() {
	}

	public HashMap<Integer, EnumCards> getP1Cards() {
		return p1Cards;
	}

	public void setP1Cards(HashMap<Integer, EnumCards> p1Cards) {
		this.p1Cards = p1Cards;
	}

	public HashMap<Integer[], EnumCards> getBoardCards() {
		return boardCards;
	}

	public void setBoardCards(HashMap<Integer[], EnumCards> boardCards) {
		this.boardCards = boardCards;
	}

	public HashMap<Integer, EnumCards> getP2Cards() {
		return p2Cards;
	}

	public void setP2Cards(HashMap<Integer, EnumCards> p2Cards) {
		this.p2Cards = p2Cards;
	}
	
	public String toString() {
		StringBuffer modelToString = new StringBuffer();
		for(Map.Entry<Integer, EnumCards> element : p1Cards.entrySet()) {
			modelToString.append(element.getKey()+ ":" + element.getValue().toString() + ",");
		}
		modelToString.append("|");
		for(Map.Entry<Integer[], EnumCards> element : boardCards.entrySet()) {
			modelToString.append(element.getKey() + ":" + element.getValue().toString() + ",");
		}
		modelToString.append("|");
		for(Map.Entry<Integer, EnumCards> element : p2Cards.entrySet()) {
			modelToString.append(element.getKey() + ":"+ element.getValue().toString() + ",");
		}
		modelToString.append("/");
		return modelToString.toString();
	}
	
	public void modelFromString(String modelStringed) {
		String[] modelSplitted = modelStringed.split("|");
		String p1CardsStringed = modelSplitted[0];
		String[] p1CardsSplitted = p1CardsStringed.split(",");
		for(String card : p1CardsSplitted) {
			String index = card.split(":")[0];
			String cardName = card.split(":")[1];
			//p1Cards.put(Integer.parseInt(index),);	??
			//Fai un parser in EnumCards per trovare la carta partendo da una stringa
		}
	}

	
	

}
