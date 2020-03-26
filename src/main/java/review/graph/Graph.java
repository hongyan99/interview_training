package review.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	// A List of Lists to represent an adjacency list
	private List<List<Integer>> adjList = null;
	private int[] indegree;

	// Constructor
	public Graph(List<Edge> edges, int n) {
		indegree = new int[n];
		adjList = new ArrayList<>(n);

		for (int i = 0; i < n; i++) {
			adjList.add(i, new ArrayList<>());
		}

		// add edges to the undirected graph
		for (int i = 0; i < edges.size(); i++) {
			int src = edges.get(i).getSource();
			int dest = edges.get(i).getDest();

			adjList.get(src).add(dest);
			adjList.get(dest).add(src);
			indegree[dest] += 1;
		}
	}
	
	public List<List<Integer>> getAdjList() {
		return adjList;
	}
	
	public int getIndegree(int node) {
		return indegree[node];
	}
	
	public int getSize() {
		return indegree.length;
	}
}
