package sorting.practice.topklargest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * You are given an array of integers arr, of size n, which is analogous to a 
 * continuous stream of integers input. Your task is to find K largest elements 
 * from a given stream of numbers.
 * 
 * By definition, we don't know the size of the input stream. Hence, produce K 
 * largest elements seen so far, at any given time. For repeated numbers, return 
 * them only once.
 * 
 * If there are less than K distinct elements in arr, return all of them.
 * 
 * @author Hongyan
 *
 */
public class TopKLargest {
	public static void main(String[] args) {
		int[] result = topK(new int[] {1,5,4,4,2,6,8,2,3,5,9,10,12,11}, 4);
		System.out.println(Arrays.toString(result));
		result = topK(new int[] {1,5,1,5,1}, 3);
		System.out.println(Arrays.toString(result));
	}
	
	// The basic idea is to always keep the largest K numbers in a minHeap,
	// loop through all numbers, the keep placing the number into the minHeap.
	// keep only maximum of K numbers in the heap. At the end of the loop,
	// the top K numbers should be left. Than we simply fetch the numbers 
	// from the heap in order to get an orders list.
	// For uniqueness, simply keep all numbers in the heap also in a Set
	// all the time, and check whether the next number already exists
	// in the Set.
	private static int[] topK(int[] arr, int k) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();
		Set<Integer> uniqueValues = new HashSet<>();
		
		for(int i = 0; i<arr.length; i++) {
			Integer peek = minHeap.size()>=k? minHeap.peek() : null;
			if(peek!=null && peek>=arr[i]) {
			    continue;
			}
			if(uniqueValues.contains(arr[i])) {
				continue;
			}
			uniqueValues.add(arr[i]);
			minHeap.add(arr[i]);
			if(minHeap.size()>k) {
				minHeap.poll();
				uniqueValues.remove(arr[i]);
			}
		}
		Integer[] returns = new Integer[minHeap.size()];
		returns = minHeap.toArray(returns);
		return Arrays.stream(returns).mapToInt(Integer::intValue).toArray();
	}
}
