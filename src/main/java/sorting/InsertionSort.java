package sorting;

import java.util.Arrays;

public class InsertionSort {
	public static void main(String[] args) {
		int[] arr = new int[] {7, 5, 4, 10, 2};
		sortRecursion(arr);
		System.out.println(Arrays.toString(arr));
		arr = new int[] {7, 5, 0, 4, 10, 2};
		sortIterate(arr);
		System.out.println(Arrays.toString(arr));
	}

//	E0, E1, E2, ..., E(k-2), E(k-1), Ek, ..., En
//	We insert 1st, 2nd, 3rd, ... in order (k=1, 2, 3, ..., n)
//	For each k, E0, E1, ..., E(k-2) already sorted and we are inserting E(k-1) into it
//	For each k we loop from the end of array to E(k-2)
//	
//	1. Loop k from 2 to n (we start from 2 since when k=1, no need to insert)
//	2. Save element at k-1 to temp
//	3. Loop i from k-2 to 0
//	4. Compare temp with Ei, if temp is smaller, shift Ei one index higher
//	5. Else record i+1 as the ip (insertion point)
//	6. After the loop for i assign temp to E(ip)	
	private static void sortIterate(int[] arr) {
		// Loop from 2 to n
		for(int k = 2; k <= arr.length; k++) {
			// store the kth element (at index k-1)
			int temp = arr[k-1];
			
			// init insert point at index 0 (if the element to insert
			// is smaller than all sorted elements)
			int ip = 0;
			
			// Loop
			for(int i = k-2; i > -1; i--) {
				if(temp < arr[i]) {
					arr[i+1] = arr[i];
				} else {
					ip = i+1;
					break;
				}
			}
			arr[ip] = temp;
		}
	}

	private static void sortRecursion(int[] arr) {
		// insert the nth element
		recurse(arr, arr.length);
	}

//	Assuming that the (k-1)th element already insterted to a sorted array
//	Which means that element at index 0 to k-2 are sorted.
//	Now insert the kth element it so it grows to k sorted elements (0 to k-1).
//	
//	1. Terminal condition is k=1
//	2. Reduce to insert the (k-1)th element
//	3. Store the element to insert (at index k-1) to temp
//	3. Loop i from index k-2 to 0, backward
//	4. If temp < Ei, shift element Ei to E(i+1)
//	5. Otherwise, record i+1 as the ip (insertion point) to insert temp
//	6. After the loop, assign temp to E(ip)
	private static void recurse(int[] arr, int k) {
		// insert the first element is a nil operation
		if(k == 1) {
			return;
		}
		
		// reduce to insert the (k-1)th element
		recurse(arr, k-1);
		
		// element to insert: the kth element (at index k-1)
		int temp = arr[k-1];
		
		// all elements from 0 to k-2 are smaller than temp, we need to insert it at index 0
		int ip = 0;
		
		// loop from i=k-2 backward to 0 
		for(int i = k-2; i > -1; i--) {
			if(temp < arr[i]) {
				// if the element to insert is smaller, shift the element i one index higher
				arr[i+1] = arr[i];
			} else {
				// if the element to insert is larger, place it right after element i
				ip = i+1;
				break;
			}
		}
		arr[ip] = temp;
	}
	
	

}
