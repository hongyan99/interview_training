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
	}
	
	private static int minNumberOfRolls(int n, List<Integer> moves) {
		// Algorithm:
		// 1. Do a BFS traversal.
		// 2. For each node, find all possible moves for each dice value
		// 3. Record the visited nodes and the steps
		if(n==1) {
			return 0;
		}
		List<Node> queue = new ArrayList<>();
		queue.add(new Node(0, 0));
		final boolean[] visited = new boolean[n];
		
		while (!queue.isEmpty()) {
			Node node = queue.remove(0);
			if(visited[node.val]) {
				continue;
			}
			visited[node.val]=true;
			List<Node> adjList = calcAdjList(node, moves, n);
			if(adjList.isEmpty()) {
				return node.depth+1;
			}
			adjList.forEach(c-> {
				if(!visited[c.val]) {
					queue.add(c);
				}
			});
		}
		return -1;
    }

	private static List<Node> calcAdjList(Node node, List<Integer> moves, int n) {
		List<Node> returns = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			int index = node.val+i;
			if(index == n-1) {
				return Collections.emptyList();
			}
			int nextIndex = moves.get(index);
			if(nextIndex==-1) {
				returns.add(new Node(index, node.depth+1));
			} else if (nextIndex == n-1) {
				return Collections.emptyList();
			} else if (nextIndex < n-1) {
				returns.add(new Node(nextIndex, node.depth+1));
			}
		}
		return returns;
	}
	
	private static class Node {
		final int val;
		final int depth;
		public Node(int val, int depth) {
			this.val = val;
			this.depth = depth;
		}
	}
}
