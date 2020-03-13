package review.graph.dfs;

import java.util.Arrays;
import java.util.List;

import review.graph.Bucket;
import review.graph.DfsBucket;
import review.graph.Edge;
import review.graph.Graph;

public class IterativeDfs {
	// Perform iterative DFS on graph g starting from vertex v
	public static void dfs(Graph graph, Bucket<Integer> bucket) {
		bucket.iterate(
				n->System.out.print(n + " "), 
				n->graph.getAdjList().get(n));
	};

	// Iterative Java implementation of Depth first search
	public static void main(String[] args) {
		// List of graph edges as per above diagram
		List<Edge> edges = Arrays.asList(
				// Notice that node 0 is unconnected node
				new Edge(1, 2), new Edge(1, 7), new Edge(1, 8), new Edge(2, 3), new Edge(2, 6), new Edge(3, 4),
				new Edge(3, 5), new Edge(8, 9), new Edge(8, 12), new Edge(9, 10), new Edge(9, 11)
		// , new Edge(6, 9) // introduce cycle
		);

		// Set number of vertices in the graph (0-12)
		final int n = 13;

		// create a graph from edges
		Graph graph = new Graph(edges, n);

		// stores vertex is discovered or not
		Bucket<Integer> bucket = new DfsBucket(n);

		// Do iterative DFS traversal from all undiscovered nodes to
		// cover all unconnected components of graph
		for (int i = 0; i < n; i++) {
			if (bucket.add(i)) {
				// start DFS traversal from vertex i
				dfs(graph, bucket);
			}
		}
	}
}