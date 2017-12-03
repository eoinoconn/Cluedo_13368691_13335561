package ie.ucd.items;

public abstract class Pawn {

	private int location[] = new int[2];
	
	public int[] getLocation() {
		return location;
	}
	
	public void setLocation(int row, int col) {
		location[0] = row;
		location[1] = col;
	}
	
}
