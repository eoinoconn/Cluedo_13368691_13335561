package ie.ucd.items;


public class GameBoard {

	private static final int DIMENSIONS = 9;
	private int[][] grid = new int[DIMENSIONS][DIMENSIONS];
	
	public GameBoard(int[][] grid) {
        
		this.grid = grid;
	}
	
	// Method to return the current grid square and the numbers corresponding to the 
	public int[] getOptions(SuspectPawn pawn) {
		int[] location = pawn.getLocation();
		int[] options = new int[5]; // some array of numbers representing options
		options[0] = grid[location[0]][location[1]]; //current position
		if((location[1]-1)>=0 && (location[1]-1)<DIMENSIONS) {
			options[1] = grid[location[0]][location[1]-1]; //cell above
		}
		if((location[1]+1)>=0 && (location[1]+1)<DIMENSIONS) {
			options[2] = grid[location[0]][location[1]+1]; //cell below
		}
		if((location[0]-1)>=0 && (location[0]-1)<DIMENSIONS) {
			options[3] = grid[location[0]-1][location[1]]; //cell left
		}
		if((location[0]+1)>=0 && (location[0]+1)<DIMENSIONS) {
			options[4] = grid[location[0]+1][location[1]]; //cell right
		}
		return options;
	}
	
	public int[] getRoomLocation(Room room) {
		int[] location = new int[2];
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	            if (grid[i][j]==room.ordinal()+10 && (grid[i+1][j]==room.ordinal() || grid[i-1][j]==room.ordinal() || grid[i][j+1]==room.ordinal() || grid[i][j-1]==room.ordinal())) {
	                // if room in room adjacent to doorway
	            	location[0] = i;
	                location[1] = j;
	            	return location;
	            }
	        }
		}
		return location; // will return -1, 0 as location if room number not found in grid
		
	}
}
