package ie.ucd.items;

public class SuspectCard extends Card {

	private Suspect name;
	
	public SuspectCard(Suspect name) {
		this.name = name;
	}
	
	public Suspect getName() {
		return name;
	}
}
