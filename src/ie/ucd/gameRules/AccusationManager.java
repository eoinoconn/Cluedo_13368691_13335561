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

public class AccusationManager {

	private GameBoard gameBoard;
	private ArrayList<Player> playerCollection; 
	private Player currentPlayer;
	private Slot currentSlot;
	private ArrayList<Card> murdererCards;
	private int playerIndex;
	private Scanner sc;
	private Suspect murderer;
	private Weapon murderWeapon;
	private Room murderRoom;
	
	/**
	 * 
	 * @param currentPlayer the one making the accusation
	 * @param gameBoard to find out which room the accusation is being made in
	 * @param playerCollection the collection of all players (active and inactive) to provide a list of suspects
	 * @param playerIndex position of current player within the player collection
	 * @param murdererCards the cards of the actual murder scenario to check against
	 * @param sc scanner to take in user inputs
	 */
	public AccusationManager(Player currentPlayer, GameBoard gameBoard, ArrayList<Player> playerCollection, int playerIndex, ArrayList<Card> murdererCards, Scanner sc) {
		this.currentPlayer = currentPlayer;
		this.gameBoard = gameBoard;
		this.playerCollection = playerCollection;
		this.playerIndex = playerIndex;
		this.murdererCards = murdererCards;
		this.sc = sc;
		makeAccusation();
	}
	
	/**
	 * Take user inputs of guesses
	 */
	public void makeAccusation() {
		
		SuspectPawn sp = currentPlayer.getSuspectPawn();
		currentSlot = this.gameBoard.getSlot(sp.getLocation());
		
		// check that player is in a room
		if(currentSlot.getType()!=3) {

			System.out.println("You must be in a room to make an accusation!");
			return;
		}
		
		else {
			
			// Take suspect input
			System.out.println("Where do you believe the murder took place?");
			murderRoom = this.getRoomChoice(sc);
			
			// Take suspect input
			System.out.println("Who do you believe is the murderer?");
			murderer = this.getSuspectChoice(sc);
			
			// Take weapon input
			System.out.println("With which weapon?");
			murderWeapon = this.getMurderWeapon(sc);
		}
	}
	
	/**
	 * Compare the accused to the actual murder scenario
	 */
	public void checkAccusation() {
		
		int whoseGo = currentPlayer.playerNumber();
		int winner = 0;
		int numPlayers;
		boolean suspectCorrect = murderer == murdererCards.get(0).getName();
		boolean roomCorrect = murderRoom == murdererCards.get(1).getName();
		boolean weaponCorrect = murderWeapon == murdererCards.get(2).getName();
		
		
		// If the accusation is correct
		if(suspectCorrect && roomCorrect && weaponCorrect) {
			
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
		
		
		else { // incorrect Accusation
			
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
			
			
			// If only one player now remains, they win by default
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
	
	/**
	 * 
	 * @param sc
	 * scanner to read in the user's choice
	 * @return
	 * the murder weapon being accused
	 */
	private Room getRoomChoice(Scanner sc) {
		int i = 1;
		for(Room rm: Room.values()) {
			System.out.println(i + " " + rm.toString());
			i++;
		}
		int weaponIndex = sc.nextInt() - 1;
		return Room.values()[weaponIndex];
	}
	
	/**
	 * 
	 * @param sc
	 * scanner to read in the user's choice
	 * @return
	 * the suspect being accused
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
		int suspectIndex = sc.nextInt() - 1;
		return playerCollection.get(suspectIndex).getSuspectPawn().getName();
	}
	
	/**
	 * 
	 * @param sc
	 * scanner to read in the user's choice
	 * @return
	 * the murder weapon being accused
	 */
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
