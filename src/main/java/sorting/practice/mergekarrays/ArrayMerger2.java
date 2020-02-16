package sorting.practice.mergekarrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

import sorting.ArrayStreamMerger;

/**
 * Problem: <p/>
 * 
 * Given an array of integer arrays, assuming that each individual array is sorted 
 * in ascending order, merge them into one single integer array.
 * 
 * The output is expected to be an int array.
 * 
 * A similar implementation but works with input as InputStream array is implemented in 
 * {@link ArrayStreamMerger}.
 * 
 * @author Hongyan
 *
 */
public class ArrayMerger2 {
	public static void main(String[] args) {
		int[] a1 = new int[] {34, 26, 20, 13, 11, 7, 4, 4};
		int[] a2 = new int[] {41, 34, 27, 23, 19, 10, 8, 0};
		int[] a3 = new int[] {26, 25, 19, 12, 7, 7, 7, 5};
		int[] a4 = new int[] {46, 39, 35, 33, 27, 19, 12, 9};
		int[] a5 = new int[] {33, 24, 22, 18, 18, 10, 3, 0};
		int[] a6 = new int[] {42, 35, 35, 30, 21, 20, 12, 9};
		int[] a7 = new int[] {42, 33, 24, 21, 12, 12, 8, 7};
		int[] a8 = new int[] {29, 23, 21, 18, 18, 11, 8, 7};
		int[] a9 = new int[] {35, 30, 30, 23, 15, 14, 8, 7};
		int[] a10 = new int[] {20, 18, 17, 16, 12, 11, 5, 4};
		int[] result = merge(a1, a2, a3, a4,a5,a6,a7,a8,a9,a10);
		System.out.println(Arrays.toString(result));
	}

// Borrowed from IKWeb implementation, with modification
//	1. Place element 0 of all arrays in the a min heap, together with each element, reference to the array it belongs to
//	2. Get the min from the heap, add the next element the min belongs to to the heap
//	3. Repeat the above until all elements of all arrays are used.
//	
//	The time complexity of this algorithm is N*K*logK since we have to go through all N*K elements,
//	and each element we insert into the heap takes logK time, fetch an element from the heap is 
//	also logK time.
//	
//	The space complexity is the size of the min heap K.	
	private static int[] merge(int[] ... arr) {
		final int rows = arr.length;
		final int columns = arr[0].length;
		PriorityQueue<ArraySource> queue = createQueue(arr);
		for(int i = 0; i < arr.length; i++) {
			queue.add(new ArraySource(arr, i, 0));
		}
		int[] result = new int[rows*columns];
		int index = 0;
		while(!queue.isEmpty()) {
			ArraySource as = queue.poll();
			result[index++] = as.getValue();
			if(as.inc()) {
				queue.add(as);
			}
		}
		return result;
	}
	
	private static PriorityQueue<ArraySource> createQueue(int[][] arr) {
		Boolean isIncreasing = null;
		for(int i = 0; i < arr.length || isIncreasing==null; i++) {
			if(arr[i][0] < arr[i][arr[i].length-1]) {
				isIncreasing = Boolean.TRUE;
			}
			if(arr[i][0] > arr[i][arr[i].length-1]) {
				isIncreasing = Boolean.FALSE;
			}
		}
		if(Boolean.FALSE.equals(isIncreasing)) {
			return new PriorityQueue<>(Collections.reverseOrder());
		}
		return new PriorityQueue<>();
	}

	private static class ArraySource implements Comparable<ArraySource> {
		private final int[][] arr;
		private final int row;
		private int column;
		
		public ArraySource(int[][] arr, int row, int column) {
			this.arr = arr;
			this.row = row;
			this.column = column;
		}

		@Override
		public int compareTo(ArraySource o) {
			return Long.compare(getValue(), o.getValue());
		}

		public int getValue() {
			return arr[row][column];
		}
		
		private boolean inc() {
			column++;
			return column<arr[row].length;
		}
	}
}
