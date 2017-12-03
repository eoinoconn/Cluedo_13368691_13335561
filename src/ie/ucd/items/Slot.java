package ie.ucd.items;

public class Slot {

	private int type; // 0 if not set, 1 if in corridor, 2 if in doorway, 3 if in room
	private int number; // 0 if in corridor/doorway, room number if in room
	private int up, down, left, right;
	
	public Slot(int[] options, int number) {
		this.type = options[0];
		this.up = options[1];
		this.down = options[2];
		this.left = options[3];
		this.right = options[4];
		this.number = number;
	}
	
	public int getType() {
		return type;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int[] getOptions() {
		int[] options = new int[5];
		options[0] = type;
		options[1] = up;
		options[2] = down;
		options[3] = left;
		options[4] = right;
		return options;
	}
	
	public void setOptions(int up, int down, int left, int right) {
		
	}
	
}
