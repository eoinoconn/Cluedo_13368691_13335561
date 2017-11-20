package ie.ucd.items;

import java.util.ArrayList;

public class Turn {

	public String makeHypothesis(int id, Room room, Suspect suspect, Weapon weapon, ArrayList<Player> playerCollection) {

		//Create the string to add to the notebooks of all players
		String str_1 = "Player " + (id+1) + " suspects that " + suspect.toString() + " committed the murder with the " + weapon.toString() + " in the " + room.toString();
		
		// Loop to iterate through every Player
		for (int i = 0; i < playerCollection.size(); i++) {
			
			// Check each players hand for the mentioned hypothesis
			// Take in the first card that refutes the hypothesis
			// Do not check own hand
			if(i==id) continue;
			Card refute = playerCollection.get(i).checkCards(room, suspect, weapon);
			if (refute != null) {
				
				// Create the string to add to the notebooks of the un-involved players
				String str_2 = str_1 + "\nPlayer " + (i+1) + " refuted Player " + (id+1) + "'s hypothesis";
				
				// Then add that string to the notebook of the un-involved players
				for(int j = 0; j < playerCollection.size(); j++) {
					if (j == i || j == id) continue;
					playerCollection.get(j).getNotebook().addEvent(str_2);
				}
				
				// create the string to add to the refuting players notebook
				str_2 = str_1 + "\nYou refuted Player " + (id+1) +"'s hypothesis with " + refute.getName().toString();
				playerCollection.get(i).getNotebook().addEvent(str_2);

				// create the relevant string to add to the current players notebook
				str_2 = "\nPlayer " + (i+1) + " refuted your hypothesis with " + refute.getName().toString();
				playerCollection.get(id).getNotebook().addEvent(str_1 + str_2);

				// Exit the loop
				return str_2;
			}
			
		}
		
		// In the event the hypothesis is not refuted, tell all the players 
		str_1 = str_1 + "\nPlayer " + (id+1) + "'s hypothesis was not refuted";
		
		for(Player player: playerCollection) {
			player.getNotebook().addEvent(str_1);
		}
		
		// return printable string for current player
		return "Your Hypothesis was not refuted";
	}
	
	public int makeMove(char direction, SuspectPawn suspectPawn, GameBoard board) {
		int[] options = board.getOptions(suspectPawn);
		int dim = board.getDimensions();
		
		switch(direction) {
		case 'u':
			
			if(options[1]>0 && !((options[0]==1 && options[1]==3)||(options[0]==3 && options[1]==1))) { // not at moving off board or into wall
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]-1);
				if(options[1]<3||options[0]==2) {
					return 1; 		// return 1 if used up a move
				}
				return 0;			// return 0 if did not use up a move
			}
			else if(options[1]==0) { // on top row
				if(options[3]==0) { // in top left corner
					suspectPawn.setLocation(dim-1, dim-1);
					return 0; // do not use up a move to move in secret passage
				}
				else if(options[4]==0) { // in top right corner
					suspectPawn.setLocation(0, dim-1);
					return 0; // do not use up a move to move in secret passage
				}
			}
			return -1;				// return -1 if could not move
		case 'd':
			if(options[2]>0 && !((options[0]==1 && options[2]==3)||(options[0]==3 && options[2]==1))) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0], suspectPawn.getLocation()[1]+1);
				if(options[2]<3||options[0]==2) { 
					return 1; 		// return 1 if used up a move
				}
				return 0;			// return 0 if did not use up a move
			}
			else if(options[2]==0) { // on bottom row
				if(options[3]==0) { // in bottom left corner
					suspectPawn.setLocation(dim-1, 0);
					return 0; // do not use up a move to move in secret passage
				}
				else if(options[4]==0) { // in bottom right corner
					suspectPawn.setLocation(0, 0);
					return 0; // do not use up a move to move in secret passage
				}
			}
			return -1;				// return -1 if could not move
		case 'l':
			if(options[3]>0 && !((options[0]==1 && options[3]==3)||(options[0]==3 && options[3]==1))) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0]-1, suspectPawn.getLocation()[1]);
				if(options[3]<3||options[0]==2) { 
					return 1; 		// return 1 if used up a move
				}
				return 0;			// return 0 if did not use up a move
			}
			else if(options[3]==0) { // on left wall
				if(options[1]==0) { // in top left corner
					suspectPawn.setLocation(dim-1, dim-1);
					return 0; // do not use up a move to move in secret passage
				}
				else if(options[2]==0) { // in bottom left corner
					suspectPawn.setLocation(dim-1, 0);
					return 0; // do not use up a move to move in secret passage
				}
			}
			return -1;				// return -1 if could not move
		case 'r':
			if(options[4]>0 && !((options[0]==1 && options[4]==3)||(options[0]==3 && options[4]==1))) {
				suspectPawn.setLocation(suspectPawn.getLocation()[0]+1, suspectPawn.getLocation()[1]);
				if(options[4]<3||options[0]==2) { 
					return 1; 		// return 1 if used up a move
				}
				return 0;			// return 0 if did not use up a move
			}
			else if(options[4]==0) { // on right wall
				if(options[1]==0) { // in top right corner
					suspectPawn.setLocation(0, dim-1);
					return 0; // do not use up a move to move in secret passage
				}
				else if(options[2]==0) { // in bottom right corner
					suspectPawn.setLocation(0, 0);
					return 0; // do not use up a move to move in secret passage
				}
			}
			return -1;				// return -1 if could not move
		default:
			return -2;				// return -2 if invalid option entered
		}

	}

	public boolean makeAccusation(int whoseGo, Room murderRoom, Suspect murderer, Weapon murderWeapon,
			ArrayList<Player> playerCollection, ArrayList<Card> murdererCards) {
		
		if(murderer == murdererCards.get(0).getName() && murderRoom == murdererCards.get(1).getName() 
				&& murderWeapon == murdererCards.get(2).getName()) {
			return true;
		}
		else {
			
			//Create the string to add to the notebooks of all players
			String str_1 = "Player " + (whoseGo+1) + " believes that " + murderer.toString() + " committed the murder with the " 
					+ murderWeapon.toString() + " in the " + murderRoom.toString();
			
			// Create the string to add to the notebooks of the un-involved players
			String str_2 = str_1 + "\nPlayer " + (whoseGo+1) + " is wrong and has been removed from the game!";
			
			// Then add that string to the notebook of the uninvolved players
			for(int i = 0; i < playerCollection.size(); i++) {
				if (i == whoseGo) continue;
				playerCollection.get(i).getNotebook().addEvent(str_2);
			}
			
			// Remove Player from the game
			return false;
		}
		
	}
}

