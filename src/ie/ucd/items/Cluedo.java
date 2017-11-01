package ie.ucd.items;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.ArrayList;


public class Cluedo {
	public static void main(String [] args) throws Exception {
		
		
		// TODO Initialise gameboard
		// TODO randomly assign weapon pawns
		int[] location = {1,1}; //test variable
		
		
		// TODO This could be replaced at a later date with a facade		
		// Create instances of game cards
		// Store game cards in cardDeck
		ArrayList<Card> cardDeck = new ArrayList<Card>();
		ArrayList<Person> personCollection = new ArrayList<Person>();
		for (Person per : Person.values())
		{
			cardDeck.add(new PersonCard(per));
			personCollection.add(per);
		}
		for (Weapon wep : Weapon.values())
		{
			cardDeck.add(new WeaponCard(wep));
		}
		for (Room ro : Room.values())
		{
			cardDeck.add(new RoomCard());
		}
		
		


		
		System.out.println("Welcome to Cluedo!! By eoin and Andy.");
		
		// Establish who's playing
		Scanner sc = new Scanner(System.in);
		boolean anotherPlayer = false;
		Hashtable<String, Player> playerCollection = new Hashtable<String, Player>();
		int players = 0;
		
		// Loop to repeatedly ask for new players
		do {
			
			// Input player name and initialise player object
			System.out.println("Hello player " + (++players) + '.');
			System.out.println("Please enter your name:");
			// TODO catch exceptions
			String str = sc.next();
			playerCollection.put(str, new Player()); // personCollection.get(players)));
			System.out.println("Welcome to the game " + str + '!');
		
			
			// Check if other players are present
			System.out.println("Would you like to add another player? (Y/N)");
			str = sc.nextLine();
			if (Character.toUpperCase(str.charAt(0)) == 'Y') {
				anotherPlayer = true;
			} else anotherPlayer = false;
			
		} while (anotherPlayer);
		sc.close();
	}
}