package adhoc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HistogramArea2 {
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
		AtomicLong max = new AtomicLong();
		for(int i = l; i <= r; i++) {
			Map<Integer, Long> maxHeights = new HashMap<>();
			long height = heights[i];
			final AtomicInteger minIndex = new AtomicInteger(i);
			indices.forEach((h,j)->{
				if(h>=height) {
					minIndex.set(Math.min(minIndex.get(), j));
				} else {
					Long maxH = maxHeights.getOrDefault(j, 0L);
					maxHeights.put(j, Math.max(maxH, h));
				}
			});

			Map<Long, Integer> newIndices = new HashMap<>();
			final int index = i;
			maxHeights.forEach((j, h)-> {
				newIndices.put(h, j);
				max.set(Math.max(max.get(), h * (index-j+1)));
			});
			newIndices.put(height, minIndex.get());
			max.set(Math.max(max.get(), height * (i-minIndex.get()+1)));
			indices = newIndices;
		}
		return max.get();
    }
}
