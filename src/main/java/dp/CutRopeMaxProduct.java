package dp;

import java.util.HashMap;
import java.util.Map;

public class CutRopeMaxProduct {
	public static void main(String[] args) {
		System.out.println(maxProduct(50));
	}
	
	static long maxProduct(int n) {
		Map<Integer, Long> mem = new HashMap<>();
		return maxProduct(n, mem);
    }

	private static long maxProduct(int n, Map<Integer, Long> mem) {
		if(n<=2) {
			return 1;
		}
		
		if(mem.get(n)!=null) {
			return mem.get(n);
		}
		int nLeft = n/2;
		int nRight = n-nLeft;
		long left = maxProduct(nLeft, mem);
		long right = maxProduct(nRight, mem);
		long result = max(left*right, left*nRight, nLeft*right, nLeft*nRight);
		if(nLeft-1>1 && nRight+1<n) {
			left = maxProduct(nLeft-1, mem);
			right = maxProduct(nRight+1, mem);
			result = max(result, left*right, right*(nLeft-1));
		}
		mem.put(n, result);
		return result;
	}

	private static long max(long... nums) {
		long max = nums[0];
		for(int k = 1; k<nums.length; k++) {
			max = Math.max(max, nums[k]);
		}
		return max;
	}
}
