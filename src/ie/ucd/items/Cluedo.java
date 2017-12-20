package ie.ucd.items;

import java.util.Scanner;

import ie.ucd.setup.GameSetup;

public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		String fileName = "GameBoard1.csv";	

		// Scanner instance for reading user input
	    Scanner sc = new Scanner(System.in);
		
		// Initialise Setup instance
		GameSetup setup = new GameSetup(fileName, sc);
		
		// setup.startGame returns Turn object
		Turn turn = setup.getTurn();
		
		// Begin 
		turn.playGame(sc);
		
		// close Scanner
		sc.close();
		
	}
}