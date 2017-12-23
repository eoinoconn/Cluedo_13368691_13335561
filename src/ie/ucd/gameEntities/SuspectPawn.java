package ie.ucd.gameEntities;

public class SuspectPawn extends Pawn {

	private Suspect name;
	
	public SuspectPawn(int[] location, Suspect name) {
		this.setLocation(location[0], location[1]);
		this.name = name;
	}
	
	public Suspect getName() {
		return name;
	}
}
