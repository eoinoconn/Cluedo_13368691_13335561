package ie.ucd.items;

public class GameBoard {

	private int[][] grid = new int[10][10];
	
	public GameBoard(int[][] grid) {
		this.grid = grid;
	}
	
	// Method to return the current grid square and the numbers corresponding to the 
	public int[] getOptions(PersonPawn pawn) {
		int[] location = pawn.getLocation();
		int[] options = new int[5]; // some array of numbers representing options
		options[0] = grid[location[0]][location[1]]; //current position
		options[1] = grid[location[0]+1][location[1]]; //cell above
		options[2] = grid[location[0]-1][location[1]]; //cell below
		options[3] = grid[location[0]][location[1]-1]; //cell left
		options[4] = grid[location[0]][location[1]+1]; //cell right
		return options;
	}
	
}
