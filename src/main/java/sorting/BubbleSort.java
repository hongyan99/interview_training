package sorting;

import java.util.Arrays;

public class BubbleSort {
	public static void main(String[] args) {
		int[] arr = new int[] {7, 5, 4, 10, 2};
		sortRecursion(arr);
		System.out.println(Arrays.toString(arr));
		arr = new int[] {7, 5, 0, 4, 10, 2};
		sortIterate(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	private static void sortRecursion(int [] arr) {
		if(arr.length==1) {
			return;
		}
		
		// bubble the nth element
		bubble(arr, arr.length);
	}
	
//	Bubble up the kth element, assuming that every time, the smallest element bubble up
//	Assuming that the lightest k-1 elements are all bubbled up.
//	
//	1. The terminal condition is k=0
//	2. Reduce to bubble element k-1
//	3. Loop i from the last element back to element k+1 (index k since we are 0 based)
//	4. Compare element i with i-1, if Ei<E(i-1), swap the two
	private static void bubble(int[] arr, int k) {
		// no such element to bubble, return
		if(k==0) {
			return;
		}
		
		// reduce to bubble element k-1
		bubble(arr, k-1);
		
		// bubble the kth (k=1,2,...arr.length) element
		for(int i = arr.length-1; i >= k; i--) {
			if(arr[i-1]>arr[i]) {
				int temp = arr[i];
				arr[i] = arr[i-1];
				arr[i-1] = temp;
			}
		}
	}
	
//	E0, E1, E2, ..., E(k-2), E(k-1), Ek, ..., En
//	We bubbling 1st, 2nd, 3rd, ... in order (k=1, 2, 3, ..., n)
//	For each k, E0, E1, ..., E(k-2) are already sorted and should NOT be touched
//	For each k we loop from the end of array to E(k-1)
//	
//	1. Loop k from 1 to n (means bubble the kth element)
//	2. Loop i from arr.length-1, back to one element before the (k+1)th element (index k)
//	3. Compare element i with i-1, if Ei<E(i-1), swap the two
	private static void sortIterate(int[] arr) {
		// loop to bubble 1st, 2nd, 3rd, ... nth element in order
		for(int k = 1; k < arr.length; k++) {
			// bubble the kth element, flip lighter element by
			// looping from the tail of the array moving to the head
			// all elements before k-1 are already in the proper order
			for(int i = arr.length-1; i >= k; i--) {
				if(arr[i] < arr[i-1]) {
					int temp = arr[i-1];
					arr[i-1] = arr[i];
					arr[i] = temp;
				}
			}
		}
	}
}