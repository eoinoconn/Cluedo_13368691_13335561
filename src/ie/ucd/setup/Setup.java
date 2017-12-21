package ie.ucd.setup;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.items.*;


public abstract class Setup {

	protected static Scanner sc;
	protected static ArrayList<Player> playerCollection;
	protected static int numPlayers;
	protected static GameBoard gameBoard;
	protected static ArrayList<WeaponPawn> weaponPawns;



	protected ArrayList<Room> setupRoomCollection() {

		ArrayList<Room> roomCollection = new ArrayList<Room>();

		for (Room ro : Room.values())
		{
			roomCollection.add(ro);
		}
		return roomCollection;
	}


	protected ArrayList<Suspect> setupSuspectCollection() {
		
		ArrayList<Suspect> suspectCollection = new ArrayList<Suspect>();
		
		for (Suspect per : Suspect.values())
		{
			suspectCollection.add(per);
		}
		return suspectCollection;
	}
	
	
}
