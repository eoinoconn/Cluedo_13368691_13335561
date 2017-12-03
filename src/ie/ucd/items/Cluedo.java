package ie.ucd.items;

import java.util.Scanner;
import java.util.ArrayList;

public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		String fileName = "GameBoard1.csv";	

		// push text to bottom of command line
		for(int i = 0; i < 999; i++) 
			System.out.println("\n");
		
		// Scanner instance for reading user input
	    Scanner sc = new Scanner(System.in);
		
		// Initialise Setup instance
		Setup setup = Setup.getInstance();
		
		Turn turn = setup.startGame(fileName, sc);
		
		
		System.out.println("Welcome to Cluedo!! By Eoin and Andy.");
		
	
		// Create turn object to handle turn actions
		int numPlayers = playerCollection.size();
		int turnsPlayed;
		
		// Turns loop, keeps play moving in circle
		for(turnsPlayed = 0; turnsPlayed < 100; turnsPlayed++) {
			
			// Player loop, iterates through each player, each turn
			for(int playerIndex = 0; playerIndex < numPlayers; playerIndex++) {
				
				// start player turn
				turn.startTurn(playerIndex, sc);
				
			
			}
		}
		sc.close();
		
	}
}