package ie.ucd.items;


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
	        	if(grid[i][j] % 10 > 0) {
	        		if(grid[i][j]<10) {
	        			this.grid[i][j] = new Slot();
	        			this.grid[i][j].setType(0); // 0 if at doorway
	        		}else {
	        			this.grid[i][j] = new Slot();
	        			this.grid[i][j].setType(1); // 1 if in room
	        		}
	        	}
	        	else if(grid[i][j]==10){
	        		this.grid[i][j] = new Slot();
	        		this.grid[i][j].setType(2); // 2 if in corridor
	        	}
	        	this.grid[i][j] = new Slot();
	        	this.grid[i][j].setNumber(grid[i][j] % 10);
	        }
		}
	}
	
	// Method to return the current grid square and the numbers corresponding to the 
	public int[] getOptions(SuspectPawn pawn) {
		int[] location = pawn.getLocation();
		int[] options = new int[5]; // some array of numbers representing options
		options[0] = grid[location[1]][location[0]].getType(); //current position
		if((location[1]-1)>=0) { // ensure space above is not out of bounds
			options[1] = grid[location[1]-1][location[0]].getType();
		}
		if((location[1]+1)<DIMENSIONS) { // ensure space below is not out of bounds
			options[2] = grid[location[1]+1][location[0]].getType();
		}
		if((location[0]-1)>=0) { // ensure space to the left is not out of bounds
			options[3] = grid[location[1]][location[0]-1].getType();
		}
		if((location[0]+1)<DIMENSIONS) { // ensure space to the right is not out of bounds
			options[4] = grid[location[1]][location[0]+1].getType();
		}
		return options;
	}
	
	public int[] getRoomLocation(Room room) {
		int[] location = new int[2];
		// check only the space not adjacent to the edge of the board as there are never doors
		for (int i = 1; i < DIMENSIONS-1; i++) {
	        for (int j = 1; j < DIMENSIONS-1; j++) {
	        	if (grid[i][j].getNumber()==room.ordinal()+1 && (grid[i+1][j].getType()==0 || grid[i-1][j].getType()==0 || grid[i][j+1].getType()==0 || grid[i][j-1].getType()==0)) {
	                // if in room adjacent to doorway
	            	location[0] = j;
	                location[1] = i;
	            	return location;
	            }
	        }
		}
		return location; // will return first grid square if no door found
	}
	
	public void printBoard(SuspectPawn suspectPawn) {
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	        	if(i==suspectPawn.getLocation()[1] && j==suspectPawn.getLocation()[0]) {
	        		System.out.print("@ "); // '@' = pawn location
	        	}
	        	else if(grid[i][j].getType()==0) {
					System.out.printf("* "); // ' ' = doorway
				}
	        	else if(grid[i][j].getType()==1) {
	        		System.out.printf("# "); // '#' = room
	        	}
	        	else if(grid[i][j].getType()==2) {
	        		System.out.printf("' "); // '*' = corridor
	        	}
				else {
					System.out.printf("X "); // 'X' = out of bounds
				}
	        }
	        System.out.print('\n');
		}
		System.out.println("'@' = pawn location, ' ' = corridor, '#' = room, '*' = doorway");
	}
}
