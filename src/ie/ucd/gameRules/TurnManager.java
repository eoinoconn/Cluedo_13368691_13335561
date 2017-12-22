package ie.ucd.gameRules;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.gameEntities.Card;
import ie.ucd.gameEntities.GameBoard;
import ie.ucd.gameEntities.Player;
import ie.ucd.gameEntities.WeaponPawn;

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

	/**
	 * User is presented with options to:
	 * - Enter move mode
	 * - Check notebook 
	 * - Make a hypothesis
	 * - Make an accusation
	 * - Check Cards
	 * - End your turn
	 */
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
			int numMovesRemaining = currentPlayer.rollDice();
			
			// Inform player of their moves and location
			System.out.println("You have " + numMovesRemaining + " moves.");
			
			
			// Begin action loop where the player makes his decisions for their turn
			boolean playerTurnOver = false;
			while(!playerTurnOver && currentPlayer.isActive()) {
				
				// Print the gameboard for users convenience
				gameBoard.printBoard(playerIndex, playerCollection, weaponPawns);
				
				// Ask user which action they would like to perform
				System.out.println("Would you like to:\n" + "Enter move mode (M)\n" + "Check your notebook (N)\n" + "Make a hypothesis (H)\n" + "Make an accusation (A)\n" + "Check Cards (C)\n" + "End your turn (E)");
				String str = sc.nextLine();
				switch(Character.toUpperCase(str.charAt(0))) {
				case('M'):
					new MoveManager(currentPlayer, gameBoard, playerCollection, weaponPawns).moveMode(playerIndex, sc);;
					break;
				case('N'):
					new NotebookManager(currentPlayer.getNotebook(), sc).openNotebook(sc);
					break;
				case('H'):
					HypothesisManager HM = new HypothesisManager(gameBoard, playerCollection, weaponPawns, playerIndex, sc);
					if(HM.checkEligible()) {
						HM.makeHypothesis();
						HM.checkHypothesis();
					}
					break;
				case('C'):
					System.out.print(currentPlayer.lookAtHand());
					break;
				case('A'):
					AccusationManager AM = new AccusationManager(currentPlayer, playerCollection, playerIndex, murdererCards, sc);
					AM.makeAccusation();
					AM.checkAccusation();
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
