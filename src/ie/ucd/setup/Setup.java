package ie.ucd.setup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import ie.ucd.items.Card;
import ie.ucd.items.GameBoard;
import ie.ucd.items.Player;
import ie.ucd.items.Room;
import ie.ucd.items.RoomCard;
import ie.ucd.items.Suspect;
import ie.ucd.items.SuspectCard;
import ie.ucd.items.Turn;
import ie.ucd.items.Weapon;
import ie.ucd.items.WeaponCard;
import ie.ucd.items.WeaponPawn;

import java.util.Random;


public class Setup {

	protected static Scanner sc;
	private static Setup uniqueInstance = null;
	
	public static Setup getInstance() {
		if(uniqueInstance == null)
			uniqueInstance = new Setup();
		return uniqueInstance;
	}

	
	public Turn setupGame(String fileName, Scanner scanner) {
		
		Setup.sc = scanner;
		
		// push text to bottom of command line
		for(int i = 0; i < 999; i++) 
			System.out.println("\n");
				
		System.out.println("Welcome to Cluedo!! By Eoin and Andy.");
		
		// Call setupGrid to create grid
		int[][] grid = this.setupGrid(fileName);
		
		// Create gameBoard instance.
	    GameBoard gameBoard = GameBoard.getInstance(grid);
	    
		// Stores player instances
	    PlayerSetup setupPlayers = new PlayerSetup();
		ArrayList<Player> playerCollection = setupPlayers.setupPlayers(gameBoard);
		
		// Stores Weapon Pawns
		SetupWeaponPawns pawnSetup = new SetupWeaponPawns();
		ArrayList<WeaponPawn> weaponPawns = pawnSetup.setupWeaponPawns(gameBoard);

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
		int numPlayers;
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
		
		// find out the number of active players
		numPlayers = 0;
		for(Player p : playerCollection) {
			if(p.isActive())
				numPlayers++;
		}
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
	


	protected ArrayList<Room> setupRoomCollection() {

		ArrayList<Room> roomCollection = new ArrayList<Room>();

		for (Room ro : Room.values())
		{
			roomCollection.add(ro);
		}
		return roomCollection;
	}


	protected ArrayList<Suspect> setupSuspectCollection() {
		
		ArrayList<Suspect> suspectCollection = new ArrayList<Suspect>();
		
		for (Suspect per : Suspect.values())
		{
			suspectCollection.add(per);
		}
		return suspectCollection;
	}
	
	
}
