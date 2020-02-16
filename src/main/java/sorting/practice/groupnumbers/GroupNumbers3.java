package sorting.practice.groupnumbers;

import java.util.Arrays;

public class GroupNumbers3 {
	public static void main(String[] args) {
		test(new int[] {});
		test(new int[] {2,2,1,4,3,5,6,7,8});
		test(new int[] {1,1,2,3,4,7,2,1});
	}
	
	private static void groupOddVsEven(int[] arr) {
		int left = 0, right = arr.length-1;
		while(left<right) {
			if(arr[right]%2==0) {
				int temp = arr[right];
				arr[right] = arr[left];
				arr[left] = temp;
				left++;
			} else {
				right--;
			}
		}
	}
	
	private static void test(int[] arr) {
		groupOddVsEven(arr);
		System.out.println(Arrays.toString(arr));
	}
}
