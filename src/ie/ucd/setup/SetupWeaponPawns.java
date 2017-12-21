package ie.ucd.setup;

import java.util.ArrayList;
import java.util.Collections;

import ie.ucd.gameEntities.Room;
import ie.ucd.gameEntities.Weapon;
import ie.ucd.gameEntities.WeaponPawn;

public class SetupWeaponPawns extends Setup{

	
	/**Sets up Gameboard with given .csv file
	 * @param
	 * @return
	 * Collection of all Weapon pawns placed in random rooms on the gameboard
	 */
	public void setupWeaponPawns() {
		
		ArrayList<WeaponPawn> pawnCollection = new ArrayList<WeaponPawn>();
		ArrayList<Room> rooms = setupRoomCollection();
		Collections.shuffle(rooms);
		int roomIndex=0;
		int[] location = new int[2];
		WeaponPawn weaponPawn;
		
		for(Weapon wp : Weapon.values()) 
		{
			location = gameBoard.getRoomLocation(rooms.get(roomIndex));
			weaponPawn = new WeaponPawn(gameBoard, location, wp);
			pawnCollection.add(weaponPawn);
			roomIndex++;
		}
		weaponPawns = pawnCollection;
	}
	
	
	
}
