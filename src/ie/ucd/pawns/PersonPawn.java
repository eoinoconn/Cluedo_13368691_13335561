package ie.ucd.pawns;

import ie.ucd.Person;

public class PersonPawn extends Pawn {

	private Person name;
	
	public PersonPawn(int[] location, Person name) {
		this.setLocation(location);
		this.name = name;
		
		
	}
	
}
