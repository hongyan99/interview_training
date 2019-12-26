package graph.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming 
 * a network where connections[i] = [a, b] represents a connection between servers a and b. Any server can 
 * reach any other server directly or indirectly through the network.
 * 
 * A critical connection is a connection that, if removed, will make some x server unable to reach some 
 * other y server which were initially reachable.
 * 
 * Return all critical connections in the network in any order.
 * 
 * Note: In case of no critical connection found then return a 2D array with single row containing two 
 * elements having value -1 i.e. [[-1, -1]].
 * 
 * In this implementation, we will use the bridge searching algorithm. THe algorithm works as below:
 * 
 * 1. Do a DFS traversal and along the way, mark each node with the index (every step forward, increment 
 *    the index, we name it "level", and the map that store the index we name it levelMap).
 * 2. The moment we reach a child node that has already been marked with a lower level, override the 
 *    node with that lower level value.
 * 3. Also, as we unwind back the call stack (right after the recursive call), override the node level
 *    with this lower level value (use min function).
 * 4. The result is that all nodes in a cycle will be marked with the index that is the lowest in the 
 *    cycle. Since a bridge edge is not part of a cycle, the two nodes of the edge don't have the same
 *    level value which tells us it is a bridge edge.
 *    
 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE 
 * This is not yet fully working!
 * 
 * 
 * @author hongyanli
 *
 */
public class CriticalConnections2 {
	public static void main(String[] args) {
		List<List<Integer>> connections = createConnections(
				new Integer[] {0,1}, new Integer[] {1,2}, new Integer[] {2,0}, new Integer[] {1,3});
		List<List<Integer>> result = findCriticalConnections(4, 4, connections);
		print(result);
		connections = createConnections(new Integer[] {0,1});
		result = findCriticalConnections(2, 1, connections);
		print(result);
	}
	
	private static void print(List<List<Integer>> result) {
		System.out.print("[");
		for(int k = 0; k < result.size(); k++) {
			System.out.print("(" + result.get(k).get(0) + ", " + result.get(k).get(1) + ")");
			if(k!=result.size()-1) {
				System.out.println(", ");
			}
		}
		System.out.println("]");
	}

	private static List<List<Integer>> createConnections(Integer[]... arrays) {
		List<List<Integer>> returns = new ArrayList<>();
		for(int k=0; k<arrays.length; k++) {
			returns.add(Arrays.asList(arrays[k]));
		}
		return returns;
	}

	public static List<List<Integer>> findCriticalConnections(int noOfServers, int noOfConnections,
			List<List<Integer>> connections) {
		final Map<Integer, List<Integer>> adjacencyLists = buildAdjacencyLists(noOfServers, connections);
		// We will use the parentMap combined with the visited map to find the cycles
		// and mark the nodes
		// in cycle, thus any node not marked as in a cycle are critical.
		Map<Integer, Integer> parentMap = new HashMap<>();
		// we use a map to store the visited. If the value is 1, it denotes the node is
		// visited. If the value
		// is 0, it indicate that the node is done visiting. And at last, when we found
		// a cycle, we will update
		// value to 2, and thus at the end, all nodes that have visited value equal to 0
		// are the ones that are
		// critical.
		Map<Integer, Integer> visited = new HashMap<>();
		
		final Map<Integer, Integer> levelMap = new HashMap<>();

		for (int i = 0; i < noOfServers; i++) {
			if (visited.containsKey(i)) {
				continue;
			}

			visit(i, adjacencyLists, visited, parentMap, levelMap, -1);
		}

		return packageResult(levelMap, connections);
	}

	private static List<List<Integer>> packageResult(Map<Integer, Integer> levelMap, List<List<Integer>> connections) {
		List<List<Integer>> result = new ArrayList<>();
		connections.forEach(l-> {
			if(levelMap.get(l.get(0))!=levelMap.get(l.get(1))) {
				result.add(l);
			}
		});
		if (result.size() == 0) {
			List<Integer> pair = new ArrayList<>();
			pair.add(-1);
			pair.add(-1);
			result.add(pair);
		}
		return result;
	}

	private static Map<Integer, List<Integer>> buildAdjacencyLists(int mapCapacity, List<List<Integer>> connections) {
		final Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>(mapCapacity);
		connections.forEach(l -> {
			getList(result, l.get(0)).add(l.get(1));
			getList(result, l.get(1)).add(l.get(0));
		});
		return result;
	}

	private static void visit(Integer key, Map<Integer, List<Integer>> adjacencyLists, Map<Integer, Integer> visited,
			Map<Integer, Integer> parentMap, Map<Integer, Integer> levelMap, int priorLevel) {
		final int level = priorLevel + 1;
		// we only visit the nodes that we have not yet visited
		visited.put(key, 1); // value "1" indicates that node is being visited (when done visiting we mark it as "0"
		List<Integer> children = adjacencyLists.get(key);
		levelMap.put(key, level);
		if (children != null) {
			children.forEach(childKey -> {
				if (!childKey.equals(parentMap.get(key))) {
					if (visited.containsKey(childKey)) {
						levelMap.put(key, Math.min(levelMap.get(childKey), levelMap.get(key)));
					} else {
						parentMap.put(childKey, key);
						visit(childKey, adjacencyLists, visited, parentMap, levelMap, level);
						levelMap.put(key, Math.min(levelMap.get(childKey), levelMap.get(key)));
					}
				}
			});
		}
	}

	private static List<Integer> getList(Map<Integer, List<Integer>> result, Integer key) {
		List<Integer> l = result.getOrDefault(key, new ArrayList<Integer>());
		if (l.size() == 0) {
			result.put(key, l);
		}
		return l;
	}

}
