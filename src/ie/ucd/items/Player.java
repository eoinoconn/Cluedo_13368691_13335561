package ie.ucd.items;

import java.util.Scanner;

public class Player {
	
	private SuspectPawn character;
	private int moves;
	
	public Player(Suspect name) {
		this.character = new SuspectPawn(0, 0, name); //place pawn with specified name in start location
		moves = 0;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	public boolean makeMove(GameBoard board) {
		int[] options = board.getOptions(character);
		System.out.println("\t" + options[1] + "\n" + options[3] + "\t" + options[0] + "\t" + options[4] +"\n" + "\t" + options[2]);
		System.out.println("Select Direction: 'u' for up, 'd' for down, 'l' for left, 'r' for right");
		
		Scanner sc = new Scanner(System.in);
		String movement = sc.nextLine();
		sc.close();
		switch(movement) {
		case "u":	
			if(options[1]>0) {
				character.setLocation(character.getLocation()[0], character.getLocation()[1]-1);
				if(options[1]<10) { // do not decrement moves if in a room
					this.moves--;
				}
				return true;
			}
			return false;
		case "d":
			if(options[2]>0) {
				character.setLocation(character.getLocation()[0], character.getLocation()[1]+1);
				if(options[2]<10) { // do not decrement moves if in a room
					this.moves--;
				}
				return true;
			}
			return false;
		case "l":
			if(options[3]>0) {
				character.setLocation(character.getLocation()[0]-1, character.getLocation()[1]);
				if(options[3]<10) { // do not decrement moves if in a room
					this.moves--;
				}
				return true;
			}
			return false;
		case "r":
			if(options[4]>0) {
				character.setLocation(character.getLocation()[0]+1, character.getLocation()[1]);
				if(options[4]<10) { // do not decrement moves if in a room
					this.moves--;
				}
				return true;
			}
			return false;
		default:
			return false;
				
		}

	}
	
}