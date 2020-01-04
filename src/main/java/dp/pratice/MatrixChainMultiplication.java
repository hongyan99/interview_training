package dp.pratice;

public class MatrixChainMultiplication {
	public static void main(String[] args) {
		System.out.println(minMultiplicationCost(new int[] {10,30,5,60}));
		System.out.println(minMultiplicationCost(new int[] {40,20,30,10,30}));
	}
	
	static int minMultiplicationCost(int[] mtxSizes) {
		int[][] ops = new int[mtxSizes.length][mtxSizes.length];
		
		// use a bottom up approach
		
		// Base case: number of operations needed for
		// single matrix: ops[i][i+1]
		// two matrices: ops[i][i+2], where i from 0 to n.
		for(int i = 0; i < mtxSizes.length-2; i++) {
			// no multiplication (once single matrix)
			ops[i][i+1] = 0; 
			//(multiplication of two adjacent matrices)
			ops[i][i+2] = mtxSizes[i]*mtxSizes[i+1]*mtxSizes[i+2]; 
		}
		ops[mtxSizes.length-2][mtxSizes.length-1] = 0;

		// Loops to calculate ops[i][j], growing from the base case
		for(int k = 3; k<mtxSizes.length; k++) {
			for(int i = 0; i < mtxSizes.length-k; i++) {
				int min = Integer.MAX_VALUE;
				for(int kk = 1; kk<k; kk++) {
					min = Math.min(min, ops[i][i+kk]+ops[i+kk][i+k]+mtxSizes[i]*mtxSizes[i+kk]*mtxSizes[i+k]);
				}
				ops[i][i+k] = min;
			}
		}
		return ops[0][mtxSizes.length-1];
    }
}
