package adhoc;

public class HistogramArea3 {
	public static void main(String[] args) {
		System.out.println(findMaxPossibleArea(new long[] {2, 4, 6, 5, 8}, 0, 4));
		System.out.println(findMaxPossibleArea(new long[] {2, 4, 6, 5, 8}, 3, 3));
		System.out.println(findMaxPossibleArea(new long[] {6, 2, 5, 4, 5, 1, 6}, 0, 6));
	}
	
	static long findMaxPossibleArea(long[] heights, int l, int r) {
		// Description of the algorithm
		// This is the fastest algorithm from IKWeb.
		// The area can be calculates for each height at index i, 
		// and thus the max can be calculated.
		// TO calculate the area for each height at index i, we need
		// to find how far to the left and to the right we can extend
		// the rectangle of that height.
		
		if(heights==null || heights.length==0 || l>r || (r-l+1)>heights.length) {
			return 0;
		}
		
		int[] left = new int[heights.length];
		int[] right = new int[heights.length];
		
		left[l] = l-1;
		for(int i = l+1; i <= r; i++) {
			int p = i-1;
			while(p>=l && heights[i]<=heights[p]) {
				p = left[p];
			}
			left[i] = p;
		}
		right[r] = r+1;
		long max = heights[r] * (r-left[r]);
		for(int i = r-1; i >= l; i--) {
			int p = i+1;
			while(p<=r && heights[i]<=heights[p]) {
				p = right[p];
			}
			right[i] = p;
			max = Math.max(max,  heights[i]*(right[i]-left[i]-1));
		}
		return max;
    }
}
