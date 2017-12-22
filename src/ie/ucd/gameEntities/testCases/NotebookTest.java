package ie.ucd.gameEntities.testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.gameEntities.Notebook;

class NotebookTest {

	Notebook notebook;
	
	@BeforeEach
	void setUp() throws Exception {
		notebook = new Notebook();
	}
	
	/*
	 * Tests the last entry method for both expected and unexpected input. 
	 */
	@Test
	void testLastNEntries() {
		createFakeEntries();
		
		String str = notebook.lastNEntries(0);
		String expected = "";
		assertEquals(expected, str);
		
		str = notebook.lastNEntries(1);
		expected = "This is the 10 entry\n";
		assertEquals(expected, str);
		
		str = notebook.lastNEntries(5);
		expected = "This is the 6 entry\n" + 
				"This is the 7 entry\n" + 
				"This is the 8 entry\n" + 
				"This is the 9 entry\n" + 
				"This is the 10 entry\n";
		assertEquals(expected, str);
		
		str = notebook.lastNEntries(10);
		expected = "This is the 1 entry\n" +  
				"This is the 2 entry\n" + 
				"This is the 3 entry\n" +
				"This is the 4 entry\n" + 
				"This is the 5 entry\n" + 
				"This is the 6 entry\n" +
				"This is the 7 entry\n" + 
				"This is the 8 entry\n" + 
				"This is the 9 entry\n" + 
				"This is the 10 entry\n";
		assertEquals(expected, str);
		
		str = notebook.lastNEntries(11);
		expected = "Too many entries! There are 10 entries in this notebook\n";
		assertEquals(expected, str);
	}
	
	@Test
	void getSize() {
		assertEquals(0, notebook.getSize());
		createFakeEntries();
		assertEquals(10, notebook.getSize());
	}
	
	void createFakeEntries() {
		for(int i = 1; i <11 ; i++) {
			String event = String.format("This is the %d entry", i);				
			notebook.addEvent(event);
		}
	}

}
