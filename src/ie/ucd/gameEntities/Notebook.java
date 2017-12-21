package ie.ucd.gameEntities;

import java.util.ArrayList;

public class Notebook {

	
	private ArrayList<String> events;

	
	public Notebook() {
		events = new ArrayList<String>();
	}
	
	public void addEvent(String event) {
		events.add(event);
	}
	
	/*
	 * Method to return string of the last N notebook entries
	 * @return
	 * 
	 */
	public String lastNEntries(int N) {
		String str = "";
		int size = this.getSize();
		if(N<=size) {
			for (int i = (size - N); i < size; i++) {
				str += (events.get(i) + "\n");
			}
			return str;
		}
		return "Too many entries! There are " + Integer.toString(size) + " entries in this notebook\n";
	}
	
	public int getSize() {
		return events.size();
	}
	
}
