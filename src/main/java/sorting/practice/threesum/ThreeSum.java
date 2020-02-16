package sorting.practice.threesum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given an integer array arr of size n, find all magic triplets in it.
 * 
 * Magic triplet is a group of three numbers whose sum is zero.
 * 
 * Note that magic triplets may or may not be made of consecutive numbers in arr.
 * 
 * @author Hongyan
 *
 */
public class ThreeSum {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(find3Sums(new int[] {})));
		System.out.println(Arrays.toString(find3Sums(new int[] {10, 3, -4, 1, -6, 9})));
		System.out.println(Arrays.toString(find3Sums(new int[] {12, 34, -46})));
		System.out.println(Arrays.toString(find3Sums(new int[] {0, 0, 0})));
		System.out.println(Arrays.toString(find3Sums(new int[] {-2, 2, 0 -2, 2})));
	}
	
	private static String[] find3Sums(int[] arr) {
		Set<String> result = new HashSet<String>();
		Arrays.sort(arr);
		for(int i = 0; i<arr.length-2; i++) {
			for(int j=i+1; j<arr.length-1; j++ ) {
				int twoSum = arr[i]+arr[j];
				for(int k=j+1; k<arr.length; k++) {
					int sum = twoSum+arr[k];
					if(sum==0) {
						result.add(""+arr[i]+","+arr[j]+","+arr[k]);
						break;
					}
				}
			}
		}
		return result.toArray(new String[result.size()]);
	}
}
