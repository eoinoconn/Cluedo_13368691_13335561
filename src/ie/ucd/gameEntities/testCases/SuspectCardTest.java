package ie.ucd.gameEntities.testCases;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.gameEntities.Suspect;
import ie.ucd.gameEntities.SuspectCard;

class SuspectCardTest {

	SuspectCard susCard;
	

	@BeforeEach
	void setUp() throws Exception {
		susCard = new SuspectCard(Suspect.values()[0]);
	}

	@Test
	void testToString() {
		String str = susCard.toString();
		String expected = "COLONELMUSTARD";
		assertEquals(expected, str);
	}
	
	@Test
	void testGetName() {
		assertEquals(Suspect.values()[0], susCard.getName());
	}

}
