package ie.ucd.items;

import java.util.Scanner;

public class Player {
	
	private PersonPawn character;
	private int id;
	
	public Player() {
		this.id = getNextId();
	}
	
	public void makeMove(GameBoard board) {
		int[] options = board.getOptions(character);
		System.out.println("Current position is " + options[0]);
		System.out.println("Above current position is " + options[1]);
		System.out.println("Below current position is " + options[2]);
		System.out.println("Left of current position is " + options[3]);
		System.out.println("Right of current position is " + options[4]);
		System.out.println("Select Direction: 'u' for up, 'd' for down, 'l' for left, 'r' for right");
		Scanner scanner = new Scanner(System.in);
		String movement = scanner.nextLine();
		switch(movement) {
		case "up":		character.setLocation(character.getLocation()[0], character.getLocation()[1]+1);
						break;
		case "down":	character.setLocation(character.getLocation()[0], character.getLocation()[1]-1);
						break;
		case "left":	character.setLocation(character.getLocation()[0]-1, character.getLocation()[1]);
						break;
		case "right":	character.setLocation(character.getLocation()[0]+1, character.getLocation()[1]);
						break;
		}
	}
	
	public int getId() { 
	    return id; 
	}
	
	protected static int index = 0; 
	
	protected static int getNextId() { 
	    return ++index; 
	}
}