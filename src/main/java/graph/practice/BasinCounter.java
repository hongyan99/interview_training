package graph.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * You are given a matrix where each number represents altitude of that cell, such that, 
 * water flows towards lower altitudes. If a cell’s four neighboring cells all have higher 
 * altitudes, we call this cell a sink; water collects in sinks.  Otherwise, water will 
 * flow to the neighboring cell with the lowest altitude. If a cell is not a sink, 
 * you may assume it has a unique lowest neighbor and that this will be lower than the cell.  
 * 
 * Cells that drain into the same sink – directly or indirectly – are said to be part of 
 * the same basin. Your challenge is to partition the map into basins. Your code should 
 * output the sizes of the basins, in non-decreasing order.
 * 
 * @author Hongyan
 *
 */
public class BasinCounter {
	public static void main(String[] args) {
		List<List<Integer>> matrix = new ArrayList<>();
		
		addRow(matrix, 1, 4, 7, 10);
		addRow(matrix, 5, 2, 8, 5);
		addRow(matrix, 6, 4, 3, 12);
		System.out.println(Arrays.toString(findBasins(matrix).toArray()));
	}
	
	private static void addRow(List<List<Integer>> matrix, Integer... elems) {
		List<Integer> row = Arrays.asList(elems);
		matrix.add(row);
	}

	public static List<Integer> findBasins(List<List<Integer>> matrix) {
		// Algorithm:
		// 1. Build a graph where every node points to the neighbor that has the lowest
		//    altitude.
		// 2. Do a topographic sort
		// 3. Do a BFS or DFS on the sorted graph and count the number of connected graphs
		Map<Integer, List<Integer>> adjLists = buildAdjLists(matrix);

		int width = matrix.size();
		int height = width==0? 0 : matrix.get(0).size();
		boolean[] visited = new boolean[width*height];
		Stack<Integer> sorted = new Stack<>();
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				Integer key = i*height+j;
				if(!visited[key]) {
					Stack<Integer> stack = new Stack<>();
					stack.push(key);
					visit(stack, adjLists, visited, sorted);
				}
			}
		}
		final List<Integer> counts = new ArrayList<>();
		visited = new boolean[width*height];
		while(!sorted.isEmpty()) {
			Integer key = sorted.pop();
			if(visited[key]) {
				continue;
			}
			Stack<Integer> stack = new Stack<>();
			stack.push(key);
			counts.add(count(stack, adjLists, visited, 0));
		}
		
		PriorityQueue<Integer> queue = new PriorityQueue<>(counts);
		counts.clear();
		while(!queue.isEmpty()) {
			counts.add(queue.poll());
		}
		return counts;
	 }

	private static Integer count(Stack<Integer> stack, Map<Integer, List<Integer>> adjLists, boolean[] visited, int count) {
		while(!stack.isEmpty()) {
			Integer key = stack.pop();
			if(!visited[key]) {
				visited[key] = true;
				count++;
				List<Integer> adjList = adjLists.get(key);
				if(adjList!=null) {
					adjList.forEach(c-> {
						if(!visited[c]) {
							stack.push(c);
						}
					});
				}
				count = count(stack, adjLists, visited, count);
			}
		}
		return count;
	}

	private static void visit(Stack<Integer> stack, Map<Integer, List<Integer>> adjLists, 
			boolean[] visited, Stack<Integer> sorted) {
		while(!stack.isEmpty()) {
			Integer key = stack.pop();
			if(!visited[key]) {
				visited[key] = true;
				List<Integer> adjList = adjLists.get(key);
				if(adjList!=null) {
					adjList.forEach(c-> {
						if(!visited[c]) {
							stack.push(c);
						}
					});
				}
				visit(stack, adjLists, visited, sorted);
				sorted.add(key);
			}
		}
	}

	private static Map<Integer, List<Integer>> buildAdjLists(List<List<Integer>> matrix) {
		int width = matrix.size();
		int height = width==0? 0 : matrix.get(0).size();
		Map<Integer, List<Integer>> returns = new HashMap<>();
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int minI = i;
				int minJ = j;
				int min = matrix.get(i).get(j);
				if(i-1 > -1 && matrix.get(i-1).get(j) < min) {
					min = matrix.get(i-1).get(j);
					minI = i-1;
					minJ = j;
				}
				if(i+1 < width && matrix.get(i+1).get(j) < min) {
					min = matrix.get(i+1).get(j);
					minI = i+1;
					minJ = j;
				}
				if(j-1 > -1 && matrix.get(i).get(j-1) < min) {
					min = matrix.get(i).get(j-1);
					minI = i;
					minJ = j-1;
				}
				if(j+1 < height && matrix.get(i).get(j+1) < min) {
					min = matrix.get(i).get(j+1);
					minI = i;
					minJ = j+1;
				}
				if(minI==i && minJ==j) {
					continue;
				} else {
					Integer key = minI*height+minJ;
					Integer value = i*height+j;
					List<Integer> list = returns.get(key);
					if(list==null) {
						list = new ArrayList<>();
						returns.put(key, list);
					}
					list.add(value);
				}
			}
		}
		return returns;
	}
}
