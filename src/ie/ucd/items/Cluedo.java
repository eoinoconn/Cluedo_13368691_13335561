package ie.ucd.items;

import java.util.Scanner;

public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		String fileName = "GameBoard1.csv";	

		// Scanner instance for reading user input
	    Scanner sc = new Scanner(System.in);
		
		// Initialise Setup instance
		Setup setup = Setup.getInstance();
		
		// setup.startGame returns Turn object
		Turn turn = setup.setupGame(fileName, sc);
		
		// Begin 
		turn.playGame(sc);
		
		// close Scanner
		sc.close();
		
	}
}