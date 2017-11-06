package ie.ucd.items;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		String fileName = "GameBoard1.csv";	

	
	    // Initialise Setup instance
		Setup setup = Setup.getInstance();
		
		// Call setupGrid to create grid
		int[][] grid = setup.setupGrid(fileName);
		
		// Create gameBoard instance.
	    GameBoard gameBoard = GameBoard.getInstance(grid);
	    
	    // Scanner instance for reading user input
	    Scanner sc = new Scanner(System.in);
	    
	    // Stores card instances
		ArrayList<Card> cardDeck = setup.setupCardDeck();
		
		System.out.println("Welcome to Cluedo!! By Eoin and Andy.");
		
		// Stores player instances
		ArrayList<Player> playerCollection = setup.setupPlayers(gameBoard, sc);

		
		
		
		
		Random rand = new Random();
		Turn turn = new Turn();
		int numPlayers = playerCollection.size();
		int turnsPlayed, numMovesRemaining, whoseGo, e;
		boolean playerTurnOver = false;
		boolean inMoveMode = true;
		
		
		// Turns loop, keeps play moving in circle
		for(turnsPlayed = 0; turnsPlayed < 100; turnsPlayed++) {
			
			// Player loop, iterates through each player, each turn
			for(whoseGo = 0; whoseGo < numPlayers; whoseGo++) {
				
				// Select current turns player
				Player currentPlayer = playerCollection.get(whoseGo);

				// Inform players whos turn it is
				System.out.println("Okay player " + (whoseGo + 1) + ". It's your turn!");
				System.out.println("Press return to roll the dice");
				sc.nextLine();
				
				// Randomly assign number of moves
				/* As a normal set of playing die is skewed for certain 
				 * values of roles the random integer is called twice
				 * to simulate this real life instance. 
				 */
				numMovesRemaining = rand.nextInt(5) + rand.nextInt(5) + 2;
				
				// Inform player of his moves and location
				System.out.println("You have " + numMovesRemaining + " moves.");
				
				// Begin action loop where the player makes his decisions for their turn
				playerTurnOver = false;
				while(!playerTurnOver) {
					
					// Print gameboard
					gameBoard.printBoard(currentPlayer.getSuspectPawn());
					// Ask user which action they would like to perform
					System.out.println("Would you like to enter move mode, test a hypothesis, make an accusation or end your turn?(M/H/A/E)");
					String str = sc.nextLine();
					switch(Character.toUpperCase(str.charAt(0))) {
					case('M'):
						// Enter move-mode contained in while loop
						inMoveMode = true;
						while((numMovesRemaining > 0) & (inMoveMode)) {
							
							// Print gameboard
							gameBoard.printBoard(currentPlayer.getSuspectPawn());
							
							// Tell the user how many moves they have left
							System.out.println("You have " + numMovesRemaining + " moves remaining");
							
							// Check where the player wants to move
							System.out.println("Where would you like to move? (Up/Down/Left/Right/Finish)");
							str = sc.nextLine();
							
							// If user enters F, exit move mode
							if(Character.toUpperCase(str.charAt(0)) == 'F') {
								inMoveMode = false;
							}
							// Else perform move and check move occurred
							else{
								e = turn.makeMove(str.charAt(0), currentPlayer.getSuspectPawn(), gameBoard);
							
								switch(e) {
								
								case(0):		// Player moves in a room, do not decrement moves
									
									break;
								
								case(1):		// Player moves in a corridor, decrement moves
									
									numMovesRemaining--;
									break;
									
								case(-1):		// Player attempt to make illegal move
									
									System.out.println("Sorry, you can't move there.");
									break;
									
								case(-2):		// Player enters unexpected input
									
									System.out.println("Input not recognised");
									break;
									
								default:		// Unexpected error
									System.exit(1);		//Error with Turn.makeMove();
									
								}
							}
							
							
							
						}
						break;
					case('H'):
						
						break;
						
					case('A'):
						
						break;
						
					case('E'):
						
						playerTurnOver = true;
						break;
						
					default:
						System.out.println("Unexpected input, try again");
						break;
					}
				}
				
			
			}
		}
		sc.close();
		
	}
}