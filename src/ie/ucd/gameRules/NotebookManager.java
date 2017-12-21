package ie.ucd.gameRules;

import java.util.Scanner;

import ie.ucd.items.Notebook;

public class NotebookManager {

	private Notebook nb;
	
	public NotebookManager(Notebook nb, Scanner sc) {
		this.nb = nb;
		openNotebook(sc);
	}

	public void openNotebook(Scanner sc) {
	
		// Get number of entries to print from user
		System.out.println("How many notebook entries would you like to view?");
		System.out.println("(Total " + nb.getSize() + " entries)");
		String str = sc.nextLine();
		
		// print each of these entries
		String entries = nb.lastNEntries(Integer.parseInt(str));
		System.out.println(entries + "\nPress enter to continue");
		sc.nextLine();
	}
}
