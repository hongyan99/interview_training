package dp;

public class Robbery {
	public static void main(String[] args) {
		System.out.println(maxStolenValue(new int[] {6, 1, 2, 7}));
		System.out.println(maxStolenValue(new int[] {3, 5, 7, 2, 2, 3, 8, 2, 7, 7, 7, 3, 2}));
	}
	
    static int maxStolenValue(int[] values) {
    	int[] mem = new int[values.length];
    	for(int i = 0; i < values.length; i++) {
    		mem[i] = -1;
    	}
    	// We are starting from the end of the array and looping towards the head.
    	// We can start from the head towards the end too but that is slightly slower
    	// which can failed the stringent time requirement of the tests in IKWeb.
		return maxStolenValue(values.length-1, values, mem);
    }

	private static int maxStolenValue(int i, int[] values, int[] mem) {
		if(mem[i]>-1) {
			return mem[i];
		}
		if(i==0) {
			return mem[0]=values[0];
		}
		if(i==1) {
			return Math.max(values[0], values[1]);
		}


		return mem[i] = Math.max(maxStolenValue(i-1, values, mem), maxStolenValue(i-2, values, mem) + values[i]);
	}
}
