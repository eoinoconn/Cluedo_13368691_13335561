package ie.ucd.items;

public class GameBoard {

	private int[][] grid = new int[10][10];
	
	public GameBoard(int[][] grid) {
		this.grid = grid;
	}
	
	public int[] getOptions(int[] location) {
		int[] options = new int[5]; // some array of numbers representing options
		xloc = location[0];
		yloc = location[1];
		// first check current location - am I at a doorway?
		if(grid[xloc][yloc]>0) {
			//at a doorway
		}
		if(grid[xloc+1][yloc+1]!=null) {
			//this cell can be moved into
		}
		// repeat for below and left and right
	}
	
}
