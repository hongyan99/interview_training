package review.graph.bfs;

import java.util.Arrays;
import java.util.List;

import review.graph.BfsBucket;
import review.graph.Bucket;
import review.graph.Edge;
import review.graph.Graph;

class RecursiveBfs {
	// Perform BFS recursively on graph
	public static void bfs(Graph graph, Bucket<Integer> bucket) {
		bucket.recurse(
				n->System.out.print(n + " "), 
				n->graph.getAdjList().get(n),
				n->bfs(graph, bucket));
	}

	// Recursive Java implementation of Breadth first search
	public static void main(String[] args) {
		// List of graph edges as per above diagram
		List<Edge> edges = Arrays.asList(new Edge(1, 2), new Edge(1, 3), new Edge(1, 4), new Edge(2, 5), new Edge(2, 6),
				new Edge(5, 9), new Edge(5, 10), new Edge(4, 7), new Edge(4, 8), new Edge(7, 11), new Edge(7, 12)
		// vertex 0, 13 and 14 are single nodes
		);

		// Set number of vertices in the graph
		final int n = 15;

		// create a graph from edges
		Graph graph = new Graph(edges, n);

		// stores vertex is discovered or not
		Bucket<Integer> bucket = new BfsBucket(n);

		// Do BFS traversal from all undiscovered nodes to
		// cover all unconnected components of graph
		for (int i = 0; i < n; i++) {
			if (bucket.add(i)) {
				// start BFS traversal from vertex i
				bfs(graph, bucket);
			}
		}
	}
}
