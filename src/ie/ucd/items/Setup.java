package ie.ucd.items;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Random;


public class Setup {

	private static Setup uniqueInstance = null;
	
	public static Setup getInstance() {
		if(uniqueInstance == null)
			uniqueInstance = new Setup();
		return uniqueInstance;
	}

	
	public Turn setupGame(String fileName, Scanner sc) {
		
		// push text to bottom of command line
		for(int i = 0; i < 999; i++) 
			System.out.println("\n");
				
		System.out.println("Welcome to Cluedo!! By Eoin and Andy.");
		
		// Call setupGrid to create grid
		int[][] grid = this.setupGrid(fileName);
		
		// Create gameBoard instance.
	    GameBoard gameBoard = GameBoard.getInstance(grid);
	    
		// Stores player instances
		ArrayList<Player> playerCollection = this.setupPlayers(gameBoard, sc);
		
		// Stores Weapon Pawns
		ArrayList<WeaponPawn> weaponPawns = this.setupWeaponPawns(gameBoard);

	    // deals cards to players and selects murderer cards
		ArrayList<Card> murdererCards = this.dealCards(playerCollection);
		
		return  Turn.getInstance(playerCollection, weaponPawns, gameBoard, murdererCards);
		
	}
	
	public int[][] setupGrid(String fileName){
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
		
		return grid;
	}
	
	private ArrayList<Card> setupCardDeck(){
		ArrayList<Card> cardDeck = new ArrayList<Card>();
		for (Suspect sus : Suspect.values())
		{
			cardDeck.add(new SuspectCard(sus));
		}
		for (Room ro : Room.values())
		{
			cardDeck.add(new RoomCard(ro));
		}
		for (Weapon wep : Weapon.values())
		{
			cardDeck.add(new WeaponCard(wep));
		}

		return cardDeck;	
	}
	
	public ArrayList<Card> dealCards(ArrayList<Player> playerCollection) {
		ArrayList<Card> cardDeck = this.setupCardDeck();
		int numPlayers = playerCollection.size();
		ArrayList<Card> murdererCards = new ArrayList<Card>();
		 
		// first pick the murderer, murder weapon and murder room
		Random rand = new Random();
		// remove random suspect card
		murdererCards.add(cardDeck.remove(rand.nextInt(Suspect.values().length-1)));
		// remove random room card
		murdererCards.add(cardDeck.remove(Suspect.values().length-1+rand.nextInt(Room.values().length-1)));
		// remove random weapon card
		murdererCards.add(cardDeck.remove(Suspect.values().length-1+Room.values().length-1+rand.nextInt(Weapon.values().length-1)));
		
		// deal remaining cards randomly to players
		Collections.shuffle(cardDeck);
		// remove a card and give it to next player until all out
		for(int numCards = cardDeck.size(), i=1; numCards>0; numCards--, i++) { 
			Player currentPlayer = playerCollection.get(i%(numPlayers));
			// remove a card from the deck
			Card dealtCard = cardDeck.remove(0);
			// give the card to the current player and add it to their notebook
			currentPlayer.giveCard(dealtCard);
			currentPlayer.getNotebook().addEvent("You have card: " + dealtCard.getName().toString());
		}
		// return the cards that were removed from the deck at the beginning
		return murdererCards;
	}
	
	public ArrayList<Player> setupPlayers(GameBoard gameBoard, Scanner sc){
		ArrayList<Suspect> suspectCollection = this.setupSuspectCollection();
		ArrayList<Room> roomCollection = this.setupRoomCollection();
		Collections.shuffle(roomCollection);
		boolean anotherPlayer = true;
		ArrayList<Player> playerCollection = new ArrayList<Player>();
		int numPlayers = 0;
		int numSuspects = 0;
		int suspectIndex = 0;
		int roomIndex = 0;
		int[] location = new int[2];
		
		// Loop to repeatedly ask for new players
		while (anotherPlayer && numPlayers<Suspect.values().length) {
			
			// Input player name and initialise player object
			System.out.println("Hello player " + (numPlayers+1) + '.');
			System.out.println("Welcome to the game!");
			
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
			// ensure that the number chosen is valid for number of remaining suspects
			if(suspectIndex < suspectCollection.size()) {
				System.out.println("You are suspect " + suspectCollection.get(suspectIndex));
				
				// Get location of room they're starting in
				location = gameBoard.getRoomLocation(roomCollection.get(roomIndex));
				
				// Create player with starting location and suspect type
				playerCollection.add(new Player(gameBoard, location, suspectCollection.get(suspectIndex), true));
				
				// Now that that suspect is in the game we remove it from consideration for other players
				suspectCollection.remove(suspectIndex);
				
				// Increment the number of players in the game now and room to place in
				numPlayers++;
				roomIndex++;
				
			} else System.out.println("Invalid selection");
			sc.nextLine();
			
			// Check if other players are present
			if(numPlayers<Suspect.values().length) {
				System.out.println("Would you like to add another player? (Y/N)");
				String str = sc.nextLine();
				
				if (Character.toUpperCase(str.charAt(0)) == 'Y') {
					anotherPlayer = true;
				}
				else if (numPlayers<2) {
					anotherPlayer = true;
					System.out.println("You must have a minimum of 2 players!");
				} else anotherPlayer = false;
			}
			
			// Clear the command line
			for(int i = 0; i < 999; i++) 
				System.out.println("\n");
			
		}
		
		// Next create inactive players for each of the remaining suspects
		for(Suspect sus: suspectCollection) {
			// Get location of room to start this pawn in
			location = gameBoard.getRoomLocation(roomCollection.get(roomIndex));
			
			// create an inactive player for each remaining suspect
			playerCollection.add(new Player(gameBoard, location, sus, false));
			
			// switch to next room to place in
			roomIndex++;
		}
		
		return playerCollection;
	}
	
	/**
	 * @param gameBoard
	 * @return
	 * Collection of all Weapon pawns placed in random rooms on the gameboard
	 */
	public ArrayList<WeaponPawn> setupWeaponPawns(GameBoard gameBoard) {
		
		ArrayList<WeaponPawn> pawnCollection = new ArrayList<WeaponPawn>();
		ArrayList<Room> rooms = setupRoomCollection();
		Collections.shuffle(rooms);
		int roomIndex=0;
		int[] location = new int[2];
		WeaponPawn weaponPawn;
		
		for(Weapon wp : Weapon.values()) 
		{
			location = gameBoard.getRoomLocation(rooms.get(roomIndex));
			weaponPawn = new WeaponPawn(gameBoard, location, wp);
			pawnCollection.add(weaponPawn);
			roomIndex++;
		}
		return pawnCollection;
	}


	private ArrayList<Room> setupRoomCollection() {

		ArrayList<Room> roomCollection = new ArrayList<Room>();

		for (Room ro : Room.values())
		{
			roomCollection.add(ro);
		}
		return roomCollection;
	}


	private ArrayList<Suspect> setupSuspectCollection() {
		
		ArrayList<Suspect> suspectCollection = new ArrayList<Suspect>();
		
		for (Suspect per : Suspect.values())
		{
			suspectCollection.add(per);
		}
		return suspectCollection;
	}
	
	
}
