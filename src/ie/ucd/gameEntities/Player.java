package ie.ucd.gameEntities;

import java.util.Random;

public class Player {
	
	private static int playerCounter = 0;
	private SuspectPawn suspectPawn;
	private Notebook notebook;
	private Hand hand;
	private int playerNumber;
	private boolean hypMade;
	Random rand = new Random();
	private int numMoves;
	private boolean active;
	
	public Player(GameBoard gameBoard, int[] location, Suspect name, boolean active) {
		this.suspectPawn = new SuspectPawn(gameBoard, location, name); //place pawn with specified name in start location
		this.notebook = new Notebook();
		this.hand = new Hand();
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
		
		return hand.checkCards(room, suspect, weapon);
		
	}
	
	public String lookAtHand() {
		return hand.toString();
	}
	
	public void giveCard(Card card) {
		hand.addCard(card);
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