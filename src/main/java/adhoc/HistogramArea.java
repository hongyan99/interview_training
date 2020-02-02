package adhoc;

import java.util.HashMap;
import java.util.Map;

public class HistogramArea {
	public static void main(String[] args) {
		System.out.println(findMaxPossibleArea(new long[] {2, 4, 6, 5, 8}, 0, 4));
		System.out.println(findMaxPossibleArea(new long[] {2, 4, 6, 5, 8}, 3, 3));
		System.out.println(findMaxPossibleArea(new long[] {6, 2, 5, 4, 5, 1, 6}, 0, 6));
	}
	
	static long findMaxPossibleArea(long[] heights, int l, int r) {
		// Description of the algorithm
		// The area in between any two bars can be calculated as min(h*(i-j+1)),
		// assuming that the bars has unit width, where h is the height, i is 
		// the current index, j is the lowest index with height h.
		// Loop through the bars from l to r, update the lowest index 
		// for every bar height, calculate the max area and store that max
		// value until reach index r.
		
		Map<Long, Integer> indices = new HashMap<>();
		long max = 0;
		for(int i = l; i <= r; i++) {
			Map<Long, Integer> newIndices = new HashMap<>();
			long height = heights[i];
			for(long k = 1; k<=height; k++) {
				Integer index = indices.getOrDefault(k, i);
				newIndices.put(k, index);
				int j = index==null? i : index.intValue();
				max = Math.max(max, k * (i-j+1));
			}
			indices = newIndices;
		}
		return max;
    }
}
