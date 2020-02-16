package sorting.practice.groupnumbers;

import java.util.Arrays;

public class GroupNumbers2 {
	public static void main(String[] args) {
		test(new int[] {});
		test(new int[] {2,2,1,4,3,5,6,7,8});
		test(new int[] {1,1,2,3,4,7,2,1});
	}
	
	private static void groupOddVsEven(int[] arr) {
		// left==index of first odd number
		int left = -1, right = arr.length;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i]%2!=0) {
				left = i;
				break;
			}
		}
		if(left==-1) {
			return;
		}
		
		// right==index of first even number
		for(int i = arr.length-1; i >= 0; i--) {
			if(arr[i]%2==0) {
				right = i;
				break;
			}
		}
		if(right==arr.length) {
			return;
		}

		while(left<right) {
			int temp = arr[left];
			arr[left] = arr[right];
			arr[right] = temp;
			left++;
			while(arr[left]%2==0) {
				left++;
			}
			right--;
			while(arr[right]%2!=0) {
				right--;
			}
		}
	}
	
	private static void test(int[] arr) {
		groupOddVsEven(arr);
		System.out.println(Arrays.toString(arr));
	}
}
