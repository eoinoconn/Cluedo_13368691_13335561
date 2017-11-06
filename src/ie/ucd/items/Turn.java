package ie.ucd.items;

public class Turn {

	public int makeMove(char direction, SuspectPawn suspectPawn, GameBoard board) {
		int[] options = board.getOptions(suspectPawn);
		System.out.println("\t" + options[1] + "\n" + options[3] + "\t" + options[0] + "\t" + options[4] +"\n" + "\t" + options[2]);
		System.out.println("Select Direction: 'u' for up, 'd' for down, 'l' for left, 'r' for right");
		
		switch(direction) {
		case 'u':	
			if(options[1]>0) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]-1);
				if(options[1]<10) {
					return 1; 		// return 1 if moved in corridor
				}
				return 0;			// return 0 if moved in room
			}
			return -1;				// return -1 if could not move
		case 'd':
			if(options[2]>0) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]+1);
				if(options[2]<10) { // do not decrement moves if in a room
					return 1; 		// return 1 if moved in corridor
				}
				return 0;			// return 0 if moved in room
			}
			return -1;				// return -1 if could not move
		case 'l':
			if(options[3]>0) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0]-1, suspectPawn.getLocation()[1]);
				if(options[3]<10) { // do not decrement moves if in a room
					return 1; 		// return 1 if moved in corridor
				}
				return 0;			// return 0 if moved in room
			}
			return -1;				// return -1 if could not move
		case 'r':
			if(options[4]>0) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0]+1, suspectPawn.getLocation()[1]);
				if(options[4]<10) { // do not decrement moves if in a room
					return 1; 		// return 1 if moved in corridor
				}
				return 0;			// return 0 if moved in room
			}
			return -1;				// return -1 if could not move
		default:
			return -1;				// return -1 if could not move
				
		}

	}
	
	
}
