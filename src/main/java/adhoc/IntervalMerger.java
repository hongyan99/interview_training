package adhoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Given an array of time intervals(in any order) inputArray, of size n, merge all overlapping 
 * intervals into one and return the resulting array outputArray, such that no two intervals 
 * in outputArray are overlapping. In other words, result array should contain only mutually 
 * exclusive intervals. Hence, in outputArray, no pair of intervals i and j exists, such that 
 * outputArray[i][0] <= outputArray[j][0] <= outputArray[i][1].
 * 
 * @author Hongyan
 *
 */
public class IntervalMerger {
	public static void main(String[] args) {
		int[][] inputArray = new int[][] {{10, 12}, {1, 2}, {1000, 100000}, 
			{-1000000000, 1000000000}, {2, 5}, {7, 10}, {12, 456}};
		for(int[] pair : getMergedIntervals(inputArray)) {
			System.out.println(Arrays.toString(pair));
		}
	}
	
	static int[][] getMergedIntervals(int[][] inputArray) {
		//The basic idea:
		//1. Sort the intervals by the starting value of the intervals.
		//2. Loop through the intervals in order, and join the interval with the last
		//   interval if overlapping.
		PriorityQueue<int[]> array = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[0]-o2[0];
			}
		});
		
		for(int[] pair : inputArray) {
			array.add(pair);
		}
		
		List<int[]> output = new ArrayList<int[]>();
		
		int[] lastPair = array.poll();
		output.add(lastPair);
		while(!array.isEmpty()) {
			int[] pair = array.poll();
			if(lastPair[1]>=pair[0]) {
				lastPair[1] = Math.max(lastPair[1], pair[1]);
			} else {
				output.add(pair);
				lastPair = pair;
			}
		}
		
		int[][] returns = new int[output.size()][2];
		for(int i = 0; i < output.size(); i++) {
			returns[i] = output.get(i);
		}
		return returns;
    }
}
