package ie.ucd.items;

public class Slot {

	private int type; // -1 if not set, 0 if in doorway, 1 if in room, 2 if in corridor
	private int number; // 0 if in corridor/doorway, room number if in room
	
	public Slot() {
		type = -1;
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
