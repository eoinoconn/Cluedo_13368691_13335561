package ie.ucd.items;

public class Player {
	
	private SuspectPawn suspectPawn;
	private int moves;
	
	public Player(int xlocation, int ylocation, Suspect name) {
		this.suspectPawn = new SuspectPawn(xlocation, ylocation, name); //place pawn with specified name in start location
		moves = 0;
	}
	
	public SuspectPawn getSuspectPawn() {
		return suspectPawn;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public void setMoves(int moves) {
		this.moves = moves;
	}
	
}