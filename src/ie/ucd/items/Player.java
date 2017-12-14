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
	private boolean active;
	
	public Player(GameBoard gameBoard, int[] location, Suspect name, boolean active) {
		this.suspectPawn = new SuspectPawn(gameBoard, location, name); //place pawn with specified name in start location
		this.notebook = new Notebook();
		this.cardHand = new ArrayList<Card>();
		this.playerNumber = ++playerCounter;
		numMoves = 0;
		this.active = active;
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

	public boolean getHypMade() {
		return hypMade;
	}
	
	public void setMoves(int moves) {
		numMoves = moves;
	}
	
	public void setHypMade(boolean hypMade) {
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
		numMoves = rand.nextInt(6) + rand.nextInt(6) + 2;
		return numMoves;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void removeFromGame() {
		active = false;
	}
	
}