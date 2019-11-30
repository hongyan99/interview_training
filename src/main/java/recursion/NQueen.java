package recursion;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class NQueen {
	public static void main(String[] args) {
		String[][] arrangements = arrange(4);
		for(int i = 0; i < arrangements.length; i++) {
			for(int j = 0; j < arrangements[i].length; j++) {
				System.out.println(arrangements[i][j]);
			}
			System.out.println();
		}
	}
	
	private static String[][] arrange(int n) {
		// Stores the column index at which a queen is located on a row
		Integer[] indices = new Integer[n];
		// Stores all the possible arrangements
		List<String[]> arrangements = new ArrayList<String[]>();
		for(int col = 0; col < n; col++) {
			// assign a column index for row one and deducting the indices
			// for remaining rows
			indices[0] = col; 
			find(indices, 0, arrangements);
		}
		String[][] returns = new String[arrangements.size()][n];
		for(int i = 0; i < arrangements.size(); i++) {
			returns[i] = arrangements.get(i);
		}
		return returns;
	}

	private static void find(Integer[] indices, int row, List<String[]> arrangements) {
		row++;
		if(row==indices.length) {
			// Done processing all rows for the current iteration. 
			// Render the result and add to the collection of results
			arrangements.add(render(indices));
			return;
		}

		// Find out all column indices at which a queen is subject to attack
		// and store the positive location in a BitSet, that is, only if the
		// bit is NOT set, we can have a queen located at the column
		BitSet bs = initialize(indices, row);
		for(int col = 0; col < indices.length; col++) {
			if(!bs.get(col)) {
				// Found a column that is good for a queen, so continue to the next row.
				indices[row] = col;
				find(indices, row, arrangements);
			}
		}
	}

	private static BitSet initialize(Integer[] indices, int row) {
		BitSet bs = new BitSet(indices.length);
		for(int r = 0; r < row; r++) {
			int col = indices[r];
			bs.set(col);
			if(col-row+r>=0) {
				bs.set(col-row+r);
			}
			if(col+row-r<indices.length) {
				bs.set(col+row-r);
			}
		}
		return bs;
	}

	private static String[] render(Integer[] indices) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < indices.length; i++) sb.append('-');
		String[] returns = new String[indices.length];
		for(int k=0; k < indices.length; k++) {
			StringBuilder sb1 = new StringBuilder(sb.toString());
			int idx = indices[k];
			sb1.replace(idx, idx+1, "q");
			returns[k] = sb1.toString();
		}
		return returns;
	}
}
