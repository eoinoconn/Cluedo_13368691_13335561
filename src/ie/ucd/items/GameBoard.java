package ie.ucd.items;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameBoard {

	private static final int DIMENSIONS = 9;
	private int[][] grid = new int[DIMENSIONS][DIMENSIONS];
	
	public GameBoard(String fileName) {
        
		File file = new File(fileName);
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

 
        int lineNo = 0;
        for(List<String> line: lines) {
            int columnNo = 0;
            for (String value: line) {
            	grid[lineNo][columnNo] = Integer.parseInt(value);
                columnNo++;
            }
            lineNo++;
        }
        //System.out.println(Arrays.deepToString(grid));
	}
	
	// Method to return the current grid square and the numbers corresponding to the 
	public int[] getOptions(SuspectPawn pawn) {
		int[] location = pawn.getLocation();
		int[] options = new int[5]; // some array of numbers representing options
		options[0] = grid[location[0]][location[1]]; //current position
		if((location[1]-1)>=0 && (location[1]-1)<DIMENSIONS) {
			options[1] = grid[location[0]][location[1]-1]; //cell above
		}
		if((location[1]+1)>=0 && (location[1]+1)<DIMENSIONS) {
			options[2] = grid[location[0]][location[1]+1]; //cell below
		}
		if((location[0]-1)>=0 && (location[0]-1)<DIMENSIONS) {
			options[3] = grid[location[0]-1][location[1]]; //cell left
		}
		if((location[0]+1)>=0 && (location[0]+1)<DIMENSIONS) {
			options[4] = grid[location[0]+1][location[1]]; //cell right
		}
		return options;
	}
	
}
