package review.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BTree<N> {
	// A List of Lists to represent an adjacency list
	private Map<N, Pair<N>> adjList = null;

	// Constructor
	public BTree(List<N[]> edges) {
		adjList = new HashMap<>();

		edges.forEach(node-> {
			adjList.put(node[0], new Pair<N>(node[1], node[2]));
		});
	}
	
	public Pair<N> getChildren(N node) {
		return adjList.get(node);
	}
	
	public static class Pair<N> {
		public N left, right;
		Pair(N left, N right) {
			this.left = left;
			this.right = right;
		}
	}
}
