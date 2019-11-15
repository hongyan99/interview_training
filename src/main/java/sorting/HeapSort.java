package sorting;

import java.util.Arrays;

public class HeapSort {
	public static void main(String[] args) {
		int[] arr = new int[] {7, 5, 0, 4, 10, 2};
		sort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	private static void sort(int[] arr) {
		MinHeap heap = MinHeap.heapify(arr);
		int[] sorted = new int[arr.length];
		int count = 0;
		while(!heap.isEmpty()) {
			sorted[count++] = heap.pop();
		}
		
		System.arraycopy(sorted, 0, arr, 0, arr.length);
	}
}
