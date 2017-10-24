package ie.ucd.items;

public class PersonCard extends Card {

	private Person name;
	
	public PersonCard(Person name) {
		this.name = name;
	}
	
	public Person getName() {
		return name;
	}
}
