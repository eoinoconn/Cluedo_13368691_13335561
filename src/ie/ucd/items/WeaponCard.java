package ie.ucd.items;

public class WeaponCard extends Card {

	private Weapon name;
	
	public WeaponCard(Weapon name) {
		this.name = name;
	}
	
	public Weapon getName() {
		return name;
	}
	
	public String toString() {
		return name.toString();
	}
}
