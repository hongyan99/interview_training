package recursion;

import java.util.HashMap;
import java.util.Map;

public class BinarySearchTreeCounter {
	public static void main(String[] args) {
		long start = System.nanoTime();
		System.out.println(countTrees(10));
		System.out.println("Done in " + (System.nanoTime()-start) + " nano-seconds");
		start = System.nanoTime();
		System.out.println(countTreesDP(10, new HashMap<Integer, Integer>()));
		System.out.println("Done in " + (System.nanoTime()-start) + " nano-seconds");
	}
	
	private static int countTrees(int n) {
		if(n<=1) {
			return 1;
		}
		
		int count = 0;
		for(int i = 0; i < n; i++) {
			int leftTreeSize = i-0;
			int rightTreeSize = n-i-1;
			count += countTrees(leftTreeSize) * countTrees(rightTreeSize);
		}
		
		return count;
	}
	
	private static int countTreesDP(int n, Map<Integer, Integer> cache) {
		if(cache.get(n)!=null) {
			return cache.get(n);
		}
		if(n<=1) {
			return 1;
		}
		
		int count = 0;
		for(int i = 0; i < n; i++) {
			int leftTreeSize = i-0;
			int rightTreeSize = n-i-1;
			count += countTrees(leftTreeSize) * countTrees(rightTreeSize);
		}

		cache.put(n, count);
		return count;
	}
}
