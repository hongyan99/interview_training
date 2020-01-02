package graph.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShortestDistance {
	public static void main(String[] args) {
		List<List<Character>> grid = createGrid("OOOOO", "OOOOO", "OOGOO", "OOOOO", "OOOOO");
		print(find_shortest_distance_from_a_guard(grid));
	}
	
	private static void print(List<List<Integer>> grid) {
		for(int i = 0; i<grid.size(); i++) {
			for(int j = 0; j<grid.get(0).size(); j++) {
				System.out.print(grid.get(i).get(j));
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	private static List<List<Character>> createGrid(String... rows) {
		List<List<Character>> grid = new ArrayList<>();
		for(String row : rows) {
			List<Character> l = new ArrayList<>();
			for(int i = 0; i < row.length(); i++) {
				l.add(row.charAt(i));
			}
			grid.add(l);
		}
		return grid;
	}

	/*
     * Complete the 'find_shortest_distance_from_a_guard' function below.
     *
     * The function accepts the 2D CHARACTER ARRAY.
     * Returns 2D INTEGER ARRAY.
     */
    public static List<List<Integer>> find_shortest_distance_from_a_guard(List<List<Character>> grid) {
    	// Algorithm:
    	// This is really a multi-graph problem, and each graph start from a guard (a 'G' node).
    	// no topological sorting is needed. Just start a different BFS from each 'G' node, and 
    	// update distance as we move forward.
    	
    	// Get all zero-in-degree nodes and sort the graph
    	// ONE DISCOVERY WITH THIS EXERCISE: Using LinkedList instead of ArrayList sped up some 
    	// corner cases by a factor of 3 to 4!!!
    	Queue<int[]> guards = findAllGuards(grid);
		int rows = grid.size();
		int cols = rows==0? 0 : grid.get(0).size();
    	
    	// Calculate the shortest paths
    	List<List<Integer>> dist = new ArrayList<>();
    	for(int i = 0; i < grid.size(); i++) {
    		dist.add(new ArrayList<>());
        	for(int j = 0; j < grid.get(0).size(); j++) {
       			dist.get(i).add(-1);
        	}
    	}

   		calculateShotestPaths(guards, grid, dist, rows, cols);

    	return dist;
    }

	private static void calculateShotestPaths(Queue<int[]> queue, List<List<Character>> grid, List<List<Integer>> dists, int rows, int cols) {
		boolean[][] visited = new boolean[rows][cols];
		while(!queue.isEmpty()) {
			int[] node = queue.poll();
			if(visited[node[0]][node[1]]) {
				continue;
			}
			visited[node[0]][node[1]]=true;
			dists.get(node[0]).set(node[1], node[2]);
			int[] child = new int[] {node[0]-1, node[1], 0};
			if(child[0]>-1 && grid.get(child[0]).get(child[1])=='O') {
				child[2] = node[2] + 1;
				queue.add(child);
			}
			child = new int[] {node[0]+1, node[1], 0};
			if(child[0]<rows && grid.get(child[0]).get(child[1])=='O') {
				child[2] = node[2] + 1;
				queue.add(child);
			}
			child = new int[] {node[0], node[1]+1, 0};
			if(child[1]<cols && grid.get(child[0]).get(child[1])=='O') {
				child[2] = node[2] + 1;
				queue.add(child);
			}
			child = new int[] {node[0], node[1]-1, 0};
			if(child[1]>-1 && grid.get(child[0]).get(child[1])=='O') {
				child[2] = node[2] + 1;
				queue.add(child);
			}
		}
	}

	private static Queue<int[]> findAllGuards(List<List<Character>> grid) {
		Queue<int[]> zeroIn = new LinkedList<>();
		for(int i = 0; i < grid.size(); i++) {
			int cols = grid.get(0).size();
			for(int j = 0; j < cols; j++) {
				if(grid.get(i).get(j)=='G') {
					zeroIn.add(new int[] {i, j, 0});
				}
			}
		}
		return zeroIn;
	}
}
