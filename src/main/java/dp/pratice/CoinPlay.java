package dp.pratice;

public class CoinPlay {
	public static void main(String[] args) {
		System.out.println(maxWin(new int[] {8, 15, 3, 7}));
		System.out.println(maxWin(new int[] {1, 5}));
	}
	
    static int maxWin(int[] v) {
    	int[][] mem = createMem(v);
		return maxWin(0, v.length-1, v, mem);
    }

	private static int[][] createMem(int[] v) {
		int[][] mem1 = new int[v.length][v.length];
    	for(int i = 0; i < mem1.length; i++) {
    		for(int j = 0; j < mem1[i].length; j++ ) {
    			mem1[i][j] = -1;
    		}
    	}
		return mem1;
	}

	private static int maxWin(int i, int j, int[] v, int[][] mem) {
		if(i==j) {
			mem[i][j] = v[i];
		}
		if(i>j) {
			return 0;
		}
		
		if(mem[i][j]==-1) {
			int leftLeft = maxWin(i+2, j, v, mem);
			int leftRight = maxWin(i+1, j-1, v, mem);
			int rightLeft = maxWin(i, j-2, v, mem);
			int rightRight = maxWin(i+1, j-1, v, mem);
			
			mem[i][j] = Math.max(Math.min(leftLeft, leftRight)+v[i], Math.min(rightRight, rightLeft)+v[j]);
		}
		
		return mem[i][j];
	}
}
