package ie.ucd.gameEntities.testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.gameEntities.Slot;

class SlotTest {

	Slot slot;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		slot = new Slot();
	}

	@Test
	void testType() {
		assertEquals(0, slot.getType());
		slot.setType(5);
		assertEquals(5, slot.getType());
	}
	
	@Test
	void testNumber() {
		assertEquals(0, slot.getNumber());
		slot.setNumber(5);
		assertEquals(5, slot.getNumber());
	}
	
	@Test
	void testOptions() {
		Slot one = new Slot();
		Slot two = new Slot();
		Slot three = new Slot();
		Slot four = new Slot();
		
		slot.setOptions(one, two, three, four);
		Slot[] options = slot.getOptions();
		assertEquals(one, options[0]);
		assertEquals(two, options[1]);
		assertEquals(three, options[2]);
		assertEquals(four, options[3]);
		
	}
	
	@Test
	void testHasPawn() {
		assertEquals(false, slot.getHasPawn());
		slot.setHasPawn(true);
		assertEquals(true, slot.getHasPawn());
	}
	
}
