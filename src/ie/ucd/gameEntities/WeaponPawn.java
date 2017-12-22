package ie.ucd.gameEntities;

public class WeaponPawn extends Pawn {

	private Weapon name;
	
	public WeaponPawn(int[] location, Weapon name) {
		this.setLocation(location[0], location[1]);
		this.name = name;
	}
	
	public Weapon getName() {
		return name;
	}
}
