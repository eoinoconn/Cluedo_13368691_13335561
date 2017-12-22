package ie.ucd.setup;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.gameEntities.Card;
import ie.ucd.gameRules.GameManager;

public class GameSetup extends Setup{

	private String fileName;
	
	public GameSetup(String fileName, Scanner sc) {
		Setup.sc = sc;
		this.fileName = fileName;
	}
	
	/**
	 * Created instances of each of the setup classes and sets up the game entities
	 * @return The game manager class instance
	 */
	public GameManager getGameManager() {
		// push text to bottom of command line
		for(int i = 0; i < 999; i++) 
			System.out.println("\n");
				
		System.out.println("Welcome to Cluedo!! By Eoin and Andy.");
		
		// Create gameBoard instance.
		GameBoardSetup setupGameBoard = new GameBoardSetup();
	    setupGameBoard.gameBoardSetup(fileName);
	    
		// Stores player instances
	    PlayerSetup setupPlayers = new PlayerSetup();
		setupPlayers.setupPlayers();
		
		// Stores Weapon Pawns
		SetupWeaponPawns pawnSetup = new SetupWeaponPawns();
		pawnSetup.setupWeaponPawns();

	    // deals cards to players and selects murderer cards
		CardsSetup setupCards = new CardsSetup();
		ArrayList<Card> murdererCards = setupCards.setupCards();
		
		return new GameManager(playerCollection, weaponPawns, gameBoard, murdererCards, sc);
	}

}
