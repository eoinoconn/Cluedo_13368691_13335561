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
	
	private GameBoard(int[][] intGrid) {
		
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	        	int[] options = new int[5];
	        	int slotNumber = intGrid[i][j] % 10;
	        	for (int option = 0; option < 5 ; option++) {
	        		int r, c;
	        		if(option==0) {							// current slot
		        		r = i;
		        		c = j;
		        	}
	        		else if(option==1 && i>0) {				// up
		        		r = i-1;
		        		c = j;
		        	}
	        		else if(option==2 && i<DIMENSIONS-1) {	// down
		        		r = i+1;
		        		c = j;
		        	}
	        		else if(option==3 && j>0) {				// left
		        		r = i;
		        		c = j-1;
		        	}
	        		else if(option==4 && j<DIMENSIONS-1){	// right
		        		r = i;
		        		c = j+1;
		        	}
	        		// leave this option as 0 if not within limits
	        		else continue;
	        		
		        	if((r==0&&c==0)||(r==0&&c==DIMENSIONS-1)||(r==DIMENSIONS-1&&c==0)||(r==DIMENSIONS-1&&c==DIMENSIONS-1)) {
		        		// type 4 if at a secret passage
		        		options[option] = 4;
		        	}
		        	else if(intGrid[i][j] % 10 > 0) {
		        		if(intGrid[r][c]<10) {
		        			// type 2 if at doorway
		        			options[option] = 2;
		        		}
		        		else {
		        			// type 3 if in a room
		        			options[option] = 3;
		        		}
		        	}
		        	else if(intGrid[r][c]==10){
		        		// type 1 if in a corridor
		        		options[option] = 1;
		        	}
		        	else {
		        		// type 0 if out of bounds
		        		options[option] = 0;
		        	}
	        	}
	        	this.grid[i][j] = new Slot(options, slotNumber);
	        }
		}
		
	}
	
//  	// Method to return the current grid square and the numbers corresponding to the 
//	public int[] getOptions(SuspectPawn pawn) {
//		int[] location = pawn.getLocation();
//		int[] options = new int[5]; // some array of numbers representing options
//		options[0] = grid[location[1]][location[0]].getType(); //current position
//		if((location[1]-1)>=0) { // ensure space above is not out of bounds
//			options[1] = grid[location[1]-1][location[0]].getType();
//		}
//		if((location[1]+1)<DIMENSIONS) { // ensure space below is not out of bounds
//			options[2] = grid[location[1]+1][location[0]].getType();
//		}
//		if((location[0]-1)>=0) { // ensure space to the left is not out of bounds
//			options[3] = grid[location[1]][location[0]-1].getType();
//		}
//		if((location[0]+1)<DIMENSIONS) { // ensure space to the right is not out of bounds
//			options[4] = grid[location[1]][location[0]+1].getType();
//		}
//		return options;
//	}
//	
	public int[] getRoomLocation(Room room) {
		int[] location = new int[2];
		// check only the space not adjacent to the edge of the board as there are never doors
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	        	int[] options = grid[i][j].getOptions();
	        	int roomNum = grid[i][j].getNumber();
	        	if (roomNum==room.ordinal()+1 && (options[1]==2 || options[2]==2 || options[3]==2 || options[4]==2)) {
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
