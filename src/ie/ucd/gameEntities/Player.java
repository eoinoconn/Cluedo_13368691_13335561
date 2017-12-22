/*
 * Player class
 * Models players in the game
 */

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
	
	public Player(SuspectPawn suspectPawn, boolean active) {
		this.suspectPawn = suspectPawn;
		this.notebook = new Notebook();
		this.hand = new Hand();
		this.playerNumber = ++playerCounter;
		numMoves = 0;
		this.active = active;
	}
	
	/**
	 * 
	 * @return the suspect pawn instance belonging to this player
	 */
	public SuspectPawn getSuspectPawn() {
		return suspectPawn;
	}
	
	/**
	 * 
	 * @return the notebook instance belonging to this player
	 */
	public Notebook getNotebook() {
		return notebook;
	}
	
	/**
	 * 
	 * @return the players order in the collection of all players
	 */
	public int playerNumber() {
		return this.playerNumber;
	}
	
	/**
	 * 
	 * @return the number of moves available from the most recent dice roll
	 */
	public int getMoves() {
		return numMoves;
	}
	
	/**
	 * Decrements the players remaining moves
	 * @return the number of moves remaining
	 */
	public int moveMade() {
		return --this.numMoves;
	}

	/**
	 * 
	 * @return whether this player has made a hypothesis already on this turn
	 */
	public boolean getHypMade() {
		return hypMade;
	}
	
	/*
	 * Set whether this player has made a hypothesis already on this turn
	 */
	public void setHypMade(boolean hypMade) {
		this.hypMade = hypMade;
	}
	
	/**
	 * Set the number of moves remaining on this turn
	 * @param moves
	 */
	public void setMoves(int moves) {
		numMoves = moves;
	}
	
	/**
	 * check this players hand for these three cards
	 * @param room
	 * @param suspect
	 * @param weapon
	 * @return
	 */
	public Card checkCards(Room room, Suspect suspect, Weapon weapon) {
		
		return hand.checkCards(room, suspect, weapon);
		
	}

	/**
	 * print out what cards this player has to console
	 */
	public String lookAtHand() {
		return hand.toString();
	}
	
	/**
	 * 
	 * @param card to be dealt to this player
	 */
	public void giveCard(Card card) {
		hand.addCard(card);
	}
	
	/**
	 * 
	 * @return a number between 2 and 12 with probability of each number equivalent to rolling two dice
	 */
	public int rollDice() {
		numMoves = rand.nextInt(6) + rand.nextInt(6) + 2;
		return numMoves;
	}
	
	/**
	 * 
	 * @return whether the player is in the game or not
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Sets the active attribute to false, removing the player from play
	 */
	public void removeFromGame() {
		active = false;
	}
	
}