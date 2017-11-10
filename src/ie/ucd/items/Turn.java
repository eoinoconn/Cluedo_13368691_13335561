package ie.ucd.items;

import java.util.ArrayList;

public class Turn {

	public String makeHypothesis(int id, Room room, Suspect suspect, Weapon weapon, ArrayList<Player> playerCollection) {

		//Create the string to add to the notebooks of all players
		String str_1 = "Player " + id + " suspects that " + suspect.toString() + " committed the murder with the " + weapon.toString() + " in the " + room.toString();
		
		// Loop to iterate through every Player
		for (int i = 0; i < playerCollection.size(); i++) {
			
			// Check each players hand for the mentioned hypothesis
			// Take in the first card that refutes the hypothesis
			Card refute = playerCollection.get(i).checkCards(room, suspect, weapon);
			if (refute != null) {
				
				// Create the string to add to the notebooks of the un-involved players
				String str_2 = str_1 + "\nPlayer " + i + " refuted Player " + id + "s hypothesis";
				
				// Then add that string to the notebook of the un-involved players
				for(int j = 0; j < playerCollection.size(); j++) {
					if (j == i || j == id) continue;
					playerCollection.get(j).getNotebook().addEvent(str_2);
				}
				
				// create the string to add to the refuting players notebook
				str_2 = str_1 + "\nYou refuted Player " + id +"s hypothesis with " + refute.toString();
				playerCollection.get(i).getNotebook().addEvent(str_2);

				// create the relevant string to add to the current players notebook
				str_2 = "\nPlayer " + i + "refuted your hypothesis with " + refute.toString();
				playerCollection.get(id).getNotebook().addEvent(str_1 + str_2);

				// Exit the loop
				return str_2;
			}
			
		}
		
		// In the event the hypothesis is not refuted, tell all the players 
		str_1 = str_1 + "\nPlayer " + id + "s hypothesis was not refuted";
		for(Player player: playerCollection) {
			player.getNotebook().addEvent(str_1);
		}
		
		// return printable string for current player
		return "Your Hypothesis was not refuted";
	}
	
	public int makeMove(char direction, SuspectPawn suspectPawn, GameBoard board) {
		int[] options = board.getOptions(suspectPawn);
		
		switch(direction) {
		case 'u':
			if(options[1]>0 && !((options[0]==10 && options[1]>10)||(options[0]>10 && options[1]==10))) { // not at moving off board or into wall
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]-1);
				if(options[1]<=10) {
					return 1; 		// return 1 if moved in corridor
				}
				return 0;			// return 0 if moved in room
			}
			return -1;				// return -1 if could not move
		case 'd':
			if(options[2]>0 && !((options[0]==10 && options[2]>10)||(options[0]>10 && options[2]==10))) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]+1);
				if(options[2]<=10) { // do not decrement moves if in a room
					return 1; 		// return 1 if moved in corridor
				}
				return 0;			// return 0 if moved in room
			}
			return -1;				// return -1 if could not move
		case 'l':
			if(options[3]>0 && !((options[0]==10 && options[3]>10)||(options[0]>10 && options[3]==10))) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0]-1, suspectPawn.getLocation()[1]);
				if(options[3]<=10) { // do not decrement moves if in a room
					return 1; 		// return 1 if moved in corridor
				}
				return 0;			// return 0 if moved in room
			}
			return -1;				// return -1 if could not move
		case 'r':
			if(options[4]>0 && !((options[0]==10 && options[4]>10)||(options[0]>10 && options[4]==10))) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0]+1, suspectPawn.getLocation()[1]);
				if(options[4]<=10) { // do not decrement moves if in a room
					return 1; 		// return 1 if moved in corridor
				}
				return 0;			// return 0 if moved in room
			}
			return -1;				// return -1 if could not move
		default:
			return -2;				// return -2 if invalid option entered
		}

	}
	
	
}
