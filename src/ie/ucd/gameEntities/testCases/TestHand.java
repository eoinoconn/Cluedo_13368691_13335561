/*
 * Class to test hand object.
 */
package ie.ucd.gameEntities.testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.gameEntities.*;

class TestHand {
	
	Hand hand;
	
	@BeforeEach
	public void setUp(){
		this.hand = new Hand();
		this.hand.addCard(new RoomCard(Room.values()[0]));
		this.hand.addCard(new WeaponCard(Weapon.values()[0]));
		this.hand.addCard(new SuspectCard(Suspect.values()[0]));
	}
	
	@Test
	public void testCheckCards() {
		
		// Test with all overlapping inputs
		Card card = this.hand.checkCards(Room.values()[0],  Suspect.values()[0], Weapon.values()[0]);
		assertEquals(true, (card != null));
		
		// Test with 1 overlapping input
		card = this.hand.checkCards(Room.values()[1],  Suspect.values()[2], Weapon.values()[0]);
		assertEquals(true, (card != null));
		
		// Test with no overlapping inputs
		card = this.hand.checkCards(Room.values()[3],  Suspect.values()[2], Weapon.values()[5]);
		assertEquals(false, (card != null));
	
	}
	
	@Test
	public void testToString() {
		String str = hand.toString();
		String expected = "THEBAR\nPOCKETSAND\nDENNIS\n";
		assertEquals(expected, str);
	}

}
