package ie.ucd.items;

public abstract class Pawn {

	private int location[] = new int[2];
	
	public int[] getLocation() {
		return location;
	}
	
	public void setLocation(GameBoard gameBoard, int rlocation, int clocation) {
		gameBoard.getSlot(location).setHasPawn(false);
		location[0] = rlocation;
		location[1] = clocation;
		gameBoard.getSlot(location).setHasPawn(true);
	}
	
}
