package graph.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class LongestPath {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(find_longest_path(6, new int[] {1,1,1,2,5,3,4}, new int[] {2,3,4,5,3,6,6}, new int[] {1,1,2,1,1,1,1}, 1, 6)));
//		System.out.println(Arrays.toString(find_longest_path(4, new int[] {1,1,1,3}, new int[] {2,3,4,4}, new int[] {2,2,4,3}, 1, 4)));
//		System.out.println(Arrays.toString(find_longest_path(5, new int[] {5,4,3,2,5,5,3}, new int[] {4,3,2,1,1,3,1}, new int[] {1,1,1,1,3,3,1}, 5, 1)));
//		System.out.println(Arrays.toString(find_longest_path(3, new int[] {3,2}, new int[] {2,1}, new int[] {2000000000,2000000000}, 3, 1)));
//		System.out.println(Arrays.toString(find_longest_path(9, new int[] {6,6,2,2,1,5,7,3,3,4,4,8}, new int[] {1,2,1,5,7,7,9,4,2,5,8,7}, new int[] {1,2,6,6,4,5,8,2,4,6,7,6}, 3, 7)));
	}
	
	static int[] find_longest_path(int size, int[] dagFrom, int[] dagTo, int[] dagWeight, int from, int to) {
		// The assumption is that there is no cycle from "from" to "to".
		// The algorithm:
		// 1. Build the adjacency list with negated edge weights
		// 2. Do a topological sort (must start with the zero-in-degree nodes)
		// 3. Walk the sorted graph and find the shortest path from "from" to "to"
		// 4. Package the resulting path as array and return

		// we must use long[] since int is not enough to store the path lengths
		Set<Integer> zeroIn = new HashSet<>();
		Map<Integer, List<long[]>> adjLists = createAdjLists(dagFrom, dagTo, dagWeight, zeroIn);
		Stack<Integer> sorted = topoSort(size, adjLists, zeroIn);
		return findLongestPath(size, from-1, to-1, sorted, adjLists);
    }

	private static int[] findLongestPath(int size, int from, int to, Stack<Integer> sorted, Map<Integer, List<long[]>> adjLists) {
		boolean started = false;
		long[] pathLengths = new long[size];
		Arrays.fill(pathLengths, Long.MAX_VALUE);
		pathLengths[from] = 0L;
		Map<Integer, Integer> parentsMap = new HashMap<>();
		while (!sorted.isEmpty()) {
			int node = sorted.pop();
			if(node==from) {
				started = true;
			} else if (node==to) {
				break;
			}
			if(started) {
				List<long[]> children = adjLists.get(node);
				if(children!=null) {
					for(long[] child : children) {
						long newLength=child[1]+pathLengths[node];
						if(pathLengths[(int)child[0]]>newLength) {
							parentsMap.put((int)child[0], node);
							pathLengths[(int)child[0]]=newLength;
						}
					}
				}
			}
		}
		
		List<Integer> path = new ArrayList<>();
		path.add(to);
		Integer node = to;
		while(node!=from) {
			node = parentsMap.get(node);
			if(node==null) {
				return new int[0];
			}
			path.add(node);
		}
		int[] pathArray = new int[path.size()];
		for(int i = 0; i < path.size(); i++) {
			pathArray[path.size()-i-1] = path.get(i)+1;
		}
		return pathArray;
	}

	private static Stack<Integer> topoSort(int size, Map<Integer, List<long[]>> adjLists, Set<Integer> zeroIn) {
		boolean[] visited = new boolean[size];
		Stack<Integer> result = new Stack<>();
		zeroIn.forEach(k-> {
			topoSort(k, visited, result, adjLists);
		});
		return result;
	}

	private static void topoSort(int node, boolean[] visited, Stack<Integer> result, Map<Integer, List<long[]>> adjLists) {
		visited[node] = true;
		List<long[]> children = adjLists.get(node);
		if(children!=null) {
			for(long[] child : children) {
				if(!visited[(int)child[0]]) {
					topoSort((int)child[0], visited, result, adjLists);
				}
			}
		}
		result.push(node);
	}

	// Creates the map adjacency lists, the weight for each edge is also packaged in. 
	private static Map<Integer, List<long[]>> createAdjLists(int[] dagFrom, int[] dagTo, int[] dagWeight, Set<Integer> zeroIn) {
		Map<Integer, List<long[]>> adjLists = new HashMap<>();
		for(int i = 0; i < dagFrom.length; i++) {
			zeroIn.add(dagFrom[i]-1);
		}
		for(int i = 0; i < dagTo.length; i++) {
			zeroIn.remove(dagTo[i]-1);
		}
		for(int i = 0; i < dagFrom.length; i++) {
			adjLists.computeIfAbsent(dagFrom[i]-1, k-> {
				return new ArrayList<>();
				// the list elements contains 2 values: the neighbor id, and 
				// the negated weight of the edge. Note that since we negated
				// the edge weights, the longest path problem becomes a
				// shortest path problem.
			}).add(new long[] {dagTo[i]-1, -dagWeight[i]});
		}
		return adjLists;
	}
}
