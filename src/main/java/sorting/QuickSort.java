package sorting;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
	public static void main(String[] args) {
		int[] arr = new int[] {7, 5, 0, 4, 10, 2};
		quickSort(arr);
		System.out.println(Arrays.toString(arr));
		arr = new int[] {7, 5, 0, 7, 5, 4, 10, 2};
		quickSortInplace(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	private static void quickSort(int[] arr) {
		partition(arr, 0, arr.length-1);
	}
	
//	Given start index and end index, and pivotIdx, do quick sort of the sub-array
//	
//	1. Terminal condition: if start>=end, return
//	2. Create a new temp array newArr that can hold all elements of the sub-array (end-start+1)
//	3. Store value at pivotIdx as pivotValue
//	4. init left with 0 and right with end-start+1
//	5. Loop i from start to end
//	6. if(i!=pivotIdx) do
//	7. if(pivotValue<arr[i]) place arr[i] at index left in newArr and increment left
//	8. Otherwise place arr[i] at index right in newArr and decrement right
//	9. After the loop, assign pivotValue at index left of newArr
//	10.Copy all elements of newArr to arr start from index start
//	11.Divide into two sub-problems
//	11a.The first sub-problem: start=start, end=start+left-1, pivot=start
//	11b.The second sub-problem: start=start+left+1, end=end, pivot=end
	private static void partition(int[] arr, int start, int end) {
		// Can no longer divide, so return
		if(start>=end) {
			return;
		}

		// create temp array to hold sorted sub-array
		int len = end-start+1;
		int[] newArr = new int[len];
		int left = 0;
		int right = newArr.length-1;
		
		// hold pivot value in pivotValue
		int pivotIdx = pickPivot(start, end);
		int pivotValue = arr[pivotIdx];
		
		// Loop from start to end to partition the values around pivotValue
		for(int i = start; i<=end; i++) {
			if(i!=pivotIdx) {
				if(pivotValue<arr[i]) {
					newArr[right--]=arr[i];
				} else {
					newArr[left++]=arr[i];
				}
			}
		}

		// Copy pivotValue into newArr
		newArr[left] = pivotValue;
		
		// Copy sorted values from newArr into arr starting from index start
		System.arraycopy(newArr, 0, arr, start, newArr.length);
		
		// Divide to two sub-problems
		// First sub-problem, new start=start, new end=start+left-1, pivotIdx=start
		partition(arr, start, start+left-1);
		// Second sub-problem, new start=start+left+1, new end=end, pivotIdx=end
		partition(arr, start+left+1, end);
	}
	
//	Given start index and end index, and pivotIdx, do quick sort of the sub-array
//	
//	1. Terminal condition: if start>=end, return
//	2. Store value at pivotIdx as pivotValue
//	4. init left with start and right with end
//	5. Loop while left<right
//	6. Loop while left less than pivotIdx, and value at left not greater, inc left
//	7. Loop while right greater than pivotIdx, and value at right not lesser, desc right
//	8. swap left and right element
//	9. if(left==pivotIdx) then pivotIdx=right and inc left
//	10.else if(right==pivotIdx) then pivotIdx=left and desc right
//	11.else inc left and desc right
//	12.Divide into two sub-problems
//	12a.The first sub-problem: start=start, end=start+left-1, pivot=start
//	12b.The second sub-problem: start=start+left+1, end=end, pivot=end
	private static void quickSortInplace(int[] arr) {
		partitionInplace(arr, 0, arr.length-1);
	}
	
	private static void partitionInplace(int[] arr, int start, int end) {
		// Can no longer divide, so return
		if(start>=end) {
			return;
		}
		
		int pivotIdx = pickPivot(start, end);
		// hold pivot value in pivotValue
		int pivotValue = arr[pivotIdx];
		
		// Loop from start to end to partition the values around pivotValue
		// With two leg walking from both sides of the sub-array
		// Swap mis-placed elements on the two legs until
		// all smaller elements are on the left
		// and all larger elements are on the right
		int left=start;
		int right=end;
		while(left<right) {
			while(left<pivotIdx && arr[left]<=pivotValue) {
				left++;
			}
			
			while(right>pivotIdx && arr[right]>=pivotValue) {
				right--;
			}
			int temp = arr[left];
			arr[left] = arr[right];
			arr[right] = temp;
			
			if(left==pivotIdx) {
				// if left leg step on the pivotIdx, pivot on the right leg (since we swapped)
				pivotIdx=right;
				left++;
			} else if(right==pivotIdx) {
				// if right leg step on the pivot, pivot on the left leg (since we swapped)
				pivotIdx=left;
				right--;
			} else {
				left++;
				right--;
			}
		}
		
		// Divide to two sub-problems
		// First sub-problem, new start=start, new end=start+left-1, pivotIdx=start
		partitionInplace(arr, start, pivotIdx-1);
		// Second sub-problem, new start=start+left+1, new end=end, pivotIdx=end
		partitionInplace(arr, pivotIdx+1, end);
	}
	
	private static int pickPivot(int start, int end) {
		Random r = new Random(System.currentTimeMillis());
		return r.nextInt(end-start+1) + start;
	}
}
