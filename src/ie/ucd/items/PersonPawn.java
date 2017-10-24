package ie.ucd.items;

public class PersonPawn extends Pawn {

	private Person name;
	
	public PersonPawn(int[] location, Person name) {
		this.setLocation(location);
		this.name = name;
		
		
	}
	
	public Person getName() {
		return name;
	}
	
}
