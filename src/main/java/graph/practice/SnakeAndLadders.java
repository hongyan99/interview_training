package graph.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Find the minimum number of die rolls necessary to reach the final cell of the given Snakes 
 * and Ladders board game. 
 * 
 * Rules are as usual: Player starts from cell one, rolls a die and 
 * advances 1-6 (random number of) steps at a time. Should they land on a cell where a ladder 
 * starts, player immediately climbs up that ladder. Similarly, having landed on a cell with a 
 * snake’s mouth, player goes down to the tail of that snake before they roll the die again. 
 * Game ends when the player arrives at the last cell.
 * 
 * @author Hongyan
 */
public class SnakeAndLadders {
	public static void main(String[] args) {
		Integer[] moves = new Integer[] {-1, 18, -1, -1, -1, -1, -1, -1, 2, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1, -1};
		System.out.println(minNumberOfRolls(20, Arrays.asList(moves)));
		moves = new Integer[] {-1, -1, -1, -1, -1, -1, -1, -1};
		System.out.println(minNumberOfRolls(8, Arrays.asList(moves)));
		moves = new Integer[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		System.out.println(minNumberOfRolls(13, Arrays.asList(moves)));
		moves = new Integer[] {-1};
		System.out.println(minNumberOfRolls(1, Arrays.asList(moves)));
		moves = new Integer[] {-1,-1,-1,-1,36,-1,-1,-1,-1,-1,-1,28,-1,-1,-1,-1,32,-1,
			-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,37,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,9,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,44,-1};
		System.out.println(minNumberOfRolls(59, Arrays.asList(moves)));
	}
	
	private static int minNumberOfRolls(int n, List<Integer> moves) {
		// Algorithm:
		// 1. Do a BFS traversal.
		// 2. For each node, find all possible moves for each dice value
		// 3. Record the visited nodes and the steps
		if(n==1) {
			return 0;
		}
		List<int[]> queue = new ArrayList<>();
		queue.add(new int[] {0, 0});
		final boolean[] visited = new boolean[n];
		
		while (!queue.isEmpty()) {
			int[] node = queue.remove(0);
			if(visited[node[0]]) {
				continue;
			}
			visited[node[0]]=true;
			List<int[]> adjList = calcAdjList(node, moves, n);
			if(adjList.isEmpty()) {
				return node[1]+1;
			}
			adjList.forEach(c-> {
				if(!visited[c[0]]) {
					queue.add(c);
				}
			});
		}
		return -1;
    }

	private static List<int[]> calcAdjList(int[] node, List<Integer> moves, int n) {
		List<int[]> returns = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			int index = node[0]+i;
			if(index == n-1) {
				return Collections.emptyList();
			}
			int nextIndex = moves.get(index);
			if(nextIndex==-1) {
				returns.add(new int[] {index, node[1]+1});
			} else if (nextIndex == n-1) {
				return Collections.emptyList();
			} else if (nextIndex < n-1) {
				returns.add(new int[] {nextIndex, node[1]+1});
			}
		}
		return returns;
	}
}
