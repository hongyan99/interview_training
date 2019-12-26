package graph.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You are given a rows * cols chessboard and a knight that moves like in normal chess. 
 * Currently knight is at starting position denoted by start_row th row and start_col th col, 
 * and want to reach at ending position denoted by end_row th row and end_col th col.  
 * 
 * The goal is to calculate the minimum number of moves that the knight needs to take to get 
 * from starting position to ending position.
 * 
 * @author hongyanli
 *
 */
public class ChessNight {
	public static void main(String[] args) {
		System.out.println(minMoves(5,5,0,0,4,1));
		System.out.println(minMoves(2,1,1,0,1,0));
		System.out.println(minMoves(1,100000,0,50,0,99999));
	}
	
	private static int minMoves(int rows, int cols, int start_row, int start_col, int end_row, int end_col) {
		List<Coordinate> queue = new ArrayList<>();
		Map<Coordinate, Coordinate> parents = new HashMap<>();
		Map<Coordinate, Boolean> visited = new HashMap<>();
		Coordinate root = new Coordinate(start_row, start_col);
		queue.add(root);
		visited.put(root, Boolean.TRUE);
		while(!queue.isEmpty()) {
			Coordinate coord = queue.remove(0);
			if(coord.x==end_row && coord.y==end_col) {
				int moves = 0;
				while(parents.get(coord)!=null) {
					moves++;
					coord = parents.get(coord);
				}
				return moves;
			}
			List<Coordinate> adjList = getAdjList(rows, cols, coord.x, coord.y);
			for(int i = 0; i<adjList.size(); i++) {
				Coordinate childCoord = adjList.get(i);
				if(visited.get(childCoord)==null) {
					visited.put(childCoord, Boolean.TRUE);
					parents.put(childCoord, coord);
					queue.add(childCoord);
				}
			}
		}
		return -1;
    }
	
	private static List<Coordinate> getAdjList(int rows, int cols, int row, int col) {
		List<Coordinate> coords = new ArrayList<Coordinate>();
		createCoords(rows, cols, coords, row-2, col-1);
		createCoords(rows, cols, coords, row-2, col+1);
		createCoords(rows, cols, coords, row+2, col-1);
		createCoords(rows, cols, coords, row+2, col+1);
		createCoords(rows, cols, coords, row-1, col-2);
		createCoords(rows, cols, coords, row+1, col-2);
		createCoords(rows, cols, coords, row-1, col+2);
		createCoords(rows, cols, coords, row+1, col+2);
		return coords;
	}

	private static void createCoords(int rows, int cols, List<Coordinate> coords, int i, int j) {
		if(i>-1 && i<rows && j>-1 && j<cols) {
			coords.add(new Coordinate(i, j));
		}
	}
	
	private static class Coordinate {
		final int x;
		final int y;
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinate other = (Coordinate) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}
}
