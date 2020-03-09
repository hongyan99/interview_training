package review.graph;

import java.util.function.Consumer;

/**
 * A bucket that stores nodes. This can be used by traversal of graphs.
 * For BFS, use BfsBucket, for DFS, use DfsBucket. To iterate, use the
 * iterate method, recursion, use recurse method.
 * 
 * @author Hongyan
 *
 * @param <N> the data type for the node to store in this bucket.
 */
public interface Bucket<N> {
	/**
	 * Adds a node to the bucket.
	 * 
	 * @param node the node to add.
	 * @return true if the node is added. False otherwise.
	 */
	boolean add(N node);
	/**
	 * Offer the next node in the bucket to process.
	 * 
	 * @param processor to function to process a node 
	 */
	void offer(Consumer<N> processor);
	
	/**
	 * Is the bucket empty?
	 * 
	 * @return true if it is empty.
	 */
	boolean isEmpty();
	
	default void iterate(Consumer<N> processor, AdjacencyList<N> adjList) {
		// run till bucket is not empty
		while (!isEmpty()) {
			offer(node -> {
				processor.accept(node);
				// do for every edge (v -> u)
				for (N u : adjList.getList(node)) add(u);
			});
		}
	}
	
	default void recurse(
			Consumer<N> processor, AdjacencyList<N> adjList, Consumer<N> recurse) {
		if (isEmpty()) return;

		// pop front node from queue and print it
		offer(node -> {
			processor.accept(node);
			// do for every edge (v -> u)
			for (N u : adjList.getList(node)) add(u);

			recurse.accept(node);
		});
	}
}
