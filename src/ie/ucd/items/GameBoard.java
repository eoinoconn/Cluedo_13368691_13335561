package ie.ucd.items;

import java.util.ArrayList;

public class GameBoard {

	private static GameBoard uniqueInstance = null;
	private static final int DIMENSIONS = 25;
	private Slot[][] grid = new Slot[DIMENSIONS][DIMENSIONS];
	
	public static GameBoard getInstance(int[][] grid) {
		if(uniqueInstance == null)
			uniqueInstance = new GameBoard(grid);
		return uniqueInstance;
	}
	
	private GameBoard(int[][] grid) {
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	        	if((i==0&&j==0)||(i==0&&j==DIMENSIONS-1)||(i==DIMENSIONS-1&&j==0)||(i==DIMENSIONS-1&&j==DIMENSIONS-1)) {
	        		this.grid[i][j] = new Slot();
	        		this.grid[i][j].setType(4); // 4 if at secret passage
        			this.grid[i][j].setNumber(grid[i][j] % 10);
	        	}
	        	else if(grid[i][j] % 10 > 0) {
	        		if(grid[i][j]<10) {
	        			this.grid[i][j] = new Slot();
	        			this.grid[i][j].setType(2); // 2 if at doorway
	        			this.grid[i][j].setNumber(grid[i][j]);
	        		}else {
	        			this.grid[i][j] = new Slot();
	        			this.grid[i][j].setType(3); // 3 if in room
	        			this.grid[i][j].setNumber(grid[i][j] % 10);
	        		}
	        	}
	        	else if(grid[i][j]==10){
	        		this.grid[i][j] = new Slot();
	        		this.grid[i][j].setType(1); // 1 if in corridor
	        		this.grid[i][j].setNumber(grid[i][j] % 10);
	        	}
	        	else {
	        		this.grid[i][j] = new Slot();
	        	}
	        }
		}
	}
	
	// Method to return the current grid square and the numbers corresponding to the 
	public int[] getOptions(SuspectPawn pawn) {
		int[] location = pawn.getLocation();
		int[] options = new int[5]; // some array of numbers representing options
		options[0] = grid[location[0]][location[1]].getType(); //current position
		if((location[0]-1)>=0) { // ensure space above is not out of bounds
			options[1] = grid[location[0]-1][location[1]].getType();
		}
		if((location[0]+1)<DIMENSIONS) { // ensure space below is not out of bounds
			options[2] = grid[location[0]+1][location[1]].getType();
		}
		if((location[1]-1)>=0) { // ensure space to the left is not out of bounds
			options[3] = grid[location[0]][location[1]-1].getType();
		}
		if((location[1]+1)<DIMENSIONS) { // ensure space to the right is not out of bounds
			options[4] = grid[location[0]][location[1]+1].getType();
		}
		return options;
	}
	
	public int[] getRoomLocation(Room room) {
		int[] location = new int[2];
		// check only the space not adjacent to the edge of the board as there are never doors
		for (int i = 1; i < DIMENSIONS-1; i++) {
	        for (int j = 1; j < DIMENSIONS-1; j++) {
	        	if (grid[i][j].getNumber()==room.ordinal()+1 && (grid[i+1][j].getType()==2 || grid[i-1][j].getType()==2 || grid[i][j+1].getType()==2 || grid[i][j-1].getType()==2)) {
	                // if in room adjacent to doorway
	            	location[0] = i;
	                location[1] = j;
	            	return location;
	            }
	        }
		}
		return location; // will return first grid square if no door found
	}
	
	public Slot getSlot(int[] location) {
		return grid[location[0]][location[1]];
	}
	
	public void printBoard(int currentPlayerId, ArrayList<Player> playerCollection) {
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	        	int id = 1;
	        	for(Player p: playerCollection) {
	        		SuspectPawn sp = p.getSuspectPawn();
	        		// if a player is at this grid square, print their id or @ symbol
	        		if(i==sp.getLocation()[0] && j==sp.getLocation()[1]) {
	        			if(id==currentPlayerId+1) {
	        				System.out.print("[@] "); // '@' = current player's pawn
	        			}
	        			else {
	        				System.out.print("[" + id + "] "); // id number = this player's pawn
	        			}
	        			break;
	        		}
	        		id++;
	        	}
	        	if(id<=playerCollection.size()) {
	        	}
	        	else if(grid[i][j].getType()==1) {
	        		System.out.printf(" '  "); // corridor
	        	}
	        	else if(grid[i][j].getType()==2||grid[i][j].getType()==4) {
					System.out.printf(" #  "); // doorway or secret passage
	        	}
	        	else if(grid[i][j].getType()==3) {
	        		System.out.printf(" " + grid[i][j].getNumber() + "  "); // room
	        	}
				else {
					System.out.printf(" X  "); // out of bounds
				}
	        }
	        // print a legend of room names
	        if(i<Room.values().length) {
	        	System.out.print("\t" + (i+1) + " = " + Room.values()[i]);
	        }
	        System.out.print("\n\n");
		}
		System.out.println("[] = pawn location, ' = corridor, room number = room, # = doorway\n");
	}
	
	public int getDimensions() {
		return DIMENSIONS;
	}
}
