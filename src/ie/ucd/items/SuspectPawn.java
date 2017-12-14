package ie.ucd.items;

public class SuspectPawn extends Pawn {

	private Suspect name;
	
	public SuspectPawn(GameBoard gameBoard, int[] location, Suspect name) {
		this.setLocation(gameBoard, location[0], location[1]);
		this.name = name;
	}
	
	public Suspect getName() {
		return name;
	}
}
