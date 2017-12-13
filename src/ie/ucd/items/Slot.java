package ie.ucd.items;

public class Slot {

	private int type;
	private int number;
	private boolean hasPawn;
	private Slot[] options = new Slot[4];
	
	public Slot() {
		type = 0;
		number = 0;
		hasPawn = false;
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
	
	public void setOptions(Slot above, Slot below, Slot left, Slot right) {
		options[0] = above;
		options[1] = below;
		options[2] = left;
		options[3] = right;
	}
	
	public Slot[] getOptions() {
		return options;
	}
	
	public boolean getHasPawn(){
		return hasPawn;
	}
	
	public void setHasPawn(boolean hasPawn) {
		this.hasPawn = hasPawn;
	}
	
}
