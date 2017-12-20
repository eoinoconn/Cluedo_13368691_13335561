package ie.ucd.setup;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.items.Card;
import ie.ucd.items.GameBoard;
import ie.ucd.items.Turn;
import ie.ucd.items.WeaponPawn;

public class GameSetup extends Setup{

	
	public GameSetup(String fileName, Scanner sc) {
		Setup.sc = sc;
		Setup.fileName = fileName;
	}
	
	public Turn getTurn() {
		// push text to bottom of command line
		for(int i = 0; i < 999; i++) 
			System.out.println("\n");
				
		System.out.println("Welcome to Cluedo!! By Eoin and Andy.");
		
		// Create gameBoard instance.
		GameBoardSetup setupGameBoard = new GameBoardSetup();
	    GameBoard gameBoard = setupGameBoard.gameBoardSetup(fileName);
	    
		// Stores player instances
	    PlayerSetup setupPlayers = new PlayerSetup();
		playerCollection = setupPlayers.setupPlayers(gameBoard);
		
		// Stores Weapon Pawns
		SetupWeaponPawns pawnSetup = new SetupWeaponPawns();
		ArrayList<WeaponPawn> weaponPawns = pawnSetup.setupWeaponPawns(gameBoard);

	    // deals cards to players and selects murderer cards
		CardsSetup setupCards = new CardsSetup();
		ArrayList<Card> murdererCards = setupCards.dealCards();
		
		return Turn.getInstance(playerCollection, weaponPawns, gameBoard, murdererCards);
	}

}
