package ie.ucd.gameEntities;

public class SuspectCard extends Card {

	private Suspect name;
	
	public SuspectCard(Suspect name) {
		this.name = name;
	}
	
	public Suspect getName() {
		return name;
	}
	
	public String toString() {
		return name.toString();
	}
}
