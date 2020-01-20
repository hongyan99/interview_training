package dp.pratice;

/**
 * Given a sequence of matrices, find the most efficient way to multiply these 
 * matrices together. The problem is not actually to perform the multiplications, 
 * but merely to decide in which order to perform the multiplications. We have 
 * many options to multiply a chain of matrices because matrix multiplication 
 * is associative. In other words, no matter how we parenthesize the product, 
 * the result will be the same. For example, if we had four matrices A, B, C, 
 * and D, we would have:
 * 
 * (ABC)D = (AB)(CD) = A(BCD) = ....
 * 
 * However, the order in which we parenthesize the product affects the number of 
 * simple arithmetic operations needed to compute the product, or the efficiency. 
 * For example, suppose A is a 10 × 30 matrix, B is a 30 × 5 matrix, and C is a 
 * 5 × 60 matrix. Then,
 * 
 * (AB)C = (10×30×5) + (10×5×60) = 1500 + 3000 = 4500 operations 
 * 
 * A(BC) = (30×5×60) + (10×30×60) = 9000 + 18000 = 27000 operations.
 * 
 * Clearly, the first parenthesization requires less number of operations.
 * 
 * Given an array mtxSizes[], which represents the chain of matrices such that 
 * the ith matrix Ai is of dimension mtxSizes[i-1] x mtxSizes[i], we need to 
 * write a function that should return the minimum number of multiplications 
 * needed to multiply the chain. Length of chain of matrices is n, and thus size 
 * of mtxSizes is (n+1).
 * 
 * @author Hongyan
 *
 */
public class MatrixChainMultiplication {
	public static void main(String[] args) {
		System.out.println(minMultiplicationCost(new int[] {10,30,5,60}));
		System.out.println(minMultiplicationCost(new int[] {40,20,30,10,30}));
	}
	
	static int minMultiplicationCost(int[] mtxSizes) {
		//In this algorithm, we greedily build the answer for all sets of  
		//adjacent matrices. Since the dimensions of the adjacent matrices
		//are represented by indices from i to j, where both j > i, we 
		//store the results in a 2-D array ops[i][j], 
		//where i=0,mtxSizes.length-2 and j=1,mtxSizes.length-1.
		//Important fact, if a sequence of matrices i to j are multiplied
		//calculated as i to k, and k to j and then multiplying the results
		//then the cost would be
		//cost[i][j] at k = cost[i][k]+cost[k][j]+mtxSizes[i]*mtxSizes[k]*mtxSizes[j].
		//
		//Thus the minimum cost of matrices from i to j can be expressed as below
		//ops[i][j] = min(ops[i][k],ops[k][j]+mtxSizes[i]*mtxSizes[k]*mtxSizes[j])
		//where k loops from i+1 to j-1
		//
		//The base case would be for j==i+1
		int[][] ops = new int[mtxSizes.length][mtxSizes.length];
		
		// use a bottom up approach
		
		// Base case: number of operations needed for
		// single matrix: ops[i][i+1]
		// two matrices: ops[i][i+2], where i from 0 to n.
		for(int i = 0; i < mtxSizes.length-2; i++) {
			// no multiplication (once single matrix)
			ops[i][i+1] = 0; 
		}

		// Loops to calculate ops[i][j], growing from the base case
		for(int k = 2; k<mtxSizes.length; k++) {
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
