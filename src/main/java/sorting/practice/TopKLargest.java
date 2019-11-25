package sorting.practice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class TopKLargest {
	public static void main(String[] args) {
		int[] result = topK(new int[] {1,5,4,4,2,6,8,2,3,5,9,10,12,11}, 4);
		System.out.println(Arrays.toString(result));
		result = topK(new int[] {1,5,1,5,1}, 3);
		System.out.println(Arrays.toString(result));
	}
	
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
