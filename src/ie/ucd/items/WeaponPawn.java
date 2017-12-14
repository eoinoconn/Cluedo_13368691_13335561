package ie.ucd.items;

public class WeaponPawn extends Pawn {

	private Weapon name;
	
	public WeaponPawn(GameBoard gameBoard, int[] location, Weapon name) {
		this.setLocation(location[0], location[1]);
		gameBoard.getSlot(location).setHasPawn(true);
		this.name = name;
	}
	
	public Weapon getName() {
		return name;
	}
}
