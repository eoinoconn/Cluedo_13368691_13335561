package ie.ucd.items;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		String fileName = "GameBoard1.csv";	
		File file = new File(fileName);
	    List<List<String>> lines = new ArrayList<>();
	    Scanner inputStream;
	    int[][] grid = new int[25][25];
	    
	    
	    // Open file, scan grid into lines list
	    try{
	        inputStream = new Scanner(file);
	        while(inputStream.hasNext()){
	        
	        	String line = inputStream.next();
	            String[] values = line.split(",");
	            // this adds the currently parsed line to the 2-dimensional string array
	            lines.add(Arrays.asList(values));
	        }
	        inputStream.close();
	    }catch (FileNotFoundException e) {
	            e.printStackTrace();
	    }
	
	    // convert our grid of strings representing location values into 
	    // grid of integers 
	    int lineNo = 0;
	    for(List<String> line: lines) {
	        int columnNo = 0;
	        for (String value: line) {
	        	grid[lineNo][columnNo] = Integer.parseInt(value);
	            columnNo++;
	        }
	        lineNo++;
	    }
	
	    // Initialise gameboard
		
	    GameBoard gameBoard = GameBoard.getInstance(grid);
	   
		
		
		// TODO This could be replaced at a later date with a facade		
		// Create instances of game cards
		// Store game cards in cardDeck
	    int i = 0;
	    int[] location = new int[2];
		ArrayList<Card> cardDeck = new ArrayList<Card>();
		ArrayList<Suspect> suspectCollection = new ArrayList<Suspect>();
		ArrayList<Room> roomCollection = new ArrayList<Room>();
		ArrayList<WeaponPawn>  weaponPawnCollection = new ArrayList<WeaponPawn>();
		for (Suspect per : Suspect.values())
		{
			cardDeck.add(new SuspectCard(per));
			suspectCollection.add(per);
		}
		for (Room ro : Room.values())
		{
			cardDeck.add(new RoomCard());
			roomCollection.add(ro);
		}
		Collections.shuffle(roomCollection);
		for (Weapon wep : Weapon.values())
		{
			cardDeck.add(new WeaponCard(wep));
			location = gameBoard.getRoomLocation(roomCollection.get(i++));
			weaponPawnCollection.add(new WeaponPawn(location[0], location[1], wep));
		}

		
		System.out.println("Welcome to Cluedo!! By eoin and Andy.");
		
		// Establish who's playing
		Scanner sc = new Scanner(System.in);
		boolean anotherPlayer = false;
		ArrayList<Player> playerCollection = new ArrayList<Player>();
		int numPlayers = 0;
		int numSuspects = 0;
		int suspectIndex = 0;
		
		// Loop to repeatedly ask for new players
		do {
			
			// Input player name and initialise player object
			System.out.println("Hello player " + (++numPlayers) + '.');
			System.out.println("Please enter your name:");
			// TODO catch exceptions
			String str = sc.next();
			sc.nextLine();
			System.out.println("Welcome to the game " + str + '!');
			
			// See who they'd like to be
			System.out.println("Who would you like to play as?");
			//print out all the remaining suspects
			numSuspects = 0;
			for(Suspect sus: suspectCollection) {
				System.out.println((numSuspects+1) + " " + sus.toString());
				numSuspects++;
			}
			
			// Take their choice of suspect
			System.out.println("Enter a number between 1 & " + numSuspects + ":");
			suspectIndex = sc.nextInt() - 1;
			System.out.println(str + ", you are suspect " + suspectCollection.get(suspectIndex));
			
			// Get location of room they're starting in
			location = gameBoard.getRoomLocation(roomCollection.get(numPlayers - 1));
			
			// Create player with starting location and suspect type
			playerCollection.add(new Player(location[0], location[1], suspectCollection.get(suspectIndex)));
			
			// Now that that suspect is in the game we remove it from consideration for other players
			suspectCollection.remove(suspectIndex);
			
			// Check if other players are present
			sc.nextLine();
			System.out.println("Would you like to add another player? (Y/N)");
			str = sc.nextLine();
			if (Character.toUpperCase(str.charAt(0)) == 'Y') {
				anotherPlayer = true;
			} else anotherPlayer = false;
			
		} while (anotherPlayer);
		
		
		Random rand = new Random();
		Turn turn = new Turn();
		int turns, numMoves;
		int whoseGo, e;
		boolean turnOver = false;
		boolean moving = true;
		// Turns loop, keeps play moving in circle
		for(turns = 0; turns < 100; turns++) {
			// Player loop, iterates through each player, each turn
			for(whoseGo = 0; whoseGo < numPlayers; whoseGo++) {
				
				// Select current turns player
				Player currentPlayer = playerCollection.get(whoseGo);

				// Inform players whos turn it is
				System.out.println("Okay player " + (whoseGo + 1) + ". It's your turn!");
				System.out.println("Press return to continue");
				sc.nextLine();
				
				// Randomly assign number of moves
				System.out.println("Lets roll the dice!!");
				/* As a normal set of playing die is skewed for certain 
				 * values of roles the random integer is called twice
				 * to simulate this real life instance. 
				 */
				numMoves = rand.nextInt(5) + rand.nextInt(5) + 2;
				
				// Inform player of his moves and location
				location = currentPlayer.getSuspectPawn().getLocation();
				System.out.println("You have " + numMoves + " moves.");
				System.out.println("You are at location " + location[0] + ' ' + location[1]);
				
				// Begin action loop where the player makes his decisions for their turn
				turnOver = false;
				while(!turnOver) {
					
					// Ask user which action they would like to perform
					System.out.println("Would you like to enter move mode, test a hypothesis, make an accusation or end your turn?(M/H/A/E)");
					String str = sc.nextLine();
					switch(Character.toUpperCase(str.charAt(0))) {
					case('M'):
						// Enter move-mode contained in while loop
						moving = true;
						while((numMoves > 0) & (moving)) {
							
							// Print gameboard
							gameBoard.printBoard(currentPlayer.getSuspectPawn());
							
							// Tell the user how many moves they have left
							System.out.println("You have " + numMoves + " moves remaining");
							
							// Check where the player wants to move
							System.out.println("Where would you like to move? (Up/Down/Left/Right/Finish)");
							str = sc.nextLine();
							
							// If user enters F, exit move mode
							if(Character.toUpperCase(str.charAt(0)) == 'F') {
								moving = false;
							}
							// Else perform move and check move occurred
							else{
								e = turn.makeMove(str.charAt(0), currentPlayer.getSuspectPawn(), gameBoard);
							
								switch(e) {
								
								case(0):		// Player moves in a room, do not decrement moves
									
									break;
								
								case(1):		// Player moves in a corridor, decrement moves
									
									numMoves--;
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
						
						turnOver = true;
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