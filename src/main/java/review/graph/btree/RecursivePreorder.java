package review.graph.btree;

import java.util.Arrays;
import java.util.List;

import review.graph.BTree;
import review.graph.BTree.Pair;

public class RecursivePreorder {
	// Perform iterative DFS on graph g starting from vertex v
	public static void recurse(BTree<Integer> tree, Integer node) {
		if(tree==null) {
			return;
		}
		
		System.out.print(node + " ");
		Pair<Integer> pair = tree.getChildren(node);
		if(pair!=null) {
			recurse(tree, pair.left);
			recurse(tree, pair.right);
		}
	};

	public static void main(String[] args) {
		List<Integer[]> edges = Arrays.asList(
			new Integer[] {0, 1, 2}, new Integer[] {1, 3, 4}
		);

		// create a graph from edges
		BTree<Integer> tree = new BTree<>(edges);
		recurse(tree, 0);
	}
}