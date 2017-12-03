package ie.ucd.items;

import java.util.ArrayList;
import java.util.Random;

public class Player {
	
	private static int playerCounter = 0;
	private SuspectPawn suspectPawn;
	private Notebook notebook;
	private ArrayList<Card> cardHand;
	private int playerNumber;
	private boolean hypMade;
	Random rand = new Random();
	private int numMoves;
	
	public Player(int xlocation, int ylocation, Suspect name) {
		this.suspectPawn = new SuspectPawn(xlocation, ylocation, name); //place pawn with specified name in start location
		this.notebook = new Notebook();
		this.cardHand = new ArrayList<Card>();
		this.playerNumber = ++playerCounter;
		numMoves = 0;
	}
	
	public SuspectPawn getSuspectPawn() {
		return suspectPawn;
	}
	
	public Notebook getNotebook() {
		return notebook;
	}
	
	public int playerNumber() {
		return this.playerNumber;
	}
	
	public int getMoves() {
		return numMoves;
	}
	
	public int moveMade() {
		return --this.numMoves;
	}

	public boolean hypMade() {
		return hypMade;
	}
	
	public void setMoves(int moves) {
		numMoves = moves;
	}
	
	public void hypMade(boolean hypMade) {
		this.hypMade = hypMade;
	}
	
	public Card checkCards(Room room, Suspect suspect, Weapon weapon) {
		
		// iterate through each card in hand
		for(int i = 0; i < cardHand.size(); i++) {
			Card currentCard = cardHand.get(i);
			
			// Check if and of the hypothesis statement match players cards
			if((currentCard instanceof RoomCard && ((RoomCard) currentCard).getName() == room) ||
				(currentCard instanceof WeaponCard && ((WeaponCard) currentCard).getName() == weapon) ||
				(currentCard instanceof SuspectCard && ((SuspectCard) currentCard).getName() == suspect)) {
				return currentCard;	// Found card, refuting hypothesis
			}
		}
		return null;	// Did not find card, do not refute hypothesis
	}
	
	public void giveCard(Card card) {
		cardHand.add(card);
	}
	
	public int rollDice() {
		numMoves = rand.nextInt(5) + rand.nextInt(5) + 2;
		return numMoves;
	}
	
}