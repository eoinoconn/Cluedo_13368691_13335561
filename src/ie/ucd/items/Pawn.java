package ie.ucd.items;

public abstract class Pawn {

	private int location[] = new int[2];
	
	public int[] getLocation() {
		return location;
	}
	
	public void setLocation(int rlocation, int clocation) {
		location[0] = rlocation;
		location[1] = clocation;
	}
	
}
