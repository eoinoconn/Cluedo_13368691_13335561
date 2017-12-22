package ie.ucd.gameEntities.testCases;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.gameEntities.Room;
import ie.ucd.gameEntities.RoomCard;

class RoomCardTest {

	RoomCard roomCard;
	
	@BeforeEach
	void setUp() throws Exception {
		roomCard = new RoomCard(Room.values()[0]);
	}

	@Test
	void testToString() {
		String str = roomCard.toString();
		String expected = "THEBAR";
		assertEquals(expected, str);
	}
	
	@Test
	void testGetName() {
		assertEquals(Room.values()[0], roomCard.getName());
	}
}
