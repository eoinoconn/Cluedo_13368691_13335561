package ie.ucd.gameEntities.testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.gameEntities.GameBoard;
import ie.ucd.gameEntities.Room;
import ie.ucd.gameEntities.Slot;

class GameBoardTest {

	GameBoard board;
	int[][] grid;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		int[][] grid = {{11,11,11,11,11,11,10,10,12,12,12,12,12,12,12,12,10,10,13,13,13,13,13,13,13},
				{11,11,11,11,11,11,10,10,12,12,12,12,12,12,12,12,10,10,13,13,13,13,13,13,13},
				{11,11,11,11,11,11,10,2,12,12,12,12,12,12,12,12,2,10,13,13,13,13,13,13,13},
				{11,11,11,11,11,11,10,10,12,12,12,12,12,12,12,12,10,10,13,13,13,13,13,13,13},
				{11,11,11,11,11,11,10,10,12,12,12,12,12,12,12,12,10,10,3,13,13,13,13,13,13},
				{10,10,10,1,10,10,10,10,12,12,12,12,12,12,12,12,10,10,10,10,10,10,10,10,10},
				{10,10,10,10,10,10,10,10,10,2,10,10,10,10,2,10,10,10,10,10,10,10,10,10,10},
				{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,15,15,15,15,15,15,15},
				{10,10,10,10,10,10,10,10,10,10,0,0,0,0,0,10,10,5,15,15,15,15,15,15,15},
				{14,14,14,14,14,10,10,10,10,10,0,0,0,0,0,10,10,10,15,15,15,15,15,15,15},
				{14,14,14,14,14,14,14,14,10,10,0,0,0,0,0,10,10,10,15,15,15,15,15,15,15},
				{14,14,14,14,14,14,14,14,10,10,0,0,0,0,0,10,10,10,15,15,15,15,15,15,15},
				{14,14,14,14,14,14,14,14,4,10,0,0,0,0,0,10,10,10,10,10,10,10,10,5,10},
				{14,14,14,14,14,14,14,14,10,10,0,0,0,0,0,10,10,10,10,10,10,6,10,10,10},
				{14,14,14,14,14,14,14,14,10,10,0,0,0,0,0,10,10,10,16,16,16,16,16,16,16},
				{14,14,14,14,14,14,14,14,10,10,0,0,0,0,0,10,10,16,16,16,16,16,16,16,16},
				{10,10,10,10,10,10,4,10,10,10,0,0,0,0,0,10,6,16,16,16,16,16,16,16,16},
				{10,10,10,10,10,10,10,10,10,10,10,8,8,10,10,10,10,16,16,16,16,16,16,16,16},
				{10,10,10,10,10,10,7,10,10,18,18,18,18,18,18,10,10,10,16,16,16,16,16,16,16},
				{17,17,17,17,17,17,17,10,10,18,18,18,18,18,18,8,10,10,10,10,10,10,10,10,10},
				{17,17,17,17,17,17,17,10,10,18,18,18,18,18,18,10,10,9,10,10,10,10,10,10,10},
				{17,17,17,17,17,17,17,10,10,18,18,18,18,18,18,10,10,19,19,19,19,19,19,19,19},
				{17,17,17,17,17,17,17,10,10,18,18,18,18,18,18,10,10,19,19,19,19,19,19,19,19},
				{17,17,17,17,17,17,17,10,10,18,18,18,18,18,18,10,10,19,19,19,19,19,19,19,19},
				{17,17,17,17,17,17,17,10,10,18,18,18,18,18,18,10,10,19,19,19,19,19,19,19,19}};
		board = GameBoard.getInstance(grid);
	}

	@Test
	void testgetRoomLocation() {
		int[] expected_1 = {1, 1};
		assertEquals(expected_1[0], board.getRoomLocation(Room.values()[0])[0]);
		assertEquals(expected_1[1], board.getRoomLocation(Room.values()[0])[1]);
		int[] expected_2 = {1,8};
		assertEquals(expected_2[0], board.getRoomLocation(Room.values()[1])[0]);
		assertEquals(expected_2[1], board.getRoomLocation(Room.values()[1])[1]);
		int[] expected_3 = {14, 18};
		assertEquals(expected_3[0], board.getRoomLocation(Room.values()[5])[0]);
		assertEquals(expected_3[1], board.getRoomLocation(Room.values()[5])[1]);
	}
	@Test
	void testGetDimensions() {
		assertEquals(25, board.getDimensions());
	}
	
	@Test
	void testGetSuspectPawn() {
		Slot slot = board.getSlot(5,5);
		assertEquals(true, slot instanceof Slot);
		assertEquals(true, slot != null);
		int[] location = {5,5};
		slot = board.getSlot(location);
		assertEquals(true, slot != null);
	}
}
