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
		//This is a recursive solution.
		//The idea is that we simply enumerate through all possible splits and calculate 
		//cost for each and find the minimum of all.
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
