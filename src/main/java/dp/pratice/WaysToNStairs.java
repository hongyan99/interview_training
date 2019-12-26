package dp;

public class WaysToNStairs {
	public static void main(String[] args) {
		System.out.println(countWaysToClimb(new int[] {2, 3}, 7));
	}
	
    static long countWaysToClimb(int[] steps, int n) {
    	long[] mem = new long[n];
    	for(int i = 0; i < n; i++) {
    		mem[i] = -1;
    	}
		return countWaysToClimb(steps, n, mem);
    }

    private static long countWaysToClimb(int[] steps, int n, long[] mem) {
    	if(n==0) {
    		return 1;
    	}
    	if(n<0) {
    		return 0;
    	}
    	
    	if(mem[n-1]==-1) {
    		long total = 0;
    		for(int i = 0; i < steps.length; i++) {
    			total += countWaysToClimb(steps, n-steps[i], mem);
    		}
    		mem[n-1] = total;
    	}
    	
		return mem[n-1];
    }
}
