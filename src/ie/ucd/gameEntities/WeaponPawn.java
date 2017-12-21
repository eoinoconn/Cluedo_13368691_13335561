package ie.ucd.gameEntities;

public class WeaponPawn extends Pawn {

	private Weapon name;
	
	public WeaponPawn(GameBoard gameBoard, int[] location, Weapon name) {
		this.setLocation(gameBoard, location[0], location[1]);
		this.name = name;
	}
	
	public Weapon getName() {
		return name;
	}
}
