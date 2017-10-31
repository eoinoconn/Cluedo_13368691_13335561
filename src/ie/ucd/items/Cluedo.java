package ie.ucd.items;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.Arrays;
import java.io.*;

public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		// Create instances of game components
		// TODO This could be replaced at a later date with a facade
		// TODO merge into one Hashtable
		Hashtable<Person, Card> personCardDeck = new Hashtable<Person, Card>();
		for (Person per : Person.values())
		{
			personCardDeck.put(per, new PersonCard(per));
		}
		Hashtable<Weapon, Card> weaponCardDeck = new Hashtable<Weapon, Card>();
		for (Weapon wep : Weapon.values())
		{
			weaponCardDeck.put(wep, new WeaponCard(wep));
		}
		
		// Read grid from file
		String fileName = "grid.txt";
		File csv = new File(fileName);
		double[][] grid = new double[10][10];
        String[] testStr = new String[30];
        int[] results = new int[10];
        
        String delimiter = ",";
        
        // TODO error catching
        Scanner sc = new Scanner(csv);
        
        while(sc.hasNextLine()) {
        	String line = sc.nextLine();
        	testStr = line.split(delimiter);
        	for(int i=0;i<testStr.length;i++) {
        		results[i] = Integer.parseInt(testStr[i]);
        	}
        }
        sc.close();
	}
}