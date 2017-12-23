package ie.ucd.gameEntities;

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
	
	/**
	 * 
	 * @param type 0 - off map, 1 - corridor, 2 - doorway, 3 - room, 4 - secret passage
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return 0 - off map, 1 - corridor, 2 - doorway, 3 - room, 4 - secret passage
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * 
	 * @param number room/doorway number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * 
	 * @return room/doorway number
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Associate this slot with the slots around it
	 * @param above
	 * @param below
	 * @param left
	 * @param right
	 */
	public void setOptions(Slot above, Slot below, Slot left, Slot right) {
		options[0] = above;
		options[1] = below;
		options[2] = left;
		options[3] = right;
	}
	
	/**
	 * 
	 * @return Array of four slots - above, below, left and right
	 */
	public Slot[] getOptions() {
		return options;
	}
	
	/**
	 * 
	 * @return true if this slot is occupied by a pawn
	 */
	public boolean getHasPawn(){
		return hasPawn;
	}
	
	/**
	 * Sets whether there is a pawn on this slot
	 * @param hasPawn
	 */
	public void setHasPawn(boolean hasPawn) {
		this.hasPawn = hasPawn;
	}
	
}
