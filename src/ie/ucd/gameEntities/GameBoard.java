package ie.ucd.gameEntities;

import java.util.ArrayList;

public class GameBoard {

	private static GameBoard uniqueInstance = null;
	private static final int DIMENSIONS = 25;
	private Slot[][] grid = new Slot[DIMENSIONS][DIMENSIONS];
	
	public static GameBoard getInstance(int[][] grid) {
		if(uniqueInstance == null)
			uniqueInstance = new GameBoard(grid);
		return uniqueInstance;
	}
	
	private GameBoard(int[][] grid) {
		Slot above, below, left, right;
		
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	        	
	        	// if at secret passage - set type to 4 and number to room number
	        	if((i==0&&j==0)||(i==0&&j==DIMENSIONS-1)||(i==DIMENSIONS-1&&j==0)||(i==DIMENSIONS-1&&j==DIMENSIONS-1)) {
	        		this.grid[i][j] = new Slot();
	        		this.grid[i][j].setType(4);
        			this.grid[i][j].setNumber(grid[i][j] % 10);
	        	}
	        	
	        	else if(grid[i][j] % 10 > 0) {
	        		// if at doorway - set type to 2 and number to room number
	        		if(grid[i][j]<10) {
	        			this.grid[i][j] = new Slot();
	        			this.grid[i][j].setType(2);
	        			this.grid[i][j].setNumber(grid[i][j]);
	        		}
	        		// if in a room - set type to 3 and number to room number
	        		else {
	        			this.grid[i][j] = new Slot();
	        			this.grid[i][j].setType(3);
	        			this.grid[i][j].setNumber(grid[i][j] % 10);
	        		}
	        	}
	        	
	        	// if in corridor - set type to 1 and number will be zero
	        	else if(grid[i][j]==10){
	        		this.grid[i][j] = new Slot();
	        		this.grid[i][j].setType(1);
	        	}
	        	
	        	// if out of bounds - type and number will be zero
	        	else {
	        		this.grid[i][j] = new Slot();
	        	}
	        }
		}
		
		// Now link slots
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	        	
	        	above = new Slot();
	        	below = new Slot();
	        	left = new Slot();
	        	right = new Slot();
	        	// link corner slots for secret passages
	        	if(i==0) {
	        		if(j==0) {
	        			above = this.grid[DIMENSIONS-1][DIMENSIONS-1];
	        			left = this.grid[DIMENSIONS-1][DIMENSIONS-1];
	        		}
	        		else if(j==DIMENSIONS-1) {
	        			above = this.grid[DIMENSIONS-1][0];
	        			right = this.grid[DIMENSIONS-1][0];
	        		}
	        	}
	        	else if(i==DIMENSIONS-1) {
	        		if(j==0) {
	        			below = this.grid[0][DIMENSIONS-1];
	        			left = this.grid[0][DIMENSIONS-1];
	        		}
	        		else if(j==DIMENSIONS-1) {
	        			below = this.grid[0][0];
	        			right = this.grid[0][0];
	        		}
	        	}
	        	
	        	// link all regularly connected slots
	        	if(i>0)
	        		above = this.grid[i-1][j];
	        	
	        	if(i<DIMENSIONS-1)
	        		below = this.grid[i+1][j];

	        	if(j>0)
	        		left = this.grid[i][j-1];

	        	if(j<DIMENSIONS-1)
	        		right = this.grid[i][j+1];
	        	
	        	this.grid[i][j].setOptions(above, below, left, right);
	        }
		}
	}
	
	public int[] getRoomLocation(Room room) {
		int[] location = new int[2];
		boolean byDoor, inRoom, taken;
		
		// check only the space not adjacent to the edge of the board as there are never doors
		for (int i = 1; i < DIMENSIONS-1; i++) {
	        for (int j = 1; j < DIMENSIONS-1; j++) {
	        	inRoom = grid[i][j].getType()==3;
	        	byDoor = grid[i+1][j].getType()==2 || grid[i-1][j].getType()==2 || grid[i][j+1].getType()==2 || grid[i][j-1].getType()==2;
	        	taken = grid[i][j].getHasPawn();
	        	
	        	if (grid[i][j].getNumber()==room.ordinal()+1 && inRoom && !byDoor && !taken) {
	                // if in room adjacent to doorway
	            	location[0] = i;
	                location[1] = j;
	            	return location;
	            }
	        }
		}
		return location; // will return first grid square room slot found
	}
	
	public Slot getSlot(int[] location) {
		return grid[location[0]][location[1]];
	}
	
	public Slot getSlot(int row, int col) {
		return grid[row][col];
	}
	
	public void changePawnLocation(Pawn pawn, int row, int col) {
		getSlot(row, col).setHasPawn(true);
		getSlot(pawn.getLocation()).setHasPawn(false);
		pawn.setLocation(row, col);
	}
	
	public void printBoard(int currentPlayerId, ArrayList<Player> playerCollection, ArrayList<WeaponPawn> weaponPawns) {
		SuspectPawn sp;
		int pawnId;
		int legendLength;
		Suspect suspectName;
		Weapon weaponName;
		
		for (int i = 0; i < DIMENSIONS; i++) {
	        for (int j = 0; j < DIMENSIONS; j++) {
	        	if(grid[i][j].getHasPawn()) {
		        	pawnId = 1;
		        	for(Player p: playerCollection) {
		        		sp = p.getSuspectPawn();
		        		// if a player is at this grid square, print their id or @ symbol
		        		if(i==sp.getLocation()[0] && j==sp.getLocation()[1]) {
		        			if(pawnId==currentPlayerId+1) {
		        				System.out.print("[@] "); // '@' = current player's pawn
		        			}
		        			else {
		        				System.out.print("[" + pawnId + "] "); // pawnId number = this player's pawn
		        			}
		        			break;
		        		}
		        		pawnId++;
		        	}
		        	
		        	pawnId = 1;
		        	for(WeaponPawn wp : weaponPawns) {
		        		if(i==wp.getLocation()[0] && j==wp.getLocation()[1]) {
		        			System.out.print("(" + pawnId + ") "); // pawnId number = this weaponPawn
		        			break;
		        		}
		        		pawnId++;
		        	}
	        	}
	        	
	        	else if(grid[i][j].getType()==1) {
	        		System.out.printf(" '  "); // corridor
	        	}
	        	else if(grid[i][j].getType()==2||grid[i][j].getType()==4) {
					System.out.printf(" #  "); // doorway or secret passage
	        	}
	        	else if(grid[i][j].getType()==3) {
	        		System.out.printf(" " + grid[i][j].getNumber() + "  "); // room
	        	}
				else {
					System.out.printf(" X  "); // out of bounds
				}
	        }
	        
	        // print a legend of room names
	        if(i<Room.values().length) {
	        	System.out.print("\t" + (i+1) + " = " + Room.values()[i]);
	        }
	        // then add suspect pawns
	        legendLength = Room.values().length+1;
	        if(i>=legendLength && i<legendLength+Suspect.values().length) {
	        	pawnId = i-legendLength;
	        	suspectName = playerCollection.get(pawnId).getSuspectPawn().getName();
	        	System.out.print("\t[" + (pawnId+1) + "] = " + suspectName);
	        }
	        // then add weapon pawns
	        legendLength = Room.values().length+Suspect.values().length+2;
	        if(i>=legendLength && i<legendLength+Weapon.values().length) {
	        	pawnId = i-legendLength;
	        	weaponName = weaponPawns.get(pawnId).getName();
	        	System.out.print("\t(" + (pawnId+1) + ") = " + weaponName);
	        }
	        
	        System.out.print("\n\n");
		}
		System.out.println("[] = suspect pawn, () = weapon pawn, ' = corridor, # = doorway\n");
	}
	
	public int getDimensions() {
		return DIMENSIONS;
	}
}
