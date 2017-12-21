package ie.ucd.gameRules;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.items.Card;
import ie.ucd.items.GameBoard;
import ie.ucd.items.Player;
import ie.ucd.items.WeaponPawn;

public class GameManager {

	private GameBoard gameBoard; 
	private ArrayList<Card> murdererCards;
	private ArrayList<Player> playerCollection; 
	private Scanner sc;
	private ArrayList<WeaponPawn> weaponPawns;
	
	public GameManager(ArrayList<Player> playerCollection, ArrayList<WeaponPawn> weaponPawns, GameBoard gameBoard, ArrayList<Card> murdererCards, Scanner sc) {
		this.gameBoard = gameBoard; 
		this.murdererCards = murdererCards;
		this.playerCollection = playerCollection;
		this.sc = sc;
		this.weaponPawns = weaponPawns;
	}
	
	public void playGame() {
		
		System.out.print(murdererCards);
		
		int numPlayers = playerCollection.size();
		int turnsPlayed;
		
		// Turns loop, keeps play moving in circle
		for(turnsPlayed = 0; turnsPlayed < 100; turnsPlayed++) {
			
			// Player loop, iterates through each player, each turn
			for(int playerIndex = 0; playerIndex < numPlayers; playerIndex++) {
				
				// start player turn
				new TurnManager(playerIndex, gameBoard, playerCollection, weaponPawns, murdererCards, sc);
				
			
			}
		}
	}

}
