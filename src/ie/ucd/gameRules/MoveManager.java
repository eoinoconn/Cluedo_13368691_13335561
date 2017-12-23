package ie.ucd.gameRules;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.gameEntities.GameBoard;
import ie.ucd.gameEntities.Player;
import ie.ucd.gameEntities.Slot;
import ie.ucd.gameEntities.SuspectPawn;
import ie.ucd.gameEntities.WeaponPawn;

public class MoveManager{
	
	protected GameBoard gameBoard;
	protected ArrayList<Player> playerCollection;
	protected ArrayList<WeaponPawn> weaponPawns;
	protected Player currentPlayer;


	public MoveManager(Player currentPlayer, GameBoard gameBoard, ArrayList<Player> playerCollection, ArrayList<WeaponPawn> weaponPawns) {
		this.gameBoard = gameBoard;
		this.playerCollection = playerCollection;
		this.weaponPawns = weaponPawns;
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * Loop until moves used up or user finished move mode
	 * @param playerIndex
	 * @param sc
	 */
	public void moveMode(int playerIndex, Scanner sc) {
		
		// Enter move-mode contained in while loop
		boolean inMoveMode = true;
		while((currentPlayer.getMoves() > 0) & (inMoveMode)) {
			
			
			// Print gameboard
			gameBoard.printBoard(playerIndex, playerCollection, weaponPawns);
			
			// Tell the user how many moves they have left
			System.out.println("You have " + currentPlayer.getMoves() + " moves remaining");
			
			// Check where the player wants to move
			System.out.println("Where would you like to move? (Up/Down/Left/Right/Finish)");
			String str = sc.nextLine();
			
			// If user enters F, exit move mode
			if(Character.toUpperCase(str.charAt(0)) == 'F') {
				inMoveMode = false;

			}
			// Else perform move and check move occurred
			else{

				int e = this.makeMove(str.charAt(0), currentPlayer.getSuspectPawn());
				
				switch(e) {
				
				case(0):		// Player moves in a room, do not decrement moves
					
					break;
				
				case(1):		// Player moves in a corridor, decrement moves
				
					currentPlayer.moveMade();
					break;
					
				case(-1):		// Player attempt to make illegal move
					
					System.out.println("Sorry, you can't move there.");
					break;
					
				case(-2):		// Player enters unexpected input
					

					System.out.println("Input not recognised");
					break;
					
				default:		// Unexpected error
					System.exit(1);		//Error with Turn.makeMove();
					
				}
				

			}
			
			
			
		}
	}
	
	/**
	 * 
	 * @param direction
	 * @param suspectPawn
	 * @return
	 */
	private int makeMove(char direction, SuspectPawn suspectPawn) {
		int[] location = suspectPawn.getLocation();
		Slot thisSlot = gameBoard.getSlot(location);
		Slot[] options = thisSlot.getOptions();
		int opt;
		int dim = gameBoard.getDimensions();
		boolean atRoomWall, inCorridor, byPawn;
		boolean[] atOuterWall = new boolean[4];
		boolean[] inCorner = new boolean[4];
		
		for(int i=0; i<4; i++) {
			inCorner[i] = (thisSlot.getType()==4 && options[i].getType()==4 && thisSlot.getNumber()!=options[i].getNumber());
		}
		
		switch(direction) {
				case 'u':
					opt = 0;
					
					if(inCorner[0]) { // on top row
						if(inCorner[2]) { // in top left corner
							gameBoard.changePawnLocation(suspectPawn, dim-1, dim-1);
							return 0; // do not use up a move to move in secret passage
						}
						else if(inCorner[3]) { // in top right corner
							gameBoard.changePawnLocation(suspectPawn, dim-1, 0);
							return 0; // do not use up a move to move in secret passage
						}
					}
					break;
				case 'd':
					opt = 1;
					
					if(inCorner[1]) { // on bottom row
						if(inCorner[2]) { // in bottom left corner
							gameBoard.changePawnLocation(suspectPawn, 0, dim-1);
							return 0; // do not use up a move to move in secret passage
						}
						else if(inCorner[3]) { // in bottom right corner
							gameBoard.changePawnLocation(suspectPawn, 0, 0);
							return 0; // do not use up a move to move in secret passage
						}
					}
					break;
				case 'l':
					opt = 2;
					
					if(inCorner[2]) { // on left wall
						if(inCorner[0]) { // in top left corner
							gameBoard.changePawnLocation(suspectPawn, dim-1, dim-1);
							return 0; // do not use up a move to move in secret passage
						}
						else if(inCorner[1]) { // in bottom left corner
							gameBoard.changePawnLocation(suspectPawn, 0, dim-1);
							return 0; // do not use up a move to move in secret passage
						}
					}
					break;
				case 'r':
					opt = 3;
					
					if(inCorner[3]) { // on right wall
						if(inCorner[0]) { // in top right corner
							gameBoard.changePawnLocation(suspectPawn, 1, 0);
							return 0; // do not use up a move to move in secret passage
						}
						else if(inCorner[1]) { // in bottom right corner
							gameBoard.changePawnLocation(suspectPawn, 0, 0);
							return 0; // do not use up a move to move in secret passage
						}
					}
					break;
				default:
					return -2;				// return -2 if invalid option entered
				}
				
				atRoomWall = (thisSlot.getType()==1 && options[opt].getType()==3)||(thisSlot.getType()==3 && options[opt].getType()==1);
				inCorridor = options[opt].getType()<3||thisSlot.getType()==2;
				byPawn = options[opt].getHasPawn();
				for(int i=0; i<4; i++) {
					atOuterWall[i] = options[i].getType()==0;
				}
				
				if(!atOuterWall[opt] && !atRoomWall && !byPawn) { // not moving off board or into wall or into other pawn 
					if(direction=='u')
						gameBoard.changePawnLocation(suspectPawn, location[0]-1, location[1]);
					else if(direction=='d')
						gameBoard.changePawnLocation(suspectPawn, location[0]+1, location[1]);
					else if(direction=='l')
						gameBoard.changePawnLocation(suspectPawn, location[0], location[1]-1);
					else
						gameBoard.changePawnLocation(suspectPawn, location[0], location[1]+1);
					if(inCorridor) {
						return 1; 		// return 1 if used up a move
					}
					return 0;			// return 0 if did not use up a move
				}
				return -1;				// return -1 if could not move
		
			}
	
	}
