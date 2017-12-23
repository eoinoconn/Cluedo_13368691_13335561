package ie.ucd.gameRules;

import java.util.Scanner;

import ie.ucd.gameEntities.Notebook;

public class NotebookManager {

	private Notebook nb;
	
	public NotebookManager(Notebook nb, Scanner sc) {
		this.nb = nb;
	}
	
	/**
	 * Prompts user to enter the desired number of entries to view, then calls method to display them
	 * @param sc
	 */
	public void openNotebook(Scanner sc) {
	
		// Get number of entries to print from user
		System.out.println("How many notebook entries would you like to view?");
		System.out.println("(Total " + nb.getSize() + " entries)");
		String str;
		int someInt = 0;
		boolean isInt = false;
		while(!isInt) {
			str = sc.nextLine();
			try {
			    someInt = Integer.parseInt(str);
			    isInt = true;
			  } catch (NumberFormatException e) {
				  System.out.println("\nThis is not a valid entry, try again");
				  isInt = false;
			  }
		}
		
		// print each of these entries
		String entries = nb.lastNEntries(someInt);
		System.out.println(entries + "\nPress enter to continue");
		sc.nextLine();
	}
}
