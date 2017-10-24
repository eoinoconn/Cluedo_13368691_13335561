package ie.ucd.items;

public class WeaponPawn extends Pawn {

	private Weapon name;
	
	public WeaponPawn(int[] location, Weapon name) {
		this.setLocation(location);
		this.name = name;
	}
	
	public Weapon getName() {
		return name;
	}
}
