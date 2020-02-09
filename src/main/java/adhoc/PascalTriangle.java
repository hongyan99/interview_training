package adhoc;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {
	public static void main(String[] args) {
		print(findPascalTriangle(4));
	}
	
	private static void print(List<List<Integer>> triangle) {
		triangle.forEach(row-> {
			row.forEach(n-> {
				System.out.print(String.format("%3d", n));
			});
			System.out.println();
		}); 
	}
	
	public static List<List<Integer>> findPascalTriangle(int n) {
		List<List<Integer>> returns = new ArrayList<>();
		if(n<1) {
			return returns;
		}
		List<Integer> row = new ArrayList<>();
		row.add(1);
		returns.add(row);
		
		for(int i = 1; i < n; i++) {
			row = new ArrayList<>();
			returns.add(row);
			row.add(1);
			for ( int j = 1; j < i; j++) {
				row.add((returns.get(i-1).get(j-1)+returns.get(i-1).get(j))%MOD);
			}
			row.add(1);
		}
    	return returns;
    }
	
	static final int MOD = 1000000007;
}
