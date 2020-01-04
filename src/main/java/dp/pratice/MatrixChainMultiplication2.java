package dp.pratice;

public class MatrixChainMultiplication2 {
	public static void main(String[] args) {
		System.out.println(minMultiplicationCost(new int[] {10,30,5,60}));
		System.out.println(minMultiplicationCost(new int[] {40,20,30,10,30}));
	}
	
	static int minMultiplicationCost(int[] mtxSizes) {
		Integer[][] ops = new Integer[mtxSizes.length][mtxSizes.length];
		return minMultiplicationCost(mtxSizes, ops, 0, mtxSizes.length-1);
    }
	
	static int minMultiplicationCost(int[] mtxSizes, Integer[][] ops, int i, int j) {
		if(ops[i][j]!=null) {
			return ops[i][j];
		}
		if(j==i+1) {
			return 0;
		}
		if(j==i+2) {
			return mtxSizes[i]*mtxSizes[i+1]*mtxSizes[i+2]; 
		}
		
		int min = Integer.MAX_VALUE;
		for(int k = i+1; k<j; k++) {
			min = Math.min(min, 
					minMultiplicationCost(mtxSizes, ops, i, k) + 
					minMultiplicationCost(mtxSizes, ops, k, j) + 
					mtxSizes[i]*mtxSizes[k]*mtxSizes[j]);
		}
		ops[i][j] = min;
		return min;
	}
}
