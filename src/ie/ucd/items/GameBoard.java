package ie.ucd.items;


public class GameBoard {

	private static final int DIMENSIONS = 25;
	private int[][] grid = new int[DIMENSIONS][DIMENSIONS];
	
	public GameBoard(int[][] grid) {
        
		this.grid = grid;
	}
	
	// Method to return the current grid square and the numbers corresponding to the 
	public int[] getOptions(SuspectPawn pawn) {
		int[] location = pawn.getLocation();
		int[] options = new int[5]; // some array of numbers representing options
		options[0] = grid[location[0]][location[1]]; //current position
		if((location[1]-1)>=0) { // ensure space above is not out of bounds
			options[1] = grid[location[0]][location[1]-1];
		}
		if((location[1]+1)<DIMENSIONS) { // ensure space below is not out of bounds
			options[2] = grid[location[0]][location[1]+1];
		}
		if((location[0]-1)>=0) { // ensure space to the left is not out of bounds
			options[3] = grid[location[0]-1][location[1]];
		}
		if((location[0]+1)<DIMENSIONS) { // ensure space to the right is not out of bounds
			options[4] = grid[location[0]+1][location[1]];
		}
		return options;
	}
	
	public int[] getRoomLocation(Room room) {
		int[] location = new int[2];
		// check only the space not adjacent to the edge of the board as there are never doors
		for (int i = 1; i < DIMENSIONS-1; i++) {
	        for (int j = 1; j < DIMENSIONS-1; j++) {
	            if (grid[i][j]==room.ordinal()+10 && (grid[i+1][j]==room.ordinal() || grid[i-1][j]==room.ordinal() || grid[i][j+1]==room.ordinal() || grid[i][j-1]==room.ordinal())) {
	                // if room in room adjacent to doorway
	            	location[0] = i;
	                location[1] = j;
	            	return location;
	            }
	        }
		}
		return location; // will return first grid square if no door found
	}
	
	public void printBoard(SuspectPawn suspectPawn) {
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	        	if(i==suspectPawn.getLocation()[0] && j==suspectPawn.getLocation()[1]) {
	        		System.out.print('@'); // '@' = pawn location
	        	}
	        	else if(grid[i][j]<10) {
					System.out.print(' '); // ' ' = doorway
				}
	        	else if(grid[i][j]==10) {
	        		System.out.print('*'); // '*' = corridor
	        	}
				else {
					System.out.print('#'); // '#' = room
				}
	        }
	        System.out.print('\n');
		}
		System.out.println("'@' = pawn location, '*' = corridor, '#' = room, ' ' = doorway");
	}
}
