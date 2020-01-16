package dp.pratice;

import java.util.Arrays;
import java.util.List;

public class WordWrap {
	public static void main(String[] args) {
		System.out.println(solveBalancedLineBreaks(Arrays.asList(new String[] {"abc", "cd", "e", "ijklm"}), 6));
		System.out.println(solveBalancedLineBreaks(Arrays.asList(new String[] {"a"}), 1));
		System.out.println(solveBalancedLineBreaks(Arrays.asList(new String[] {"omg", "very", "are", "extreme"}), 10));
	}
	
	public static long solveBalancedLineBreaks(List<String> words, int limit) {
		int size = words.size();
		long[] costs = new long[size+1];
		int width = limit+1;

		// the first line as base case
		int len = 0;
		int lastLineMax = -1;
		costs[size] = 0L;
		for(int i = size-1; i >= 0; i--) {
			len += words.get(i).length() + 1;
			if(len > width) {
				lastLineMax = i;
				break;
			}
			costs[i] = 0L;
		}
		
		for(int i = lastLineMax; i >= 0; i--) {
			costs[i] = Long.MAX_VALUE;
			len = 0;
			for(int j = i; j < size; j++) {
				len += words.get(j).length() + 1;
				if(len > width) {
					break;
				}
				long diff = width -len;
				costs[i] = Math.min(costs[i], costs[j+1]+diff*diff*diff);
			}
		}
		return costs[0];
	}
}
