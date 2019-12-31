package graph.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DAGCycleDector {
	public static void main(String[] args) {
		List<List<Integer>> edges = createEdges(
				new int[] {0,1},new int[] {1,2},
				new int[] {2,3},new int[] {3,4},
				new int[] {5,6},new int[] {6,7},
				new int[] {6,9},new int[] {7,8},
				new int[] {8,9});
		System.out.println(hasCycle(10, 9, edges));
	}
	
    private static List<List<Integer>> createEdges(int[]... edges) {
    	List<List<Integer>> returns = new ArrayList<>();
    	for(int[] edge : edges) {
    		List<Integer> e = new ArrayList<>();
    		e.add(edge[0]);
    		e.add(edge[1]);
        	returns.add(e);
    	}
		return returns;
	}

	public static boolean hasCycle(int N, int M, List<List<Integer>> edges) {
    	// Algorithm:
    	// 1. Do a DFS
    	// 2. Record the start time and the end time
    	// 3. If a child has a start time but no end time, then there is a cycle
    	
    	Map<Integer, List<Integer>> adjLists = createAdjLists(edges);
    	boolean[] visited = new boolean[N];
    	int[] startTime = new int[N];
    	int[] endTime = new int[N];
    	
    	time = 0;
    	for(int i =0; i<N; i++) {
    		if(visited[i]) {
    			continue;
    		}
    		Stack<Integer> queue = new Stack<>();
    		queue.push(i);
    		boolean foundCycle = dfs(queue, adjLists, visited, startTime, endTime);
    		if(foundCycle) {
    			return true;
    		}
    	}
    	
		return false;
    }

	private static boolean dfs(Stack<Integer> queue, Map<Integer, List<Integer>> adjLists, boolean[] visited, int[] startTime, int[] endTime) {
		if (!queue.isEmpty()) {
			int node = queue.pop();
			if(visited[node]) {
				return false;
			}
			visited[node] = true;
			// NOTE: in between this line and where the endTime is assigned
			// we should NOT short circuit when a cycle is NOT detected yet.
			startTime[node] = ++time;
			List<Integer> adjList = adjLists.get(node);
			if(adjList!=null) {
				for(int c : adjList) {
					if(startTime[c]>0 && endTime[c]==0) {
						return true; // short circuit since we detected the cycle
					}
					if(!visited[c]) {
						queue.push(c);
					}
				}
				boolean hasCycle = dfs(queue, adjLists, visited, startTime, endTime);
				if(hasCycle) {
					return true; // short circuit since we detected the cycle
				}
			}
			endTime[node] = ++time;
		}
		return false;
	}

	private static Map<Integer, List<Integer>> createAdjLists(List<List<Integer>> edges) {
		Map<Integer, List<Integer>> adjLists = new HashMap<>();
		edges.forEach(l-> {
			List<Integer> list = adjLists.get(l.get(0));
			if(list == null) {
				list = new ArrayList<>();
				adjLists.put(l.get(0), list);
			}
			list.add(l.get(1));
		});
		return adjLists;
	}
	
	private static int time = 0;
}
