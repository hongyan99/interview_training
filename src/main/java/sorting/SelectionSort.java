package sorting;

import java.util.Arrays;

public class SelectionSort {
	public static void main(String[] args) {
		int[] arr = new int[] {7, 5, 4, 10, 2};
		iterFromLeft(arr);
		System.out.println(Arrays.toString(arr));
		arr = new int[] {7, 5, 0, 4, 10, 2};
		iterFromRight(arr);
		System.out.println(Arrays.toString(arr));
		arr = new int[] {7, 5, 0, 4, 10, 2};
		recurseFromLeft(arr);
		System.out.println(Arrays.toString(arr));
		arr = new int[] {7, 5, 0, 4, 10, 2};
		recurseFromRight(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	private static void iterFromLeft(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			findKthMin(arr, i+1);
		}
	}
	
//	E0, E1, E2, ..., E(k-1), Ek, ... En
//	E0, E1, E2, ..., E(k-1) are the first k elements that are already sorted
//	These k already sorted elements should not be touched
//
//	1. minIdx = k-1, min = E(k-1)
//	2. Loop i from k forward
//	3. If Ei<min then minIdx = i, min = Ei
//	4. After the loop, assign E(minIdx) with value in Ek, assign Ek with min
	private static void findKthMin(int [] arr, int k) {
		// assumption: the first k (indices: 0, 1, 2, ...k-1) elements are all smaller and already sorted
		// assume that element k-1 is the min and compare the rest on the right with min
		int minIdx = k-1;
		int min = arr[minIdx];
		
		// loop from k+1 forward
		for(int i = k; i < arr.length; i++) {
			if(arr[i] < min) {
				min = arr[i];
				minIdx = i;
			}
		}
		
		// swap element k with the min of the sub array starting from k.
		arr[minIdx] = arr[k-1];
		arr[k-1] = min;
	}
	
	private static void iterFromRight(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			findKthMax(arr, i+1);
		}
	}
	
//	E0, E1, E2, ..., E(n-k), E(n-k+1), ..., En
//	..............., k+1,    kth, ........, 1st
//	E(n-k+1), E(n-k+2), ..., En are the last k elements and that already sorted
//	These k already sorted elements should not be touched
//
//	1. maxIdx = n-k, min = E(n-k)
//	2. Loop i from E(n-k-1) backward
//	3. If Ei>max then maxIdx = i, max = Ei
//	4. After the loop, assign E(minIdx) with value in E(n-k), assign E(n-k) with max
	private static void findKthMax(int [] arr, int k) {
		// assumption: the elements after arr.length-k+1 are all larger and already sorted
		// assume that element arr.length-k is the max of the sub array 
		// and compare the rest on the left with max
		int maxIdx = arr.length-k;
		int max = arr[maxIdx];
		
		// loop from k-2 backward
		for(int i = arr.length-k-1; i > -1; i--) {
			if(arr[i] > max) {
				max = arr[i];
				maxIdx = i;
			}
		}
		
		// swap element arr.length-k with the max of the sub array 
		// starting from 0 and ending at arr.length-k.
		arr[maxIdx] = arr[arr.length-k];
		arr[arr.length-k] = max;
	}
	
	private static void recurseFromLeft(int[] arr) {
		// equivalent to the problem of finding the Nth minimum, N=arr.length
		recurseKthMin(arr, arr.length);
	}

//	The smallest elements is stored to the immediate left of the sub array
//	
//	1. Terminal condition: if k==0, return as is
//	2. Reduce from k to k-1
//	3. minIdx = k-1, min = E(k-1)
//	4. Loop i from k forward
//	5. If Ei<min then minIdx = i, min = Ei
//	6. After the loop, assign E(minIdx) with value in Ek, assign Ek with min
	private static void recurseKthMin(int [] arr, int k) {
		// there is no such thing as the 0th minimum so exit
		if(k==0) {
			return;
		}

		// reduce the problem of finding the K-1th minimum
		recurseKthMin(arr, k-1);
		int minIdx = k-1;
		int min = arr[minIdx];
		for(int i = k; i < arr.length; i++) {
			if(arr[i] < min) {
				min = arr[i];
				minIdx = i;
			}
		}

		// swap the min with the Kth element (at index k-1)
		arr[minIdx] = arr[k-1];
		arr[k-1] = min;
	}
	
	private static void recurseFromRight(int[] arr) {
		// equivalent to the problem of finding the Nth maximum, N=arr.length
		recurseKthMax(arr, arr.length);
	}

//	The largest elements is stored to the immediate right of the sub array
//
//	1. Terminal condition: if k==0, return as is
//	2. Reduce from k to k-1
//	3. maxIdx = n-k, min = E(n-k)
//	4. Loop i from E(n-k-1) backward
//	5. If Ei>max then maxIdx = i, max = Ei
//	6. After the loop, assign E(minIdx) with value in E(n-k), assign E(n-k) with max
	private static void recurseKthMax(int [] arr, int k) {
		// there is no such thing as the 0th maximum so exit
		if(k==0) {
			return;
		}

		// reduce the problem of finding the K-1th maximum
		recurseKthMax(arr, k-1);
		int maxIdx = arr.length-k;
		int max = arr[maxIdx];
		for(int i = arr.length-k-1; i >= 0; i--) {
			if(arr[i] > max) {
				max = arr[i];
				maxIdx = i;
			}
		}

		// swap the max with the Kth to the last element (at index arr.length-k)
		arr[maxIdx] = arr[arr.length-k];
		arr[arr.length-k] = max;
	}
}
