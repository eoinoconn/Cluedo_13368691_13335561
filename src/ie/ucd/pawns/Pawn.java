package ie.ucd.pawns;

public abstract class Pawn {

	private int location[] = new int[2];
	
	public int[] getLocation() {
		return location;
	}
	
	public void setLocation(int[] location) {
		this.location = location;
	}
	
}
