package ie.ucd.gameRules;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.gameEntities.Card;
import ie.ucd.gameEntities.GameBoard;
import ie.ucd.gameEntities.Player;
import ie.ucd.gameEntities.Room;
import ie.ucd.gameEntities.Slot;
import ie.ucd.gameEntities.Suspect;
import ie.ucd.gameEntities.SuspectPawn;
import ie.ucd.gameEntities.Weapon;
import ie.ucd.gameEntities.WeaponPawn;

public class HypothesisManager {

	private GameBoard gameBoard;
	private ArrayList<Player> playerCollection; 
	private Player currentPlayer;
	private ArrayList<WeaponPawn> weaponPawns;
	private Slot currentSlot;
	private int playerIndex;
	private Scanner sc;
	private Suspect murderer;
	private Weapon murderWeapon;
	private Room murderRoom;
	
	public HypothesisManager(GameBoard gameBoard, ArrayList<Player> playerCollection, ArrayList<WeaponPawn> weaponPawns, int playerIndex, Scanner sc) {
		this.gameBoard = gameBoard;
		this.playerCollection = playerCollection;
		this.weaponPawns = weaponPawns;
		this.playerIndex = playerIndex;
		this.sc = sc;
	}

	/**
	 * Takes in the user guesses and move the related pawns
	 */
	public void makeHypothesis() {
		
		SuspectPawn sp = currentPlayer.getSuspectPawn();

		int[] location;
			
		// get the current room and print it to the screen
		int roomIndex = currentSlot.getNumber();
		murderRoom = Room.values()[roomIndex-1];
		System.out.println("You are currently in the " + murderRoom.toString());
		
		System.out.println("Who do you think is the murderer?");
		murderer = this.getSuspectChoice(sc);

		System.out.println("With which weapon?");
		murderWeapon = this.getMurderWeapon(sc);
	
		// Next move the involved suspect and weapon pawns to the room
		
		// get an available slot in the murder room
		location = gameBoard.getRoomLocation(murderRoom);
		// find the player and move their pawn to the murder room
		for(Player p : playerCollection) {
			sp = p.getSuspectPawn();
			if(sp.getName()==murderer) {
				gameBoard.changePawnLocation(sp, location[0], location[1]);
			}
		}
		
		// get an available slot in the murder room
		location = gameBoard.getRoomLocation(murderRoom);
		// find the weapon pawn and move it to the murder room
		for(WeaponPawn wp : weaponPawns) {
			if(wp.getName()==murderWeapon) {
				gameBoard.changePawnLocation(sp, location[0], location[1]);
			}
		}
	}
	
	/**
	 * Checks all players' cards for matched with the guesses
	 */
	public void checkHypothesis() {
		
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
		}
		// stop player from making another hypothesis on this turn
		currentPlayer.setHypMade(true);
		sc.nextLine();
		
	}

	/**
	 * 
	 * @param sc
	 * scanner to read in the user's choice
	 * @return
	 * the suspect being hypothesised
	 */
	private Suspect getSuspectChoice(Scanner sc) {
		//print out all the remaining suspects and get take the players choices
		int i = 1;
		Suspect sus;
		for(Player p: playerCollection) {
			sus = p.getSuspectPawn().getName();
			System.out.println(i + " " + sus.toString());
			i++;
		}
		
		String str;
		int someInt = 0;
		boolean isInt = false;
		while(!isInt) {
			str = sc.nextLine();
			try {
			    someInt = Integer.parseInt(str);
			    isInt = true;
			  } catch (NumberFormatException e) {
				  System.out.println("\nThis is not a valid entry, try again");
				  isInt = false;
			  }
		}
		int suspectIndex = someInt - 1;
		return playerCollection.get(suspectIndex).getSuspectPawn().getName();
	}
	
	/**
	 * 
	 * @param sc
	 * scanner to read in the user's choice
	 * @return
	 * the murder weapon being hypothesised
	 */
	private Weapon getMurderWeapon(Scanner sc) {
		int i = 1;
		for(Weapon weap: Weapon.values()) {
			System.out.println(i + " " + weap.toString());
			i++;
		}
		
		String str;
		int someInt = 0;
		boolean isInt = false;
		while(!isInt) {
			str = sc.nextLine();
			try {
			    someInt = Integer.parseInt(str);
			    isInt = true;
			  } catch (NumberFormatException e) {
				  System.out.println("\nThis is not a valid entry, try again");
				  isInt = false;
			  }
		}
		int weaponIndex = someInt - 1;
		return Weapon.values()[weaponIndex];
	}
	
	/**
	 * 
	 * @return true if the current player is in a room and has not already made a hypothesis, else false
	 */
	public boolean checkEligible() {
		
		currentPlayer = playerCollection.get(playerIndex);
		SuspectPawn sp = currentPlayer.getSuspectPawn();
		currentSlot = gameBoard.getSlot(sp.getLocation());
		
		if(currentSlot.getType()!=3) {

			System.out.println("You must be in a room to make a hypothesis!");
			return false;
		}
		else if(currentPlayer.getHypMade()) {
			
			System.out.println("You cannot make another hypothesis until your next turn!");
		return false;
		}
		else
			return true;
	}
	
}
