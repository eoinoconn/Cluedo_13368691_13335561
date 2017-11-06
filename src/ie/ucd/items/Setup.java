package ie.ucd.items;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Setup {

	private static Setup uniqueInstance = null;
	
	public static Setup getInstance() {
		if(uniqueInstance == null)
			uniqueInstance = new Setup();
		return uniqueInstance;
	}

	
	public Object setup() {
		return null;
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
	
	public ArrayList<Card> setupCardDeck(){
		ArrayList<Card> cardDeck = new ArrayList<Card>();
		for (Suspect per : Suspect.values())
		{
			cardDeck.add(new SuspectCard(per));
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
	
	public ArrayList<Player> setupPlayers(GameBoard gameBoard, Scanner sc){
		ArrayList<Suspect> suspectCollection = this.setupSuspectCollection();
		ArrayList<Room>roomCollection = this.setupRoomCollection();
		
		boolean anotherPlayer = false;
		ArrayList<Player> playerCollection = new ArrayList<Player>();
		int numPlayers = 0;
		int numSuspects = 0;
		int suspectIndex = 0;
		int[] location = new int[2];
		
		// Loop to repeatedly ask for new players
		do {
			
			// Input player name and initialise player object
			System.out.println("Hello player " + (++numPlayers) + '.');
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
			System.out.println("You are suspect " + suspectCollection.get(suspectIndex));
			
			// Get location of room they're starting in
			location = gameBoard.getRoomLocation(roomCollection.get(numPlayers - 1));
			
			// Create player with starting location and suspect type
			playerCollection.add(new Player(location[0], location[1], suspectCollection.get(suspectIndex)));
			
			// Now that that suspect is in the game we remove it from consideration for other players
			suspectCollection.remove(suspectIndex);
			
			// Check if other players are present
			sc.nextLine();
			System.out.println("Would you like to add another player? (Y/N)");
			String str = sc.nextLine();
			if (Character.toUpperCase(str.charAt(0)) == 'Y') {
				anotherPlayer = true;
			} else anotherPlayer = false;
			
		} while (anotherPlayer);
		return playerCollection;
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
