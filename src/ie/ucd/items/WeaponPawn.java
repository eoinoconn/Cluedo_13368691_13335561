package ie.ucd.items;

public class WeaponPawn extends Pawn {

	private Weapon name;
	
	public WeaponPawn(int xlocation, int ylocation, Weapon name) {
		this.setLocation(xlocation, ylocation);
		this.name = name;
	}
	
	public Weapon getName() {
		return name;
	}
}
