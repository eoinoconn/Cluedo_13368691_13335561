package ie.ucd.gameRules;

import java.util.Scanner;

import ie.ucd.setup.GameSetup;

public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		new Cluedo();
		
	}
	
	public Cluedo() {
		String fileName = "GameBoard1.csv";	

		// Scanner instance for reading user input
	    Scanner sc = new Scanner(System.in);
		
		// Initialise Setup instance
		GameSetup setup = new GameSetup(fileName, sc);
		
		// setup.startGame returns Turn object
		GameManager gameManager = setup.getGameManager();
		
		// Begin 
		gameManager.playGame();
		
		// close Scanner
		sc.close();
	}
}