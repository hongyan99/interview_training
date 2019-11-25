package sorting.practice;

import java.util.Arrays;

public class MergeArrays {
	public static void main(String[] args) {
		int[] arr1 = new int[] {1, 3, 5};
		int[] arr2 = new int[] {2, 4, 6, 0, 0, 0};
		mergeFirstIntoSecond(arr1, arr2);
		System.out.println(Arrays.toString(arr2));
	}
	
	private static void mergeFirstIntoSecond(int[] arr1, int[] arr2) {
		int i = arr1.length-1;
		int j = i;
		int index = arr2.length-1;
		while(i>=0 && j>=0) {
			if(arr1[i]>arr2[j]) {
				arr2[index--]=arr1[i];
				i--;
			} else {
				arr2[index--]=arr2[j];
				j--;
			}
		}
		if(i>=0) {
			System.arraycopy(arr1, 0, arr2, 0, i+1);
		}
	}
}
