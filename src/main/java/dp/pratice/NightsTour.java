package dp;

import java.util.HashMap;
import java.util.Map;

public class NightsTour {
	public static void main(String[] args) {
		System.out.println(numPhoneNumbers(6, 20));
	}
	
    static long numPhoneNumbers(int startdigit, int phonenumberlength) {
    	Map<Integer, int[]> neighboursMap = new HashMap<>();
    	for(int i = 0; i < 10; i++) {
    		neighboursMap.put(i, getNeighbours(i));
    	}
		return numPhoneNumbers(startdigit, phonenumberlength, new long[10][phonenumberlength], neighboursMap);
    }
    
    private static long numPhoneNumbers(int start, int length, long[][] mem, Map<Integer, int[]> neighboursMap) {
    	if(length==1) {
    		return 1;
    	}
    	
    	if(mem[start][length-1]==0) {
	    	int[] neighbours = neighboursMap.get(start);
	    	long total = 0;
	    	for(int i=0; i<neighbours.length; i++) {
	    		int n = neighbours[i];
	    		total += numPhoneNumbers(n, length-1, mem, neighboursMap);
	    	}
    		mem[start][length-1] = total;
    	}
    	
		return mem[start][length-1];
    }

	private static int[] getNeighbours(int digit) {
		switch (digit) {
		case 0:
			return new int[] {4, 6};
		case 1:
			return new int[] {6, 8};
		case 2:
			return new int[] {7, 9};
		case 3:
			return new int[] {8, 4};
		case 4:
			return new int[] {9, 0, 3};
		case 5:
			return new int[] {};
		case 6:
			return new int[] {7, 0, 1};
		case 7:
			return new int[] {2, 6};
		case 8:
			return new int[] {1, 3};
		case 9:
			return new int[] {2, 4};
		}
		return null;
	}
}
