package review.graph.advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import review.graph.DfsBucket;

public class BellmanFord {
	public static void main(String[] args) {
		List<Edge> edges = Arrays.asList(
			new Edge(0, 6, 2), new Edge(1, 2, -4),
			new Edge(1, 4, 1), new Edge(1, 6, 8),
			new Edge(3, 0, 3), new Edge(3, 4, 5),
			new Edge(5, 1, 2), new Edge(7, 0, 6),
			new Edge(7, 1, -1), new Edge(7, 3, 4),
			new Edge(7, 5, -4)
		);

		final int n = 8;
		Graph graph = new Graph(edges, n);
		int source = 7;
		
		System.out.println(Arrays.toString(findShortestPath(graph, source, n)));
	}

	private static int[] findShortestPath(Graph graph, int source, int size) {
		// 1) Initialize the shortest distance for each node.
		final int distances[] = new int[size];
		for(int i = 0; i < size; i++) {
			distances[i] = Integer.MAX_VALUE;
		}
		distances[source] = 0;
		
		// 2) Creates the DFS bucket
		DfsBucket bucket = new DfsBucket(size);
		
		// 3) Loop size-1 times
		for(int k = 0; k < size-1; k++) {
			// 3.1) Start from the source node
			bucket.add(7);
			// 3.2) Traverse using the DFS bucket
			while (!bucket.isEmpty()) {
				// 3.3) For each node in the bucket
				bucket.offer(s -> {
					for (Edge e : graph.getAdjList().get(s)) {
						// 3.4) Optimize the distance. 
						// One optimization we can do here is: check that whether 
						// there is any update to the distances array, if not for
						// all nodes, we can short-circuit and exit the loop.
						// I did not do it here to avoid cluttering of the code.
						distances[e.getDest()] = Math.min(
							distances[e.getDest()], 
							distances[s]+e.getWeight()
						);
						bucket.add(e.getDest());
					}
				});
			}
			// 3.5) Clear the bucket for the next iteration
			bucket.clear();
		}
		
		return distances;
	}
	
	private static class Graph {
		// A List of Lists to represent an adjacency list
		private List<List<Edge>> adjList = null;

		// Constructor
		public Graph(List<Edge> edges, int n) {
			adjList = new ArrayList<>(n);

			for (int i = 0; i < n; i++) {
				adjList.add(i, new ArrayList<>());
			}

			// add edges to the undirected graph
			for (int i = 0; i < edges.size(); i++) {
				Edge edge = edges.get(i);
				int src = edge.getSource();
				adjList.get(src).add(edge);
			}
		}
		
		public List<List<Edge>> getAdjList() {
			return adjList;
		}
	}
	
	private static class Edge {
		private final int source, dest, weight;

		public Edge(int source, int dest, int weight) {
			this.source = source;
			this.dest = dest;
			this.weight = weight;
		}

		public int getDest() {
			return dest;
		}

		public int getSource() {
			return source;
		}

		public int getWeight() {
			return weight;
		}
	}
	
	
}
