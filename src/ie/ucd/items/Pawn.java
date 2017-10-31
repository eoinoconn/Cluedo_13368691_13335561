package ie.ucd.items;

public abstract class Pawn {

	private int location[] = new int[2];
	
	public int[] getLocation() {
		return location;
	}
	
	public void setLocation(int xlocation, int ylocation) {
		location[0] = xlocation;
		location[1] = ylocation;
	}
	
}
