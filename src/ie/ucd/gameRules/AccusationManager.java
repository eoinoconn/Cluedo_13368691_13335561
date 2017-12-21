package ie.ucd.gameRules;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.items.Card;
import ie.ucd.items.GameBoard;
import ie.ucd.items.Player;
import ie.ucd.items.Room;
import ie.ucd.items.Slot;
import ie.ucd.items.Suspect;
import ie.ucd.items.SuspectPawn;
import ie.ucd.items.Weapon;

public class AccusationManager {

	private GameBoard gameBoard;
	private ArrayList<Player> playerCollection; 
	private Player currentPlayer;
	private Slot currentSlot;
	private ArrayList<Card> murdererCards;
	private int playerIndex;
	private Scanner sc;
	
	public AccusationManager(Player currentPlayer, GameBoard gameBoard, ArrayList<Player> playerCollection, int playerIndex, ArrayList<Card> murdererCards, Scanner sc) {
		this.currentPlayer = currentPlayer;
		this.gameBoard = gameBoard;
		this.playerCollection = playerCollection;
		this.playerIndex = playerIndex;
		this.murdererCards = murdererCards;
		this.sc = sc;
		makeAccusation();
	}
	
	
	public void makeAccusation() {
		
		
		int whoseGo = currentPlayer.playerNumber();
		int winner = 0;
		int numPlayers;
		
		SuspectPawn sp = currentPlayer.getSuspectPawn();
		currentSlot = this.gameBoard.getSlot(sp.getLocation());
		
		// check that player is in a room
		if(currentSlot.getType()!=3) {

			System.out.println("You must be in a room to make an accusation!");
			return;
		}
		
		else {
			
			// get the current room and print it to the screen
			int roomIndex = currentSlot.getNumber();
			Room murderRoom = Room.values()[roomIndex-1];
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
				winner = whoseGo;
				
				// Clear command line
				for(int i = 0; i < 999; i++) 
					System.out.println("\n");
				
				// Print success message to screen
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("Congratualtions!! Youve caught the murderer!");
				System.out.println(murderer.toString() + " committed the murder, with the " + murderWeapon.toString() + " in the "
						+ murderRoom.toString() + '.');
				System.out.println("GAME OVER - Player " + winner + " Wins!!!");
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
				System.out.println("Sorry player " + whoseGo + ", you are wrong and must be removed from the game\n");
				
				
				// Remove Player from the game by making active false
				playerCollection.get(playerIndex).removeFromGame();
				
				// find out the number of active players
				numPlayers = 0;
				for(Player p : playerCollection) {
					if(p.isActive()) {
						winner = p.playerNumber();
						numPlayers++;
					}
				}
				
				if(numPlayers==1) {
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("GAME OVER - Player " + winner + " Wins By Default!");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					
					// close application
					System.exit(0);
				}
				else {
					System.out.println("Press return to continue\n");
					sc.nextLine();
				}
			}

		}
	}
	
	private Suspect getSuspectChoice(Scanner sc) {
		//print out all the remaining suspects and get take the players choices
		int i = 1;
		Suspect sus;
		for(Player p: playerCollection) {
			sus = p.getSuspectPawn().getName();
			System.out.println(i + " " + sus.toString());
			i++;
		}
		int suspectIndex = sc.nextInt() - 1;
		return playerCollection.get(suspectIndex).getSuspectPawn().getName();
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
