package ie.ucd.items;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Turn {

	private static Turn uniqueInstance = null;
	ArrayList<Player> playerCollection;
	GameBoard gameBoard;
	ArrayList<Card> murdererCards;
	Random rand = new Random();
	
	
	public static Turn getInstance(ArrayList<Player> playerCollection, GameBoard gameBoard, ArrayList<Card> murdererCards) {
		if(uniqueInstance == null)
			uniqueInstance = new Turn(playerCollection, gameBoard, murdererCards);
		return uniqueInstance;
	}
	
	private Turn(ArrayList<Player> playerCollection, GameBoard gameBoard, ArrayList<Card> murdererCards) {
		this.playerCollection = playerCollection;
		this.gameBoard = gameBoard;
		this.murdererCards = murdererCards;
	}
	
	public void playGame(Scanner sc) {
		
		int numPlayers = playerCollection.size();
		int turnsPlayed;
		
		// Turns loop, keeps play moving in circle
		for(turnsPlayed = 0; turnsPlayed < 100; turnsPlayed++) {
			
			// Player loop, iterates through each player, each turn
			for(int playerIndex = 0; playerIndex < numPlayers; playerIndex++) {
				
				// start player turn
				this.startTurn(playerIndex, sc);
				
			
			}
		}
	}
	
	private void startTurn(int playerIndex, Scanner sc) {
		
		
		// Select current turns player
		Player currentPlayer = playerCollection.get(playerIndex);
		currentPlayer.hypMade(false);
		int whoseGo = currentPlayer.playerNumber();
		
		
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
		int numMovesRemaining = currentPlayer.rollDice();
		
		// Inform player of his moves and location
		System.out.println("You have " + numMovesRemaining + " moves.");
		
		// Begin action loop where the player makes his decisions for their turn
		boolean playerTurnOver = false;
		while(!playerTurnOver) {
			
			// Print the gameboard for users convenience
			gameBoard.printBoard(playerIndex, playerCollection);
			
			// Ask user which action they would like to perform
			System.out.println("Would you like to:\n" + "Enter move mode (M)\n" + "Check your notebook (N)\n" + "Make a hypothesis (H)\n" + "Make an accusation (A)\n" + "End your turn (E)");
			String str = sc.nextLine();
			switch(Character.toUpperCase(str.charAt(0))) {
			case('M'):
				this.moveMode(playerIndex, sc);
				break;
			case('N'):
				this.openNotebook(playerIndex, sc);
				break;
			case('H'):
				this.makeHypothesis(playerIndex, sc);
				break;
				
			case('A'):
				if(this.makeAccusation(playerIndex, sc));
					playerTurnOver = true;	
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
	
	public void makeHypothesis(int playerIndex, Scanner sc) {
		
		Player currentPlayer = playerCollection.get(playerIndex);
		SuspectPawn sp = currentPlayer.getSuspectPawn();
		Slot currentSlot = gameBoard.getSlot(sp.getLocation());
		
		// check that player has not yet made a hypothesis and that they are in a room
		if(currentSlot.getType()!=3) {
			System.out.println("You must be in a room to make a hypothesis!");
		}
		else if(currentPlayer.hypMade()) {
			System.out.println("You cannot make another hypothesis until your next turn!");
		}
		
		else {
			
			// get the current room and print it to the screen
			int roomIndex = currentSlot.getNumber();
			Room murderRoom = Room.values()[roomIndex-1];
			System.out.println("You are currently in the " + murderRoom.toString());
			
			System.out.println("Who do you think is the murderer?");
			Suspect murderer = this.getSuspectChoice(sc);

			System.out.println("With which weapon?");
			Weapon murderWeapon = this.getMurderWeapon(sc);
			
			// Clear the command line
			for(int j = 0; j < 999; j++) 
				System.out.println("\n");
			
			// make the hypothesis with the chosen suspects
			
			
			
			//Create the string to add to the notebooks of all players
			String str_1 = "Player " + (playerCollection.get(playerIndex).playerNumber()) + " suspects that " + murderer.toString() + " committed the murder with the " + murderWeapon.toString() + " in the " + murderRoom.toString();
			
			boolean refuted = false;
			
			// Loop to iterate through every Player
			for (int i = 0; i < playerCollection.size(); i++) {
				
				// Check each players hand for the mentioned hypothesis
				// Take in the first card that refutes the hypothesis
				// Do not check own hand
				if(i==playerIndex) continue;
				
				Card refute = playerCollection.get(i).checkCards(murderRoom, murderer, murderWeapon);
				if (refute != null) {
					
					refuted = true;
					// Create the string to add to the notebooks of the un-involved players
					String str_2 = str_1 + "\nPlayer " + (playerCollection.get(i).playerNumber()) + " refuted Player " + (playerCollection.get(playerIndex).playerNumber()) + "'s hypothesis";
					
					// Then add that string to the notebook of the un-involved players
					for(int j = 0; j < playerCollection.size(); j++) {
						if (j == i || j == playerIndex) continue;
						playerCollection.get(j).getNotebook().addEvent(str_2);
					}
					
					// create the string to add to the refuting players notebook
					str_2 = str_1 + "\nYou refuted Player " + (playerCollection.get(playerIndex).playerNumber()) +"'s hypothesis with " + refute.getName().toString();
					playerCollection.get(i).getNotebook().addEvent(str_2);
	
					// create the relevant string to add to the current players notebook
					str_2 = "\nPlayer " + (playerCollection.get(i).playerNumber()) + " refuted your hypothesis with " + refute.getName().toString();
					playerCollection.get(playerIndex).getNotebook().addEvent(str_1 + str_2);
	
					// Exit the loop
					System.out.println(str_2);
					break;
				}
				
			}
			if(refuted == false) {
				// In the event the hypothesis is not refuted, tell all the players 
				str_1 = str_1 + "\nPlayer " + (playerCollection.get(playerIndex).playerNumber()) + "'s hypothesis was not refuted";
				
				for(Player player: playerCollection) {
					player.getNotebook().addEvent(str_1);
				}
				
				// return printable string for current player
				System.out.println("Your Hypothesis was not refuted");
				
				
				// stop player from making another hypothesis on this turn
				currentPlayer.hypMade(true);
			}
			sc.nextLine();
		}
	}
	
	public void moveMode(int playerIndex, Scanner sc) {
		
		Player currentPlayer = playerCollection.get(playerIndex);
		
		// Clear the command line
		for(int i = 0; i < 999; i++) 
			System.out.println("\n");
		
		// Enter move-mode contained in while loop
		boolean inMoveMode = true;
		while((currentPlayer.getMoves() > 0) & (inMoveMode)) {
			
			
			// Print gameboard
			gameBoard.printBoard(playerIndex, playerCollection);
			
			// Tell the user how many moves they have left
			System.out.println("You have " + currentPlayer.getMoves() + " moves remaining");
			
			// Check where the player wants to move
			System.out.println("Where would you like to move? (Up/Down/Left/Right/Finish)");
			String str = sc.nextLine();
			
			// If user enters F, exit move mode
			if(Character.toUpperCase(str.charAt(0)) == 'F') {
				inMoveMode = false;
				
				// Clear the command line
				for(int i = 0; i < 999; i++) 
					System.out.println("\n");
			}
			// Else perform move and check move occurred
			else{

				int e = this.makeMove(str.charAt(0), currentPlayer.getSuspectPawn());
			
				// Clear the command line
				for(int i = 0; i < 999; i++) 
					System.out.println("\n");
				
				switch(e) {
				
				case(0):		// Player moves in a room, do not decrement moves
					
					break;
				
				case(1):		// Player moves in a corridor, decrement moves
				
					currentPlayer.moveMade();
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
	}
	
	private int makeMove(char direction, SuspectPawn suspectPawn) {
		int[] options = gameBoard.getOptions(suspectPawn);
		int dim = gameBoard.getDimensions();
		
		switch(direction) {
		case 'u':
			
			if(options[1]>0 && !((options[0]==1 && options[1]==3)||(options[0]==3 && options[1]==1))) { // not at moving off board or into wall
				suspectPawn.setLocation(suspectPawn.getLocation()[0]-1, suspectPawn.getLocation()[1]);
				if(options[1]<3||options[0]==2) {
					return 1; 		// return 1 if used up a move
				}
				return 0;			// return 0 if did not use up a move
			}
			else if(options[1]==0) { // on top row
				if(options[3]==0) { // in top left corner
					suspectPawn.setLocation(dim-1, dim-1);
					return 0; // do not use up a move to move in secret passage
				}
				else if(options[4]==0) { // in top right corner
					suspectPawn.setLocation(dim-1, 0);
					return 0; // do not use up a move to move in secret passage
				}
			}
			return -1;				// return -1 if could not move
		case 'd':
			if(options[2]>0 && !((options[0]==1 && options[2]==3)||(options[0]==3 && options[2]==1))) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0]+1, suspectPawn.getLocation()[1]);
				if(options[2]<3||options[0]==2) { 
					return 1; 		// return 1 if used up a move
				}
				return 0;			// return 0 if did not use up a move
			}
			else if(options[2]==0) { // on bottom row
				if(options[3]==0) { // in bottom left corner
					suspectPawn.setLocation(0, dim-1);
					return 0; // do not use up a move to move in secret passage
				}
				else if(options[4]==0) { // in bottom right corner
					suspectPawn.setLocation(0, 0);
					return 0; // do not use up a move to move in secret passage
				}
			}
			return -1;				// return -1 if could not move
		case 'l':
			if(options[3]>0 && !((options[0]==1 && options[3]==3)||(options[0]==3 && options[3]==1))) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]-1);
				if(options[3]<3||options[0]==2) { 
					return 1; 		// return 1 if used up a move
				}
				return 0;			// return 0 if did not use up a move
			}
			else if(options[3]==0) { // on left wall
				if(options[1]==0) { // in top left corner
					suspectPawn.setLocation(dim-1, dim-1);
					return 0; // do not use up a move to move in secret passage
				}
				else if(options[2]==0) { // in bottom left corner
					suspectPawn.setLocation(0, dim-1);
					return 0; // do not use up a move to move in secret passage
				}
			}
			return -1;				// return -1 if could not move
		case 'r':
			if(options[4]>0 && !((options[0]==1 && options[4]==3)||(options[0]==3 && options[4]==1))) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]+1);
				if(options[4]<3||options[0]==2) { 
					return 1; 		// return 1 if used up a move
				}
				return 0;			// return 0 if did not use up a move
			}
			else if(options[4]==0) { // on right wall
				if(options[1]==0) { // in top right corner
					suspectPawn.setLocation(dim-1, 0);
					return 0; // do not use up a move to move in secret passage
				}
				else if(options[2]==0) { // in bottom right corner
					suspectPawn.setLocation(0, 0);
					return 0; // do not use up a move to move in secret passage
				}
			}
			return -1;				// return -1 if could not move
		default:
			return -2;				// return -2 if invalid option entered
		}

	}

	public boolean makeAccusation(int playerIndex, Scanner sc) {
		
		
		Player currentPlayer = this.playerCollection.get(playerIndex);
		int whoseGo = currentPlayer.playerNumber();
		
		
		SuspectPawn sp = currentPlayer.getSuspectPawn();
		Slot currentSlot = this.gameBoard.getSlot(sp.getLocation());
		
		// check that player has not yet made a hypothesis and that they are in a room
		if(currentSlot.getType()!=3) {
			System.out.println("You must be in a room to make a hypothesis!");
			return false;
		}
		else if(currentPlayer.hypMade()) {
			System.out.println("You cannot make another hypothesis until your next turn!");
			return false;
		}
		
		else {
			
			// get the current room and print it to the screen
			int roomIndex = currentSlot.getNumber();
			Room murderRoom = Room.values()[roomIndex];
			System.out.println("You are currently in the " + murderRoom.toString());
			
			// Take suspect input
			System.out.println("Who do believe is the murderer?");
			Suspect murderer = this.getSuspectChoice(sc);
			
			// Take weapon input
			System.out.println("With which weapon?");
			Weapon murderWeapon = this.getMurderWeapon(sc);
	
			
			// make the accusation with the chosen suspects
			if(murderer == murdererCards.get(0).getName() && murderRoom == murdererCards.get(1).getName() 
					&& murderWeapon == murdererCards.get(2).getName()) {
				
				// Accusation is correct
				
				// Clear command line
				for(int i = 0; i < 999; i++) 
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
				
				// incorrect Accusation
				// Create the string to add to the notebooks of all players
				String str_1 = "Player " + (playerCollection.get(whoseGo).playerNumber()) + " believes that " + murderer.toString() + " committed the murder with the " 
						+ murderWeapon.toString() + " in the " + murderRoom.toString();
				
				// Create the string to add to the notebooks of the un-involved players
				String str_2 = str_1 + "\nPlayer " + (playerCollection.get(whoseGo).playerNumber()) + " is wrong and has been removed from the game!";
				
				// Then add that string to the notebook of the uninvolved players
				for(int i = 0; i < playerCollection.size(); i++) {
					if (i == whoseGo) continue;
					playerCollection.get(i).getNotebook().addEvent(str_2);
				}
								
				
				// print message to let player know the game is over
				System.out.println("Sorry player " + whoseGo + ", you are wrong and must be removed fromt he game\n"
						+ "Press enter to continue");
				sc.nextLine();
				
				
				// Remove Player from the game
				playerCollection.remove(playerIndex);
			}

		}
		return true;
	}

	public void openNotebook(int playerIndex, Scanner sc) {
		Player currentPlayer = this.playerCollection.get(playerIndex);
		
		// get this player's notebook
		Notebook nb = currentPlayer.getNotebook();
	
		// Clear the command line
		for(int i = 0; i < 999; i++) 
			System.out.println("\n");
	
		// Get number of entries to print from user
		System.out.println("How many notebook entries would you like to view?");
		System.out.println("(Total " + nb.getSize() + " entries)");
		String str = sc.nextLine();
		
		// print each of these entries
		String entries = nb.lastNEntries(Integer.parseInt(str));
		System.out.println(entries + "\nPress enter to continue");
		sc.nextLine();
		sc.nextLine();
	}
	
	private Suspect getSuspectChoice(Scanner sc) {
		//print out all the remaining suspects and get take the players choices
		int i = 1;
		for(Suspect sus: Suspect.values()) {
			System.out.println(i + " " + sus.toString());
			i++;
		}
		int suspectIndex = sc.nextInt() - 1;
		return Suspect.values()[suspectIndex];
	}
	
	private Weapon getMurderWeapon(Scanner sc) {
		int i = 1;
		for(Weapon weap: Weapon.values()) {
			System.out.println(i + " " + weap.toString());
			i++;
		}
		int weaponIndex = sc.nextInt() - 1;
		return Weapon.values()[weaponIndex];
	}
	
}

