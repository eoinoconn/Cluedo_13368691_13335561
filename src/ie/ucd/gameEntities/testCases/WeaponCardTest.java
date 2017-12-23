package ie.ucd.gameEntities.testCases;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.gameEntities.Weapon;
import ie.ucd.gameEntities.WeaponCard;

class WeaponCardTest {

	WeaponCard wepCard;
	

	@BeforeEach
	void setUp() throws Exception {
		wepCard = new WeaponCard(Weapon.values()[0]);
	}

	@Test
	void testToString() {
		String str = wepCard.toString();
		String expected = "POCKETSAND";
		assertEquals(expected, str);
	}
	
	@Test
	void testGetName() {
		assertEquals(Weapon.values()[0], wepCard.getName());
	}

}