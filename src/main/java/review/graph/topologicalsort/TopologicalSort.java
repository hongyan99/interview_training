package review.graph.topologicalsort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//data structure to store graph edges
class Edge
{
	int source, dest;

	public Edge(int source, int dest) {
		this.source = source;
		this.dest = dest;
	}
};

//class to represent a graph object
class Graph {
	// A List of Lists to represent an adjacency list
	List<List<Integer>> adjList = null;

	// stores indegree of a vertex
	List<Integer> indegree = null;

	// Constructor
	Graph(List<Edge> edges, int N) {
		adjList = new ArrayList<>(N);
		for (int i = 0; i < N; i++) {
			adjList.add(i, new ArrayList<>());
		}

		// initialize indegree of each vertex by 0
		indegree = new ArrayList<>(Collections.nCopies(N, 0));

		// add edges to the undirected graph
		for (int i = 0; i < edges.size(); i++) {
			int src = edges.get(i).source;
			int dest = edges.get(i).dest;

			// add an edge from source to destination
			adjList.get(src).add(dest);

			// increment in-degree of destination vertex by 1
			indegree.set(dest, indegree.get(dest) + 1);
		}
	}

}
class TopologicalSort {
	// Pattern of back tracking here
	// 0. outer loop
	// 1. update state before recursion
	// 2. recursion
	// 3. un-update state after recursion (back-track)
	// 4. end of outer loop
	// 5. collect result (print result here)
	
	// Recursive function to find all topological orderings of a given DAG
	public static void findAllTopologicalOrders(Graph graph, List<Integer> path,
												boolean[] discovered, int n) {
		// 0. outer loop
		// do for every vertex
		for (int v = 0; v < n; v++) {
			// proceed only if in-degree of current node is 0 and
			// current node is not processed yet
			if (graph.indegree.get(v) == 0 && !discovered[v]) {
				// 1. update state before recursion
				// for every adjacent vertex u of v, reduce in-degree of u by 1
				for (int u: graph.adjList.get(v)) {
					graph.indegree.set(u, graph.indegree.get(u) - 1);
				}

				// include current node in the path and mark it as discovered
				path.add(v);
				discovered[v] = true;

				// 2. recursion
				// recur
				findAllTopologicalOrders(graph, path, discovered, n);

				// 3. un-update state after recursion (back-track)
				// backtrack: reset in-degree information for the current node
				for (int u: graph.adjList.get(v)) {
					graph.indegree.set(u, graph.indegree.get(u) + 1);
				}

				// backtrack: remove current node from the path and
				// mark it as undiscovered
				path.remove(path.size() - 1);
				discovered[v] = false;
			}
		}
		// 4. end of outer loop

		// 5. collect result (print result here)
		// print the topological order if all vertices are included in the path
		if (path.size() == n) {
			System.out.println(path);
		}
	}

	// Print all topological orderings of a given DAG
	public static void printAllTopologicalOrders(Graph graph)
	{
		// get number of nodes in the graph
		int N = graph.adjList.size();

		// create an auxiliary array to keep track of whether vertex is discovered
		boolean[] discovered = new boolean[N];

		// list to store the topological order
		List<Integer> path = new ArrayList<>();

		// find all topological ordering and print them
		findAllTopologicalOrders(graph, path, discovered, N);
	}

	public static void main(String[] args)
	{
		// List of graph edges as per above diagram
		List<Edge> edges = Arrays.asList(
			new Edge(0, 6), new Edge(1, 2),
			new Edge(1, 4), new Edge(1, 6),
			new Edge(3, 0), new Edge(3, 4),
			new Edge(5, 1), new Edge(7, 0),
			new Edge(7, 1)
		);

		// Number of nodes in the graph
		int n = 8;

		// create a graph from edges
		Graph graph = new Graph(edges, n);

		// print all topological ordering of the graph
		printAllTopologicalOrders(graph);
	}
}
