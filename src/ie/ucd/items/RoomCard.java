package ie.ucd.items;

public class RoomCard extends Card {
	
	private Room name;
	
	public RoomCard(Room name) {
		this.name = name;
	}
	
	public Room getName() {
		return name;
	}
	
	public String toString() {
		return name.toString();
	}
	
}
