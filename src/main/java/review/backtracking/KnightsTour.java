package review.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class KnightsTour {
	public static void main(String[] args) {
		List<int[][]> result = tour(5);
		print(result);
	}
	
	private static void print(List<int[][]> result) {
		System.out.println("Result:");
		result.forEach(b-> {
			for(int i = 0; i < b.length; i++) {
				System.out.println(Arrays.toString(b[i]));
			}
			System.out.println();
		});
	}

	private static List<int[][]> tour(int n) {
		List<int[][]> result = new ArrayList<>();
		// Start the solving from (0,0)
		// Use the backtracking template
		Board board = new Board(n);
		board.set(0, 0);
		solve(new int[] {0,0}, board, result);
		
		return result;
	}
	
	private static void solve(int[] point, Board board, List<int[][]> result) {
		if(board.isDone()) {
			board.store(result);
			return;
		}
		
		board.forEach(point, (x,y)-> {
			board.set(x, y);
			// each point is already valid, no need to check
			solve(new int[] {x, y}, board, result);
			board.unset(x, y);
		});
	}

	private static class Board {
		private final int maxCount;
		private final int dim;
		private final int[][] board;
		private int count;
		
		Board(int dim) {
			this.dim = dim;
			this.maxCount = dim*dim;
			this.board = new int[dim][dim];
			for(int i = 0; i < dim; i++) {
				Arrays.fill(this.board[i], -1);
			}
		}
		
		List<int[]> getNextPoints(int x, int y) {
			List<int[]> points = new ArrayList<>();
			addIfValid(points, x+2, y+1);
			addIfValid(points, x+2, y-1);
			addIfValid(points, x-2, y+1);
			addIfValid(points, x-2, y-1);
			addIfValid(points, x+1, y+2);
			addIfValid(points, x+1, y-2);
			addIfValid(points, x-1, y+2);
			addIfValid(points, x-1, y-2);
			return points;
		}
		
		private void addIfValid(List<int[]> points, int x, int y) {
			if(x<dim && x>=0 && y <dim && y>=0) {
				points.add(new int[] {x,y});
			}
		}
		
		void set(int x, int y) {
			board[x][y] = count;
			count++;
		}
		
		void unset(int x, int y) {
			count--;
			board[x][y] = -1;
		}
		
		void store(List<int[][]> result) {
			int[][] copy = new int[dim][dim];
			for(int i = 0; i < dim; i++) {
				copy[i] = board[i].clone();
			}
			result.add(copy);
		}
		
		boolean isDone() {
			return count>=maxCount;
		}
		
		void forEach(int[] currPoint, BiConsumer<Integer, Integer> handler) {
			List<int[]> points = getNextPoints(currPoint[0], currPoint[1]);
			points.forEach(p-> {
				if(board[p[0]][p[1]]==-1) {
					handler.accept(p[0], p[1]);
				}
			});
		}
	}
}
