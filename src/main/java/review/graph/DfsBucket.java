package review.graph;

import java.util.Arrays;
import java.util.Stack;
import java.util.function.Consumer;

// create a bucket used to do DFS
// the differences from DFS are
// 1) here we use Stack but in BFS we use Queue
// 2) we mark the node as discovered as we process it
// Both are adding the node into the bucket if it is never discovered
public class DfsBucket implements Bucket<Integer> {
	private final boolean[] discovered;
	private final Stack<Integer> stack = new Stack<>();

	public DfsBucket(int count) {
		this.discovered = new boolean[count];
	}

	@Override
	public boolean add(Integer node) {
		if (!discovered[node]) {
			stack.push(node);
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public void offer(Consumer<Integer> processor) {
		Integer node = stack.pop();
		// mark vertex as discovered so we don't add duplicate
		discovered[node] = true;
		// add vertex into the stack
		processor.accept(node);
	}

	@Override
	public void clear() {
		Arrays.fill(discovered, false);
		stack.clear();
	}
}
