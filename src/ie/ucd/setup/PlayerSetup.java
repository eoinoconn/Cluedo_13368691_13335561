package ie.ucd.setup;

import java.util.ArrayList;
import java.util.Collections;

import ie.ucd.gameEntities.Player;
import ie.ucd.gameEntities.Room;
import ie.ucd.gameEntities.Suspect;
import ie.ucd.gameEntities.SuspectPawn;

public class PlayerSetup extends Setup{

	
	private ArrayList<Room> roomCollection;
	private ArrayList<Suspect> suspectCollection;
	private int roomIndex;
	
	public PlayerSetup() {
		numPlayers = 0;
		
		playerCollection = new ArrayList<Player>();
		suspectCollection = this.setupSuspectCollection();
		roomCollection = this.setupRoomCollection();
		Collections.shuffle(roomCollection);
		roomIndex = 0;
	}
	
	
	public void setupPlayers(){
		

		Collections.shuffle(roomCollection);
		
		boolean anotherPlayer = true;
		
		// Loop to repeatedly ask for new players
		while (anotherPlayer && numPlayers<Suspect.values().length) {

			asWho();
			
			// Check if other players are present
			if(numPlayers<2) {
				anotherPlayer = true;
				System.out.println("Press return to continue");
				sc.nextLine();
			}
			else if(numPlayers<Suspect.values().length) {
				System.out.println("Would you like to add another player? (Y/N)");
				String str = sc.nextLine();
				
				if (Character.toUpperCase(str.charAt(0)) == 'Y') {
					anotherPlayer = true;
				}
				else anotherPlayer = false;
			}
			
			// Clear the command line
			for(int i = 0; i < 999; i++) 
				System.out.println("\n");
			
		}
		
		// Fill the rest of the collection with inactive players
		inactivePlayers();
		
	}
	
	private void asWho() {
		
		
		int[] location = new int[2];
		int suspectIndex = 0;
		
		
		// Input player name and initialise player object
		System.out.println("Hello player " + (numPlayers+1) + '.');
		System.out.println("Welcome to the game!");
		
		// See who they'd like to be
		System.out.println("Who would you like to play as?");
		suspectIndex = chooseSuspect();
		
		// ensure that the number chosen is valid for number of remaining suspects
		if(suspectIndex < suspectCollection.size()) {
			System.out.println("You are suspect " + suspectCollection.get(suspectIndex));
			
			// Get location of room they're starting in
			location = gameBoard.getRoomLocation(roomCollection.get(roomIndex));
			
			// Create player with starting location and suspect type
			playerCollection.add(new Player(new SuspectPawn(gameBoard, location, suspectCollection.get(suspectIndex)), true));
			
			// Now that that suspect is in the game we remove it from consideration for other players
			suspectCollection.remove(suspectIndex);
			
			// Increment the number of players in the game now and room to place in
			numPlayers++;
			roomIndex++;
			
		} else System.out.println("Invalid selection");
		sc.nextLine();
	}
	
	private void inactivePlayers() {
		int[] location = new int[2];
	// int roomIndex = 0;
		
		// Next create inactive players for each of the remaining suspects
		for(Suspect sus: suspectCollection) {
			// Get location of room to start this pawn in
			location = gameBoard.getRoomLocation(roomCollection.get(roomIndex));
			
			// create an inactive player for each remaining suspect
			playerCollection.add(new Player(new SuspectPawn(gameBoard, location, sus), false));
			
			// switch to next room to place in
			roomIndex++;
		}
	}
	
	private int chooseSuspect() {
		//print out all the remaining suspects
		int numSuspects = 0;
		for(Suspect sus: suspectCollection) {
			System.out.println((numSuspects+1) + " " + sus.toString());
			numSuspects++;
		}
		
		// Take their choice of suspect
		System.out.println("Enter a number between 1 & " + numSuspects + ":");
		return sc.nextInt() - 1;
	}
	
}
