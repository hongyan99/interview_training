package sorting.practice;

import java.util.Arrays;

public class GroupNumbers {
	public static void main(String[] args) {
		test(new int[] {});
		test(new int[] {2,2,1,4,3,5,6,7,8});
		test(new int[] {1,1,2,3,4,7,2,1});
	}
	
	// return the start of the first even number
	private static int groupOddVsEven(int[] arr) {
		int index = -1;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i]%2!=0) {
				if(index==-1) {
					index = i;
				}
			} else {
				if(index!=-1) {
					int temp = arr[index];
					arr[index] = arr[i];
					arr[i] = temp;
					index++;
				}
			}
		}
		
		return index;
	}
	
	private static void test(int[] arr) {
		int result = groupOddVsEven(arr);
		System.out.println(Arrays.toString(arr));
		System.out.println("index="+result);
	}
}
