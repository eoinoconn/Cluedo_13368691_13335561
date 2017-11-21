package ie.ucd.items;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		String fileName = "GameBoard1.csv";	

		// push text to bottom of command line
		for(int i = 0; i < 999; i++) 
			System.out.println("\n");
		
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
			for(int playerIndex = 0; playerIndex < numPlayers; playerIndex++) {
				
				// Select current turns player
				Player currentPlayer = playerCollection.get(playerIndex);
				whoseGo = currentPlayer.playerNumber();
				
				// enable the player to make a hypothesis
				boolean hypMade = false;
				
				// Clear the command line
				for(int i = 0; i < 999; i++) 
					System.out.println("\n");
				
				// Inform players who's turn it is
				System.out.println("Okay player " + (whoseGo) + ". It's your turn!");
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
					
					// Print the gameboard for users convenience
					gameBoard.printBoard(playerIndex, playerCollection);
					
					// Ask user which action they would like to perform
					System.out.println("Would you like to:\n" + "Enter move mode (M)\n" + "Check your notebook (N)\n" + "Make a hypothesis (H)\n" + "Make an accusation (A)\n" + "End your turn (E)");
					String str = sc.nextLine();
					switch(Character.toUpperCase(str.charAt(0))) {
					case('M'):
						
						// Clear the command line
						for(int i = 0; i < 999; i++) 
							System.out.println("\n");
						
						// Enter move-mode contained in while loop
						inMoveMode = true;
						while((numMovesRemaining > 0) & (inMoveMode)) {
							
							
							// Print gameboard
							gameBoard.printBoard(playerIndex, playerCollection);
							
							// Tell the user how many moves they have left
							System.out.println("You have " + numMovesRemaining + " moves remaining");
							
							// Check where the player wants to move
							System.out.println("Where would you like to move? (Up/Down/Left/Right/Finish)");
							str = sc.nextLine();
							
							// If user enters F, exit move mode
							if(Character.toUpperCase(str.charAt(0)) == 'F') {
								inMoveMode = false;
								
								// Clear the command line
								for(int i = 0; i < 999; i++) 
									System.out.println("\n");
							}
							// Else perform move and check move occurred
							else{

								e = turn.makeMove(str.charAt(0), currentPlayer.getSuspectPawn(), gameBoard);
							
								// Clear the command line
								for(int i = 0; i < 999; i++) 
									System.out.println("\n");
								
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
						// get this player's notebook
						Notebook nb = currentPlayer.getNotebook();
					
						// Clear the command line
						for(int i = 0; i < 999; i++) 
							System.out.println("\n");
					
						// Get number of entries to print from user
						System.out.println("How many notebook entries would you like to view?");
						System.out.println("(Total " + nb.getSize() + " entries)");
						str = sc.nextLine();
						
						// print each of these entries
						String entries = nb.lastNEntries(Integer.parseInt(str));
						System.out.println(entries + "\nPress enter to continue");
						sc.nextLine();
						sc.nextLine();
					
						break;
					case('H'):
						SuspectPawn sp = currentPlayer.getSuspectPawn();
						Slot currentSlot = gameBoard.getSlot(sp.getLocation());
						
						// check that player has not yet made a hypothesis and that they are in a room
						if(currentSlot.getType()!=3) {
							System.out.println("You must be in a room to make a hypothesis!");
						}
						else if(hypMade) {
							System.out.println("You cannot make another hypothesis until your next turn!");
						}
						
						else {
							
							// get the current room and print it to the screen
							int roomIndex = currentSlot.getNumber();
							Room murderRoom = Room.values()[roomIndex];
							System.out.println("You are currently in the " + murderRoom.toString());
							
							System.out.println("Who do think is the murderer?");
							
							//print out all the remaining suspects and get take the players choices
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
							
							// Clear the command line
							for(int j = 0; j < 999; j++) 
								System.out.println("\n");
							
							// make the hypothesis with the chosen suspects
							Suspect murderer = Suspect.values()[suspectIndex];
							Weapon murderWeapon = Weapon.values()[weaponIndex];
							
							String h = turn.makeHypothesis(playerIndex, murderRoom, murderer, murderWeapon, playerCollection);
							System.out.println(h);
							sc.nextLine();
							
							// stop player from making another hypothesis on this turn
							hypMade = true;
						}
						break;
						
					case('A'):
						
						System.out.println("Who do believe is the murderer?");
						
						//print out all the remaining suspects and get the players choices
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
						
						// make the accusation with the chosen suspects
						Suspect murderer = Suspect.values()[suspectIndex];
						Weapon murderWeapon = Weapon.values()[weaponIndex];
						Room murderRoom = Room.values()[roomIndex];
						
						// Make accusation prints messages to notebooks depending on accusation success
						if(turn.makeAccusation(playerIndex, murderRoom, murderer, murderWeapon, playerCollection, murdererCards)) {
							
							// Accusation is correct
							
							// Clear command line
							for(i = 0; i < 999; i++) 
								System.out.println("\n");
							
							// Print success message to screen
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							System.out.println("Congratualtions!! Youve caught the murderer!");
							System.out.println(murderer.toString() + " committed the murder, with the " + murderWeapon.toString() + " in the "
									+ murderRoom.toString() + '.');
							System.out.println("GAME OVER");
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							
							// close application
							System.exit(0);
						}
						else {
							
							//incorrect guess
							// print message to let player know the game is over
							System.out.println("Sorry player " + whoseGo + ", you are wrong and must be removed fromt he game\n"
									+ "Press enter to continue");
							sc.nextLine();
							sc.nextLine();
							
							
							// Remove Player from the game
							playerCollection.remove(playerIndex);
							playerTurnOver = true;
							numPlayers--;
							break;
						}
							
						
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