package review.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	// A List of Lists to represent an adjacency list
	private List<List<Integer>> adjList = null;

	// Constructor
	public Graph(List<Edge> edges, int N) {
		adjList = new ArrayList<>(N);

		for (int i = 0; i < N; i++) {
			adjList.add(i, new ArrayList<>());
		}

		// add edges to the undirected graph
		for (int i = 0; i < edges.size(); i++) {
			int src = edges.get(i).getSource();
			int dest = edges.get(i).getDest();

			adjList.get(src).add(dest);
			adjList.get(dest).add(src);
		}
	}
	
	public List<List<Integer>> getAdjList() {
		return adjList;
	}
}
