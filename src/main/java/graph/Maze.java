package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maze {
	private static final char WATER='#';
	private static final char LAND='.';
	private static final char START='@';
	private static final char GOAL='+';
	
	public static void main(String[] args) {
		int[][] result = findShortestPath(new String[] {"...B", ".b#.","@#+."});
		for(int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
		}
		
	}
	
    static int[][] findShortestPath(String[] grid) {
    	// TODO: need to remember the paths so that invalid path can be found
    	Map<String, String> priorCellMap = new HashMap<>();
    	Map<String, int[]> cellCoords = new HashMap<>();
    	Map<Character, Boolean> keys = new HashMap<>();
    	
    	int[] start = findStart(grid);
    	List<int[]> queue = new ArrayList<>();
    	queue.add(start);
    	visited(getKey(start), start, "", priorCellMap, cellCoords);
    	while(!queue.isEmpty()) {
    		int[] cell = queue.remove(0);
    		String parentKey=getKey(cell);
        	List<int[]> neighbours = findNeighbours(grid, cell, keys);
        	if(neighbours.size()==1 && neighbours.get(0).length==3) {
        		String childKey=getKey(neighbours.get(0));
        		priorCellMap.put(childKey, parentKey);
        		cellCoords.put(childKey, neighbours.get(0));
        		return constructResult(priorCellMap, cellCoords, cell, neighbours.get(0));
        	}
        	
        	for(int i = 0; i<neighbours.size(); i++) {
        		String childKey=getKey(neighbours.get(i));
        		if(priorCellMap.get(childKey)==null) {
        	    	visited(childKey, neighbours.get(i), parentKey, priorCellMap, cellCoords);
	        		queue.add(neighbours.get(i));
        		}
        	}
    	}
    	return null;
    }
    
    private static void visited(String key, int[] coord, String parent, Map<String, String> priorCellMap, Map<String, int[]> cellCoords) {
    	priorCellMap.put(key, parent);
    	cellCoords.put(key, coord);
    }

	private static int[][] constructResult(
			Map<String, String> priorCellMap, Map<String, int[]> cellCoords, 
			int[] parent, int[] goal) {
		List<int[]> result = new ArrayList<>();
		int[] goalCell = new int[2];
		goalCell[0] = goal[0];
		goalCell[1] = goal[1];
		result.add(0,goalCell);
		result.add(0, parent);
		while(true) {
			String parentKey = priorCellMap.get(getKey(parent));
			if(parentKey.isEmpty()) {
				break;
			}
			parent = cellCoords.get(parentKey);
			result.add(0, parent);
		}
		return result.toArray(new int[result.size()][2]);
	}

	private static String getKey(int[] cell) {
		return String.valueOf(cell[0])+'.'+String.valueOf(cell[1]);
	}

	private static List<int[]> findNeighbours(String[] grid, int[] cell, Map<Character, Boolean> keys) {
		int[][] options = new int[4][2];
		options[0][0] = cell[0]-1;
		options[0][1] = cell[1];
		
		options[1][0] = cell[0];
		options[1][1] = cell[1]+1;
		
		options[2][0] = cell[0]+1;
		options[2][1] = cell[1];
		
		options[3][0] = cell[0];
		options[3][1] = cell[1]-1;
		
		List<int[]> returns = new ArrayList<>();
		for(int i = 0; i < options.length; i++) {
			if(options[i][0]>=0 && options[i][0]<grid.length && options[i][1]>=0 && options[i][1]<grid[0].length()) {
				char c = grid[options[i][0]].charAt(options[i][1]);
				switch(c) {
				case GOAL:
					returns.clear();
					int[] result = new int[3];
					result[0]=options[i][0];
					result[1]=options[i][0];
					returns.add(result);
					return returns;
				case WATER:
					break;
				case LAND:
					returns.add(options[i]);
					break;
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case 'h':
				case 'i':
				case 'j':
					returns.add(options[i]);
					keys.put(c, Boolean.TRUE);
					break;
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
					if(keys.get(Character.toLowerCase(c))!=null) {
						returns.add(options[i]);
					}
					break;
				}
			}
		}
		return returns;
	}

	private static int[] findStart(String[] grid) {
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid.length; j++) {
				if(grid[i].charAt(j)==START) {
					return new int[] {i, j};
				}
			}
		}
		throw new IllegalArgumentException();
	}
}
