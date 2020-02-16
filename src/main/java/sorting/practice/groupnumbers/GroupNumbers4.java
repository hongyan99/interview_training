package sorting.practice.groupnumbers;

import java.util.Arrays;

public class GroupNumbers4 {
	public static void main(String[] args) {
		test(new int[] {});
		test(new int[] {2,2,1,4,3,5,6,7,8});
		test(new int[] {1,1,2,3,4,7,2,1});
	}
	
	// The beauty of this approach is it simply keep a pointer 
	// that is the index of the last know even number + 1
	private static int[] groupOddVsEven(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        
        // Here i keeps the index of the last known even number + 1.
        // Then the solution becomes simply find the next even number 
        // and place it at the pointer and move the pointer forward.
        int i = 0;
        for (int j = 0; j < arr.length; j++) {
          if (arr[j]%2 == 0) {
            swap(arr, i, j);
            i++;
          }
        }
        return arr;
	}
	
    static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
	private static void test(int[] arr) {
		groupOddVsEven(arr);
		System.out.println(Arrays.toString(arr));
	}
}
