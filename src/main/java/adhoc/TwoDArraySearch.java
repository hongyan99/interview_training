package adhoc;

public class TwoDArraySearch {
	private static final String NOT_PRESENT = "not present";
	private static final String PRESENT = "present";

	public static void main(String[] args) {
		int[][] arr = new int[3][4];
		arr[0] = new int[] {1, 2, 3, 12};
		arr[1] = new int[] {4, 5, 6, 45};
		arr[2] = new int[] {7, 8, 9, 78};
		System.out.println(isPresent(arr, 6));
		System.out.println(isPresent(arr, 7));
		System.out.println(isPresent(arr, 23));
	}
	
	private static String isPresent(int[][] arr, int x) {
		return arr.length==0? NOT_PRESENT : isPresent(arr, x, 0, 0, arr.length-1, arr[0].length-1);
    }
	
	private static String isPresent(int[][] arr, int x, int topLeftI, int topLeftJ, int bottomRightI, int bottomRightJ) {
		// The idea is as below:
		// We do something similar to a binary search.
		// 1. Pick the top row, and do a binary search to find the number.
		// 1.0. If array has 1 number, simply compare and return accordingly.
		// 1.1. If found, then return "present".
		// 1.2. If the first number is bigger (->the whole row is bigger), then return "not present".
		// 1.3. If the last number is smaller (->the whole row is smaller), then proceed to step 2.
		// 1.4. If some numbers are smaller and some are bigger, assuming number K bigger and K-1 smaller, then
		// 1.4.1. Throw away all columns with index K and higher and proceed to step 2.
		
		// 1.0. If array has 1 number, simply compare and return accordingly.
		if(topLeftI==bottomRightI && topLeftJ==bottomRightJ) {
			return arr[topLeftI][topLeftJ]==x? PRESENT : NOT_PRESENT;
		}
		ColumnMatch result = find(arr[topLeftI], x, topLeftJ, bottomRightJ);
		// 1.1. If found, then return "present".
		if(result.found) return PRESENT;
		// the whole row is bigger or smaller
		if(result.k==null) {
			// 1.2. If the first number is bigger (->the whole row is bigger), then return "not present".
			if(result.bigger) return NOT_PRESENT;
			// 1.3. If the last number is smaller (->the whole row is smaller), then proceed to step 2.
			return step2(arr, x, topLeftI, topLeftJ, bottomRightI, bottomRightJ);
		}
		// 1.4.1. Throw away all columns with index K and higher and proceed to step 2.
		return step2(arr, x, topLeftI, topLeftJ, bottomRightI, result.k-1);
	}
	
	private static String step2(int[][] arr, int x, int topLeftI, int topLeftJ, int bottomRightI, int bottomRightJ) {
		// 2. Pick the bottom row, and do a binary search to find the number.
		// 2.0. If array has 1 number, simply compare and return accordingly.
		// 2.1. If found, then return "present".
		// 2.2. If the last number is smaller (->the whole row is smaller), then return "not present"
		// 2.3. If the first number is bigger (->the whole row is bigger), then proceed to step 3.
		// 2.4. If some numbers are smaller and some are bigger, assuming number K bigger and K-1 smaller, then
		// 2.4.1. Throw away all column with index K-1 and lower and proceed to step 3.
		
		// 2.0. If array has 1 number, simply compare and return accordingly.
		if(topLeftI==bottomRightI && topLeftJ==bottomRightJ) {
			return arr[topLeftI][topLeftJ]==x? PRESENT : NOT_PRESENT;
		}
		ColumnMatch result = find(arr[bottomRightI], x, topLeftJ, bottomRightJ);
		// 2.1. If found, then return "present".
		if(result.found) return PRESENT;
		// the whole row is bigger or smaller
		if(result.k==null) {
			// 2.2. If the last number is smaller (->the whole row is smaller), then return "not present"
			if(!result.bigger) return NOT_PRESENT;
			// 2.3. If the first number is bigger (->the whole row is bigger), then proceed to step 3.
			return step3(arr, x, topLeftI, topLeftJ, bottomRightI, bottomRightJ);
		}
		// 2.4.1. Throw away all column with index K-1 and lower and proceed to step 3.
		return step3(arr, x, topLeftI, result.k, bottomRightI, bottomRightJ);
	}

	private static String step3(int[][] arr, int x, int topLeftI, int topLeftJ, int bottomRightI, int bottomRightJ) {
		// 3. Pick the middle row in between the top and the bottom (pick the smaller one if even number of rows)
		// 3.0. If nothing in between, then return "not present"
		// 3.1. If array has 1 number, simply compare and return accordingly.
		// 3.2. If found, then return "present".
		// 3.3. If the first number is bigger (->the whole row is bigger), then throw away all rows below, and repeat 3.
		// 3.4. If the last number is smaller (->the whole row is smaller), then throw away all rows above, and repeat 3.
		// 3.5. If some numbers are smaller and some are bigger, assuming number K bigger and K-1 smaller, then
		// 3.5.1. Throw away, the quadrant 2 and 4, repeat quadrant 1 and 3 using step 3.
		
		int mid = (topLeftI+bottomRightI)/2;
		// 3.0. If nothing in between, then return "not present"
		if(topLeftI==mid) return NOT_PRESENT;
		// 3.1. If array has 1 number, simply compare and return accordingly.
		if(topLeftI==bottomRightI && topLeftJ==bottomRightJ) {
			return arr[topLeftI][topLeftJ]==x? PRESENT : NOT_PRESENT;
		}
		ColumnMatch result = find(arr[mid], x, topLeftJ, bottomRightJ);
		// 3.2. If found, then return "present".
		if(result.found) return PRESENT;
		if(result.k==null) {
			if(result.bigger) {
				// 3.3. If the first number is bigger (->the whole row is bigger), then throw away all rows below, and repeat 3.
				// Now the middle row is the bottom row
				return step3(arr, x, topLeftI, topLeftJ, mid, bottomRightJ);
			} else {
				// 3.4. If the last number is smaller (->the whole row is smaller), then throw away all rows above, and repeat 3.
				// Now the middle row is the top row
				return step3(arr, x, mid, topLeftJ, bottomRightI, bottomRightJ);
			}
		}
		// 3.5.1. Throw away, the quadrant 2 and 4, repeat quadrant 1 and 3 using step 3.
		// The middle row is the bottom row for quadrant 1
		String q1Result = step3(arr, x, topLeftI, result.k, mid, bottomRightJ);
		// 3.5.1.1. If present in quadrant 1, short-circuit and return present
		if(q1Result==PRESENT) return PRESENT;
		// 3.5.1.2. Try quadrant 3
		// The middle row is the top row for quadrant 3
		return step3(arr, x, mid, topLeftJ, bottomRightI, result.k);
	}

	private static ColumnMatch find(int[] column, int x, int left, int right) {
		ColumnMatch match = new ColumnMatch();
		if(column[left]==x || column[right]==x) {
			match.found = true;
			return match;
		}
		if(column[left]>x) {
			match.bigger = true;
			return match; // stands for Bigger
		}
		if(column[right]<x) {
			return match; // stands for Smaller
		}
		int start = left, end = right;
		do {
			int mid = (start+end)/2;
			if(column[mid]==x) {
				match.found = true;
				return match;
			}
			if(column[mid]<x) {
				start = mid;
			} else {
				end = mid;
			}
		} while (start+1<end);

		match.k = end;
		return match;
	}
	
	private static class ColumnMatch {
		Integer k;
		boolean found;
		boolean bigger;
	}
}
