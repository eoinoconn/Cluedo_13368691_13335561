package ie.ucd.items;

public class Slot {

	private int type; // 0 if not set, 1 if in corridor, 2 if in doorway, 3 if in room
	private int number; // 0 if in corridor/doorway, room number if in room
	
	public Slot() {
		type = 0;
		number = 0;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
}
