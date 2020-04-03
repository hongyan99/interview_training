package review.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Adapted from the solution found in Techie Delight: 
 * https://www.techiedelight.com/find-all-possible-topological-orderings-of-dag/
 * 
 * I did this to show that the algorithm fit into the normalized form of the
 * backtracking algorithm pattern.
 * 
 * The benefit of this adapted approach is that it is easier to remember 
 * because the semantics is more clear!
 * 
 * @author Hongyan
 *
 */
public class DagOrdering {
	// Recursive function to find all topological orderings of a given DAG
	public static void findAllTopologicalOrders(
			Board board, List<List<Integer>> result) {
		if (board.isDone()) {
			board.save(result);
		}

		board.forEach(v-> {
			board.set(v);
			findAllTopologicalOrders(board, result);
			board.unset(v);
		});
	}

	// Print all topological orderings of a given DAG
	public static void printAllTopologicalOrders(Graph graph, List<List<Integer>> result) {
		// create the solution board
		Board board = new Board(graph);
		
		// find all topological ordering and print them
		findAllTopologicalOrders(board, result);
	}

	public static void main(String[] args) {
		List<Edge> edges = Arrays.asList(new Edge(0, 6), new Edge(1, 2), new Edge(1, 4), new Edge(1, 6), new Edge(3, 0),
				new Edge(3, 4), new Edge(5, 1), new Edge(7, 0), new Edge(7, 1));
		Graph graph = new Graph(edges, 8);
		List<List<Integer>> result = new ArrayList<>();
		
		printAllTopologicalOrders(graph, result);
		
		result.forEach(path-> System.out.println(path));
	}

	private static class Board {
		private final Graph graph;
		private final int count;
		private final List<Integer> path = new ArrayList<>();
		private final boolean[] discovered;
		
		Board(Graph graph) {
			this.graph = graph;
			this.count = graph.adjList.size();
			discovered = new boolean[count];
		}
		
		void forEach(Consumer<Integer> handler) {
			for (int v = 0; v < count; v++) {
				// valid choices are undiscovered 0-in-degree
				if (graph.indegree.get(v) == 0 && !discovered[v]) {
					handler.accept(v);
				}
			}
		}
		
		public void save(List<List<Integer>> result) {
			// save the result or we can simply print as in the original implementation
			result.add(new ArrayList<Integer>(path));
		}

		boolean isDone() {
			// path is full, done!
			return path.size()==count;
		}
		
		void set(int v) {
			for (int u : graph.adjList.get(v)) {
				// for all adjacent nodes, reduce the in-degree
				graph.indegree.set(u, graph.indegree.get(u) - 1);
			}

			// include current node in the path and mark it as discovered
			path.add(v);
			discovered[v] = true;
		}
		
		void unset(int v) {
			// do the reverse of the set method
			for (int u : graph.adjList.get(v)) {
				graph.indegree.set(u, graph.indegree.get(u) + 1);
			}

			path.remove(path.size() - 1);
			discovered[v] = false;
		}
	}

	//data structure to store graph edges
	private static class Edge {
		int source, dest;

		public Edge(int source, int dest) {
			this.source = source;
			this.dest = dest;
		}
	}

	//class to represent a graph object
	private static class Graph {
		// A List of Lists to represent an adjacency list
		final List<List<Integer>> adjList = new ArrayList<>();

		// stores in-degree of a vertex.
		// IT IS IMPORTANT THAT WE CAN CHANGE IT
		List<Integer> indegree = null;

		Graph(List<Edge> edges, int n) {
			for (int i = 0; i < n; i++) {
				adjList.add(i, new ArrayList<>());
			}

			// initialize in-degree of each vertex by 0
			indegree = new ArrayList<>(Collections.nCopies(n, 0));

			// add edges to the undirected graph
			for (int i = 0; i < edges.size(); i++) {
				int src = edges.get(i).source;
				int dest = edges.get(i).dest;
				adjList.get(src).add(dest);

				// increment in-degree of destination vertex by 1
				indegree.set(dest, indegree.get(dest) + 1);
			}
		}
	}
}

