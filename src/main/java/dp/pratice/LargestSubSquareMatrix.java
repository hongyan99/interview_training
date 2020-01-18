package dp.pratice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LargestSubSquareMatrix {
	public static void main(String[] args) {
		List<List<Integer>> mat = new ArrayList<>();
		addRow(mat, 1, 0, 0);
		addRow(mat, 0, 1, 1);
		addRow(mat, 0, 1, 1);
		System.out.println(lssm(3,3,mat));
	}
	
	private static void addRow(List<List<Integer>> mat, Integer...row) {
		mat.add(Arrays.asList(row));
	}
	
	public static int lssm(int n, int m, List<List<Integer>> mat) {
		//stores all the matrices at i,j as dp[i][j][0] and dp[i][j][1].
		//where the two numbers gives the size of a rectangle of all 1s
		//and the bottom right of the rectangle is at (i,j)
		//and the first number gives the height, while the second, the width 
		//(i,j) correspond to (i-1, j-1) of the matrix, thus either i==0 or j==0 functions 
		//as the base case in DP.
		//i==0 is an imaginary row above the first row of the matrix
		//j==0 is an imaginary column left of the first column of the matrix
		int[][][] dp = new int[n+1][m+1][2];
		int max = 0;
		for(int i = 1; i < n+1; i++) {
			for(int j = 1; j < m+1; j++) {
				if(mat.get(i-1).get(j-1)==1) {
					dp[i][j][0] = Math.min(dp[i-1][j-1][0], dp[i-1][j][0])+1;
					dp[i][j][1] = Math.min(dp[i-1][j-1][1], dp[i][j-1][1])+1;
					int s = Math.min(dp[i][j][0], dp[i][j][1]);
					max = Math.max(max, s);
				}
			}
		}
		
		return max;
    }
}
