package review.graph.advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import review.graph.DfsBucket;

public class BellmanFord {
	public static void main(String[] args) {
		// List of graph edges as per above diagram
		List<Edge> edges = Arrays.asList(
			new Edge(0, 6, 2), new Edge(1, 2, -4),
			new Edge(1, 4, 1), new Edge(1, 6, 8),
			new Edge(3, 0, 3), new Edge(3, 4, 5),
			new Edge(5, 1, 2), new Edge(7, 0, 6),
			new Edge(7, 1, -1), new Edge(7, 3, 4),
			new Edge(7, 5, -4)
		);

		// Set number of vertices in the graph
		final int n = 8;

		// create a graph from given edges
		Graph graph = new Graph(edges, n);

		// source vertex
		int source = 7;
		
		System.out.println(Arrays.toString(findShortestPath(graph, source, n)));
	}

	private static int[] findShortestPath(Graph graph, int source, int size) {
		final int distances[] = new int[size];
		for(int i = 0; i < size; i++) {
			distances[i] = Integer.MAX_VALUE;
		}
		distances[source] = 0;
		
		DfsBucket bucket = new DfsBucket(size);
		for(int k = 0; k < size-1; k++) {
			bucket.add(7);
			while (!bucket.isEmpty()) {
				bucket.offer(s -> {
					// do for every edge (v -> u)
					for (Edge e : graph.getAdjList().get(s)) {
						distances[e.getDest()] = Math.min(
							distances[e.getDest()], 
							distances[s]+e.getWeight()
						);
						bucket.add(e.getDest());
					}
				});
			}
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
