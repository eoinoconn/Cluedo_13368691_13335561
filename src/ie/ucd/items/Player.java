package ie.ucd.items;



public class Player {
	
	public SuspectPawn suspectPawn;
	private int moves;
	
	public Player(int xlocation, int ylocation, Suspect name) {
		this.suspectPawn = new SuspectPawn(xlocation, ylocation, name); //place pawn with specified name in start location
		moves = 0;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	
	
	public boolean makeMove(char movement, GameBoard board) {
		int[] options = board.getOptions(suspectPawn);
		System.out.println("\t" + options[1] + "\n" + options[3] + "\t" + options[0] + "\t" + options[4] +"\n" + "\t" + options[2]);
		System.out.println("Select Direction: 'u' for up, 'd' for down, 'l' for left, 'r' for right");
		
		
		switch(movement) {
		case 'u':	
			if(options[1]>0) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]-1);
				if(options[1]<10) { // do not decrement moves if in a room
					this.moves--;
				}
				return true;
			}
			return false;
		case 'd':
			if(options[2]>0) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]+1);
				if(options[2]<10) { // do not decrement moves if in a room
					this.moves--;
				}
				return true;
			}
			return false;
		case 'l':
			if(options[3]>0) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0]-1, suspectPawn.getLocation()[1]);
				if(options[3]<10) { // do not decrement moves if in a room
					this.moves--;
				}
				return true;
			}
			return false;
		case 'r':
			if(options[4]>0) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0]+1, suspectPawn.getLocation()[1]);
				if(options[4]<10) { // do not decrement moves if in a room
					this.moves--;
				}
				return true;
			}
			return false;
		default:
			return false;
				
		}

	}
	
}