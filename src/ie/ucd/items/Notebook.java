package ie.ucd.items;

import java.util.ArrayList;

public class Notebook {

	
	private ArrayList<String> events;
	private int size;
	
	public Notebook() {
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
		for (int i = (size - N); i < size; i++) {
			str += (events.get(i) + "\n");
		}
		return str;
	}
	
	
}
