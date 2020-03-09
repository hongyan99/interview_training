package review.graph;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;

// create a bucket used to do BFS
// the differences from DFS are
// 1) here we use Queue but in DFS we use Stack
// 2) we mark the node as discovered as we putting it into the bucket
// Both are adding the node into the bucket if it is never discovered
public class BfsBucket implements Bucket<Integer> {
	private final boolean[] discovered;
	private final Queue<Integer> q = new ArrayDeque<>();

	public BfsBucket(int count) {
		this.discovered = new boolean[count];
	}

	@Override
	public boolean add(Integer node) {
		if (!discovered[node]) {
			// mark vertex as discovered so we don't add duplicate
			discovered[node] = true;
			// add vertex into the queue
			q.add(node);
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return q.isEmpty();
	}

	@Override
	public void offer(Consumer<Integer> processor) {
		int node = q.poll();
		processor.accept(node);
	}
}
