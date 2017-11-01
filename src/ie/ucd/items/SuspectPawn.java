package ie.ucd.items;

public class SuspectPawn extends Pawn {

	private Suspect name;
	
	public SuspectPawn(int xlocation, int ylocation, Suspect name) {
		this.setLocation(xlocation, ylocation);
		this.name = name;
	}
	
	public Suspect getName() {
		return name;
	}
}
