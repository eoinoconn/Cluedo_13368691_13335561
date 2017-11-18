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
		
		System.out.println("Welcome to Cluedo!! By Eoin and Andy.");
		
		// Stores player instances
		ArrayList<Player> playerCollection = setup.setupPlayers(gameBoard, sc);

	    // deals cards to players and selects murderer cards
		ArrayList<Card> murdererCards = setup.dealCards(playerCollection);
		
		
		
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
					
					// Ask user which action they would like to perform
					System.out.println("Would you like to:\n" + "Enter move mode (M)\n" + "Check your notebook (N)\n" + "Make a hypothesis (H)\n" + "Make an accusation (A)\n" + "End your turn (E)");
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
					case('N'):
						Notebook nb = currentPlayer.getNotebook();
						// Get number of entries to print from user
						System.out.println("How many notebook entries would you like to view?");
						System.out.println("(Total " + nb.getSize() + " entries)");
						str = sc.nextLine();
						String entries = nb.lastNEntries(Integer.parseInt(str));
						System.out.println(entries);
					
						break;
					case('H'):
						System.out.println("Who do think is the murder?");
						//print out all the remaining suspects
						int i = 1;
						for(Suspect sus: Suspect.values()) {
							System.out.println(i + " " + sus.toString());
							i++;
						}
						int suspectIndex = sc.nextInt() - 1;
						System.out.println("With which weapon?");
						i = 1;
						for(Weapon weap: Weapon.values()) {
							System.out.println(i + " " + weap.toString());
							i++;
						}
						int weaponIndex = sc.nextInt() - 1;
						System.out.println("In which room?");
						i = 1;
						for(Room ro: Room.values()) {
							System.out.println(i + " " + ro.toString());
							i++;
						}
						int roomIndex = sc.nextInt() - 1;
						Suspect murderer = Suspect.values()[suspectIndex];
						Weapon murderWeapon = Weapon.values()[weaponIndex];
						Room murderRoom = Room.values()[roomIndex];
						String h = turn.makeHypothesis(whoseGo, murderRoom, murderer, murderWeapon, playerCollection);
						System.out.println(h);
						
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