package review.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class NQueen2 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String[][] arrangements = arrange(10);
		for(int i = 0; i < arrangements.length; i++) {
			for(int j = 0; j < arrangements[i].length; j++) {
				System.out.println(arrangements[i][j]);
			}
			System.out.println();
		}
		System.out.println("Total time taken in millis: " + (System.currentTimeMillis()-start));
	}
	
	private static String[][] arrange(int n) {
		// This is a backtracking problem.
		// All backtracking problems follow this pattern
		// void solve(level, board, result):
		//    if(board.isDone(level)) board.store(result)
		//    for each valid choice:
		//    	board.set(level, choice)
		//     	solve(level+1, board, result)
		//     	board.unSet(level, choice)
		
		// Here level is the row, and choice is the column
		
		List<String[]> result = new ArrayList<>();
		for(int col=0; col<n; col++) {
			Board b = new Board(n);
			b.set(0, col);
			solve(1, b, result);
		}
		
		String[][] returns = new String[result.size()][n];
		for(int i = 0; i<result.size(); i++) {
			returns[i] = result.get(i);
		}
		return returns;
	}
	
	private static void solve(int row, Board board, List<String[]> result) {
		// Are we done with the board
		if(board.isDone(row)) {
			board.store(result);
			return;
		}
		
		// for each choice at the current row
		board.forEach(col-> {
			if(board.isValid(row, col)) {
				board.set(row, col);
				solve(row+1, board, result);
				board.unset(row, col);
			}
		});
	}

	private static class Board {
		private final char[][] board;
		private final int dim;
		private final boolean[] takenR, takenC, takenB, takenF;
		
		Board(int dim) {
			this.dim = dim;
			this.board = new char[dim][dim];
			for(int i = 0; i < dim; i++) {
				Arrays.fill(board[i], '-');
			}
			this.takenR = new boolean[dim];
			this.takenC = new boolean[dim];
			this.takenB = new boolean[dim*2];
			this.takenF = new boolean[dim*2];
		}
		
		boolean isValid(int row, int col) {
			return !(takenR[row]||takenC[col]||takenB[row+col]||takenF[dim+row-col]);
		}
		
		void set(int row, int col) {
			set(row, col, 'q', true);
		}
		
		void unset(int row, int col) {
			set(row, col, '-', false);
		}
		
		private void set(int row, int col, char c, boolean flag) {
			board[row][col] = c;
			takenR[row] = flag;
			takenC[col] = flag;
			takenB[row+col] = flag;
			takenF[dim+row-col] = flag;
		}
		
		boolean isDone(int row) {
			return row==dim;
		}
		
		void store(List<String[]> result) {
			if(isValidBoard()) {
				String[] rows = new String[board.length];
				for(int i = 0; i < board.length; i++) {
					rows[i] = new String(board[i]);
				}
				result.add(rows);
			}
		}
		
		void forEach(Consumer<Integer> handler) {
			for(int col = 0; col < dim; col++) {
				handler.accept(col);
			}
		}
		
		boolean isValidBoard() {
			for(int row = 0; row<dim; row++) {
				if(!takenR[row]) {
					// all row must be taken for the board to be valid
					return false;
				}
			}
			return true;
		}
	}
}
