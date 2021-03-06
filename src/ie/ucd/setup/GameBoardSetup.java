package ie.ucd.setup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ie.ucd.gameEntities.GameBoard;

public class GameBoardSetup extends Setup {

	private List<List<String>> lines;
	private int[][] grid;
	Scanner inputStream;
	
	public GameBoardSetup() {
	    lines = new ArrayList<>();
	    grid = new int[25][25];
	}

	/**
	 * Calls the singleton gameboard constructor after getting the board details from the file specified by filename
	 * @param fileName 
	 */
	public void gameBoardSetup(String fileName){
		File file = new File(fileName);

		// Open file, scan grid into lines list
	    try{
	        inputStream = new Scanner(file);
	        while(inputStream.hasNext()){
	        
	        	String line = inputStream.next();
	            String[] values = line.split(",");
	            // this adds the currently parsed line to the 2-dimensional string array
	            lines.add(Arrays.asList(values));
	        }
	        inputStream.close();
	    }catch (FileNotFoundException e) {
	            e.printStackTrace();
	    }
	
	    // convert our grid of strings representing location values into 
	    // grid of integers 
	    int lineNo = 0;
	    for(List<String> line: lines) {
	        int columnNo = 0;
	        for (String value: line) {
	        	grid[lineNo][columnNo] = Integer.parseInt(value);
	            columnNo++;
	        }
	        lineNo++;
	    }
		
		gameBoard = GameBoard.getInstance(grid);
	}
	
}
