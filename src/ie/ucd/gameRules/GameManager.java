package ie.ucd.gameRules;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.gameEntities.Card;
import ie.ucd.gameEntities.GameBoard;
import ie.ucd.gameEntities.Player;
import ie.ucd.gameEntities.WeaponPawn;

public class GameManager {

	private GameBoard gameBoard; 
	private ArrayList<Card> murdererCards;
	private ArrayList<Player> playerCollection; 
	private Scanner sc;
	private ArrayList<WeaponPawn> weaponPawns;
	
	/**
	 * 
	 * @param playerCollection collection of all players (active and inactive)
	 * @param weaponPawns collection of all weapon pawns which move around the board
	 * @param gameBoard
	 * @param murdererCards the three cards taken from the deck
	 * @param sc scanner for reading user inputs
	 */
	public GameManager(ArrayList<Player> playerCollection, ArrayList<WeaponPawn> weaponPawns, GameBoard gameBoard, ArrayList<Card> murdererCards, Scanner sc) {
		this.gameBoard = gameBoard; 
		this.murdererCards = murdererCards;
		this.playerCollection = playerCollection;
		this.sc = sc;
		this.weaponPawns = weaponPawns;
	}
	
	/**
	 * Contains the main loop of the game
	 */
	public void playGame() {
		
//		// for checking if accusation is working
//		System.out.print(murdererCards);
		
		int numPlayers = playerCollection.size();
		
		// Game will loop through all players until an accusation results in the game ending. The game is ended elsewhere
		while(true) {
			
			// Player loop, iterates through each player
			for(int playerIndex = 0; playerIndex < numPlayers; playerIndex++) {
				
				// start player turn
				new TurnManager(playerIndex, gameBoard, playerCollection, weaponPawns, murdererCards, sc);
				
			
			}
		}
	}

}
