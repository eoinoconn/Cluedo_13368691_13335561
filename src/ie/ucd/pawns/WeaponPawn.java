package ie.ucd.pawns;

import ie.ucd.Weapon;

public class WeaponPawn extends Pawn {

	private Weapon name;
	
	public WeaponPawn(int[] location, Weapon name) {
		this.setLocation(location);
		this.name = name;
	}
}
