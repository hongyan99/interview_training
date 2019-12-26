package dp;

import java.util.Arrays;
import java.util.List;

public class MinimumSumCoin {
	public static void main(String[] args) {
		System.out.println(minimumCoins(createList(1,3,5), 9));
	}

    private static List<Integer> createList(Integer... values) {
		return Arrays.asList(values);
	}

	public static int minimumCoins(List<Integer> coins, int value) {
    	int[] mem = new int[value+1];
    	for(int i = 0; i < mem.length; i++) {
    		mem[i] = -1;
    	}
		return minimumCoins(coins, value, mem);
    }

	private static int minimumCoins(List<Integer> coins, int value, int[] mem) {
    	if(value<0) {
    		return Integer.MAX_VALUE;
    	}
    	if(value == 0) {
    		return 0;
    	}
		if(mem[value]>-1) {
			return mem[value];
		}
    	
    	int min = Integer.MAX_VALUE;
    	for(int k = 0; k < coins.size(); k++) {
    		min = Math.min(min, minimumCoins(coins, value-coins.get(k), mem));
    	}
    	return mem[value] = min+1;
	}
}
