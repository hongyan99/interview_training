package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumOfPath {
	public static void main(String[] args) {
		System.out.println(numberOfPaths(constructMatrix(new Integer[]{1,1,0}, new Integer[]{1,1,1}, new Integer[]{0,1,1})));
	}
	
	private static List<List<Integer>> constructMatrix(Integer[]... array) {
		List<List<Integer>> list = new ArrayList<>();
		for(int k = 0; k<array.length; k++) {
			list.add(Arrays.asList(array[k]));
		}
		return list;
	}

	public static int numberOfPaths(List<List<Integer>> matrix) {
		int rows = matrix.size();
		if(rows==0) {
			return 1;
		}
		int cols = matrix.get(0).size();
		long[][] mem = new long[rows][cols];
		for(int i = 0; i < mem.length; i++) {
			for(int j = 0; j < mem[0].length; j++) {
				mem[i][j] = -1;
			}
		}

		return numberOfPaths(rows-1, cols-1, matrix, mem);
    }
	
	private static int numberOfPaths(int i, int j, List<List<Integer>> matrix, long[][] mem) {
		if(i<0 || j<0) {
			return 0;
		}
		
		if(matrix.get(i).get(j)==0) {
			return 0;
		}
		
		if(mem[i][j]>-1) {
			return (int)mem[i][j];
		}
		
		if(i==0 && j==0) {
			return 1;
		}
		
		return (int)(mem[i][j] = (numberOfPaths(i-1, j, matrix, mem) + numberOfPaths(i, j-1, matrix, mem)) % MOD);
		
	}
	
	private static final long MOD = (long)Math.pow(10, 9) + 7L;
}
