package review.backtracking;

import java.util.concurrent.atomic.AtomicLong;

// NOT FINISHED
public class BinarySearchTreeCounter {
	public static void main(String[] args) {
		System.out.println(how_many_BSTs(10));
	}

	private static long how_many_BSTs(int n) {
		AtomicLong counter = new AtomicLong();
		solve(0, 0, n, counter);
		return counter.get();
	}

	private static void solve(int left, int right, int n, AtomicLong counter) {
		if(left+right==n-1) {
			counter.incrementAndGet();
			return;
		}
		
		solve(left+1, right, n, counter);
		solve(left, right+1, n, counter);
		if(n-left-right>2) {
			solve(left+1, right+1, n, counter);
		}
	}
}
