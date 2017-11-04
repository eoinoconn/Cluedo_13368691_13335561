package ie.ucd.items;

import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		String fileName = "GameBoard1.csv";	
		File file = new File(fileName);
	    List<List<String>> lines = new ArrayList<>();
	    Scanner inputStream;
	    int[][] grid = new int[9][9];
	    
	    
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
		
	    GameBoard gameBoard = new GameBoard(grid);
	    
	    // TODO randomly assign weapon pawns
		
		
		// TODO This could be replaced at a later date with a facade		
		// Create instances of game cards
		// Store game cards in cardDeck
		ArrayList<Card> cardDeck = new ArrayList<Card>();
		ArrayList<Suspect> suspectCollection = new ArrayList<Suspect>();
		ArrayList<Room> roomCollection = new ArrayList<Room>();
		for (Suspect per : Suspect.values())
		{
			cardDeck.add(new SuspectCard(per));
			suspectCollection.add(per);
		}
		for (Weapon wep : Weapon.values())
		{
			cardDeck.add(new WeaponCard(wep));
		}
		for (Room ro : Room.values())
		{
			cardDeck.add(new RoomCard());
			roomCollection.add(ro);
		}
		
		Collections.shuffle(roomCollection);


		
		System.out.println("Welcome to Cluedo!! By eoin and Andy.");
		
		// Establish who's playing
		Scanner sc = new Scanner(System.in);
		boolean anotherPlayer = false;
		ArrayList<Player> playerCollection = new ArrayList<Player>();
		int players = 0;
		int susnum = 0;
		int suspectIndex = 0;
		int[] playerLocation = new int[2];
		
		// Loop to repeatedly ask for new players
		do {
			
			// Input player name and initialise player object
			System.out.println("Hello player " + (++players) + '.');
			System.out.println("Please enter your name:");
			// TODO catch exceptions
			String str = sc.next();
			sc.nextLine();
			System.out.println("Welcome to the game " + str + '!');
			
			// See who they'd like to be
			System.out.println("Who would you like to play as?");
			//print out all the remaining suspects
			susnum = 0;
			for(Suspect sus: suspectCollection) {
				System.out.println((susnum+1) + " " + sus.toString());
				susnum++;
			}
			
			// Take their choice of suspect
			System.out.println("Enter a number between 1 & " + susnum + ":");
			suspectIndex = sc.nextInt()-1;
			System.out.println(str + ", you are player " + suspectCollection.get(suspectIndex));
			
			// Get location of room they're starting in
			playerLocation = gameBoard.getRoomLocation(roomCollection.get(players - 1));
			
			// Create player with starting location and suspect type
			playerCollection.add(new Player(playerLocation[0], playerLocation[1], suspectCollection.get(suspectIndex)));
			
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
		sc.close();
	}
}