package ie.ucd.items;

import java.util.ArrayList;

public class Player {
	
	private SuspectPawn suspectPawn;
	private Notebook notebook;
	private ArrayList<Card> cardHand;
	private int moves;

	
	public Player(int xlocation, int ylocation, Suspect name) {
		this.suspectPawn = new SuspectPawn(xlocation, ylocation, name); //place pawn with specified name in start location
		this.notebook = new Notebook();
		this.cardHand = new ArrayList<Card>();
		moves = 0;
	}
	
	public SuspectPawn getSuspectPawn() {
		return suspectPawn;
	}
	
	public Notebook getNotebook() {
		return notebook;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public void setMoves(int moves) {
		this.moves = moves;
	}

	public Card checkCards(Room room, Suspect suspect, Weapon weapon) {
		for(int i = 0; i < cardHand.size(); i++) {
			Card currentCard = cardHand.get(i);
			if((currentCard instanceof RoomCard && ((RoomCard) currentCard).getName() == room) ||
				(currentCard instanceof WeaponCard && ((WeaponCard) currentCard).getName() == weapon) ||
				(currentCard instanceof SuspectCard && ((SuspectCard) currentCard).getName() == suspect)) {
				return currentCard;
			}
		}
		return null;
	}
	
	public void giveCard(Card card) {
		cardHand.add(card);
	}
	
}