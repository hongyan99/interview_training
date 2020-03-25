package review.graph.topologicalsort;

import java.util.Arrays;
import java.util.List;

import review.graph.Edge;
import review.graph.Graph;

public class TopologicalSort {
	public static void main(String[] args) {
		// List of graph edges as per above diagram
		List<Edge> edges = Arrays.asList(
								new Edge(0, 6), new Edge(1, 2),
								new Edge(1, 4), new Edge(1, 6),
								new Edge(3, 0), new Edge(3, 4),
								new Edge(5, 1), new Edge(7, 0),
								new Edge(7, 1)
							);

		// Number of nodes in the graph
		int N = 8;

		// create a graph from edges
		Graph graph = new Graph(edges, N);

		// print all topological ordering of the graph
		printAllTopologicalOrders(graph);
	}

	private static void printAllTopologicalOrders(Graph graph) {
		// TODO Auto-generated method stub
		
	}
}
