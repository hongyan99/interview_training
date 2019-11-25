package sorting.practice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ThreeSum {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(find3Sums(new int[] {})));
		System.out.println(Arrays.toString(find3Sums(new int[] {10, 3, -4, 1, -6, 9})));
		System.out.println(Arrays.toString(find3Sums(new int[] {12, 34, -46})));
		System.out.println(Arrays.toString(find3Sums(new int[] {0, 0, 0})));
		System.out.println(Arrays.toString(find3Sums(new int[] {-2, 2, 0 -2, 2})));
		System.out.println(Arrays.toString(find3SumsOptimized(new int[] {})));
		System.out.println(Arrays.toString(find3SumsOptimized(new int[] {10, 3, -4, 1, -6, 9})));
		System.out.println(Arrays.toString(find3SumsOptimized(new int[] {12, 34, -46})));
		System.out.println(Arrays.toString(find3SumsOptimized(new int[] {0, 0, 0})));
		System.out.println(Arrays.toString(find3SumsOptimized(new int[] {-2, 2, 0 -2, 2})));
	}
	
	private static String[] find3Sums(int[] arr) {
		Set<String> result = new HashSet<String>();
		Arrays.sort(arr);
		for(int i = 0; i<arr.length; i++) {
			for(int j=i+1; j<arr.length; j++ ) {
				int twoSum = arr[i]+arr[j];
				String strSum = ""+arr[i]+","+arr[j]+",";
				for(int k=j+1; k<arr.length; k++) {
					if(twoSum+arr[k]==0) {
						result.add(strSum+arr[k]);
					}
				}
			}
		}
		return result.toArray(new String[result.size()]);
	}
	
	private static String[] find3SumsOptimized(int[] arr) {
		Set<String> result = new HashSet<String>();
		Arrays.sort(arr);
		if(arr.length>=3) {
			for(int i = 0; i<arr.length; i++) {
				int left = i+1;
				int right = arr.length-1;
				while(left<right) {
					int sum = arr[i]+arr[left]+arr[right];
					if(sum==0) {
						result.add(arr[i]+","+arr[left]+","+arr[right]);
						left++;
						right--;
					} else if (sum>0) {
						right--;
					} else {
						left++;
					}
				}
			}
		}
		return result.toArray(new String[result.size()]);
	}
}
