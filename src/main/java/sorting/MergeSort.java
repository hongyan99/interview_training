package sorting;

import java.util.Arrays;

public class MergeSort {
	public static void main(String[] args) {
		int[] arr = new int[] {7, 5, 0, 4, 10, 2};
		mergesort(arr);
		System.out.println(Arrays.toString(arr));
		arr = new int[] {6, 4, 3, 8, 1, 5, 2, 7};
		mergesort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
//	Terminal condition: if array length less than 2, return
//	Find the mid point of the array (index middle)
//	Sort the first half and the second half
//	Merge the two halves
//
//	0. Initially sort from start = 0 to end = n
//	1. Terminal condition end-start<2, return
//	2. Assign (start+end-1)/2 + 1 to middle
//	3. Divide to two sub-problems
//	4. First sub-problem sort from start to middle
//	5. Second sub-problem sort from middle to end
//	6. Merge two result of the sub-problems: elements in (start, middle) and (middle, end)
	private static void mergesort(int [] arr) {
		mergesort(arr, 0, arr.length);
	}
	
	private static void mergesort(int [] arr, int start, int end) {
		if(end-start < 2) {
			return;
		}
		
		int middle = (start+end-1) / 2 + 1;
		mergesort(arr, start, middle);
		mergesort(arr, middle, end);
		merge(arr, start, middle, end);
	}
	
	private static void merge(int[] arr, int start, int middle, int end) {
		// left and right leg pointer
		int left = start;
		int right = middle;

		// temp array to hold the new sorted elements
		int [] temp = new int[end-start];

		// the index of the last element placed into temp
		int last = start;
		
		// start at index 0 for temp array
		int index = 0;
		
		// walking the two sub array elements with two legs and place the smaller element into temp
		while(left<middle && right<end) {
			if( arr[left] <= arr[right]) {
				temp[index++] = arr[left++];
				last = right;
			} else {
				temp[index++] = arr[right++];
				last = left;
			}
		}
		
		// copy the remaining of the slower leg into temp
		if(last < middle) {
			System.arraycopy(arr, last, temp, index, middle-last);
		} else {
			System.arraycopy(arr, last, temp, index, end-last);
		}
		
		// copy the sorted in temp back to the array
		System.arraycopy(temp, 0, arr, start, end-start);
	}
}
