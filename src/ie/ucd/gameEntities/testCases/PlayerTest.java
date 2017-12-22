package ie.ucd.gameEntities.testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.gameEntities.Notebook;
import ie.ucd.gameEntities.Player;
import ie.ucd.gameEntities.Room;
import ie.ucd.gameEntities.RoomCard;
import ie.ucd.gameEntities.Suspect;
import ie.ucd.gameEntities.SuspectCard;
import ie.ucd.gameEntities.SuspectPawn;
import ie.ucd.gameEntities.Weapon;
import ie.ucd.gameEntities.WeaponCard;

class PlayerTest {

	Player player;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		int[] location = {1, 2};
		player = new Player(new SuspectPawn(location, Suspect.values()[1]), true);
	}

	@Test
	void testPlayerNumber() {
		assertEquals(5, player.playerNumber());
	}
	
	@Test
	void testGetSuspectPawn() {
		SuspectPawn sus = player.getSuspectPawn();
		assertEquals(true, sus instanceof SuspectPawn);
		assertEquals(true, sus != null);
	}
	
	@Test
	void testGetNotebook() {
		Notebook note = player.getNotebook();
		assertEquals(true, note instanceof Notebook);
		assertEquals(true, note != null);
	}
	

	
	@Test
	void testActive() {
		assertEquals(true, player.isActive());
		player.removeFromGame();
		assertEquals(false, player.isActive());
		
	}
	
	@Test
	void testHypMade() {
		assertEquals(false, player.getHypMade());
		player.setHypMade(true);
		assertEquals(true, player.getHypMade());
		
	}
	
	@Test
	void testMoves() {
		assertEquals(0, player.getMoves());
		player.setMoves(10);
		assertEquals(10, player.getMoves());
		player.moveMade();
		assertEquals(9, player.getMoves());
	}
	
	@Test
	void testCards() {
		player.giveCard(new SuspectCard(Suspect.values()[1]));
		player.giveCard(new WeaponCard(Weapon.values()[1]));
		player.giveCard(new RoomCard(Room.values()[1]));
		player.giveCard(new SuspectCard(Suspect.values()[3]));
		player.giveCard(new WeaponCard(Weapon.values()[2]));
		player.giveCard(new RoomCard(Room.values()[5]));
		String str = player.lookAtHand();
		String expected = "MAC\nCRUTCH\nDEESAPPARTMENT\nFRANK\nRUMHAM\nUNDERTHEBRIDGE\n";
		assertEquals(expected, str);
	}
}
