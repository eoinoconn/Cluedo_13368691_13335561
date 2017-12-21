package ie.ucd.gameRules;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.items.Card;
import ie.ucd.items.GameBoard;
import ie.ucd.items.Player;
import ie.ucd.items.WeaponPawn;

public class TurnManager {

	private int playerIndex;
	private GameBoard gameBoard; 
	private ArrayList<Card> murdererCards;
	private ArrayList<Player> playerCollection; 
	private Scanner sc;
	private ArrayList<WeaponPawn> weaponPawns;
	
	public TurnManager(int playerIndex, GameBoard gameBoard, ArrayList<Player> playerCollection, ArrayList<WeaponPawn> weaponPawns, ArrayList<Card> murdererCards, Scanner sc) {
		this.playerIndex = playerIndex;
		this.gameBoard = gameBoard; 
		this.murdererCards = murdererCards;
		this.playerCollection = playerCollection;
		this.sc = sc;
		this.weaponPawns = weaponPawns;
		startTurn();
	}

	
private void startTurn() {
		
		
		// Select current turns player
		Player currentPlayer = playerCollection.get(playerIndex);
		
		if(currentPlayer.isActive()) {
			currentPlayer.setHypMade(false);
			int whoseGo = currentPlayer.playerNumber();
			

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
			while(!playerTurnOver && currentPlayer.isActive()) {
				
				// Print the gameboard for users convenience
				gameBoard.printBoard(playerIndex, playerCollection, weaponPawns);
				
				// Ask user which action they would like to perform
				System.out.println("Would you like to:\n" + "Enter move mode (M)\n" + "Check your notebook (N)\n" + "Make a hypothesis (H)\n" + "Make an accusation (A)\n" + "End your turn (E)");
				String str = sc.nextLine();
				switch(Character.toUpperCase(str.charAt(0))) {
				case('M'):
					new MoveManager(currentPlayer, gameBoard, playerCollection, weaponPawns, playerIndex, sc);
					break;
				case('N'):
					new NotebookManager(currentPlayer.getNotebook(), sc);
					break;
				case('H'):

					new HypothesisManager(gameBoard, playerCollection, weaponPawns, playerIndex, sc);
					break;
					
				case('A'):
					new AccusationManager(currentPlayer, gameBoard, playerCollection, playerIndex, murdererCards, sc);
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
	}
}
