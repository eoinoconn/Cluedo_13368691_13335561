package ie.ucd.gameEntities;

import java.util.ArrayList;

public class Notebook {

	
	private ArrayList<String> events;
	private int size;
	
	public Notebook() {
		events = new ArrayList<String>();
		size = 0;
	}
	
	public void addEvent(String event) {
		events.add(event);
		size++;
	}
	
	/*
	 * 
	 */
	public String lastNEntries(int N) {
		String str = "";
		if(N<=size) {
			for (int i = (size - N); i < size; i++) {
				str += (events.get(i) + "\n");
			}
			return str;
		}
		return "Too many entries! There are " + Integer.toString(size) + " entries in this notebook\n";
	}
	
	public int getSize() {
		return size;
	}
	
}
