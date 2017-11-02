package ie.ucd.items;

public class TestClasses {

	public static void main(String args[]) {
		Player player1 = new Player(Suspect.DENNIS);
		GameBoard board = new GameBoard("GameBoard1.csv");
		while(player1.makeMove(board));
	}
	
	
}
