package graph.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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
 * @author hongyanli
 *
 */
public class CriticalConnections {
	public static void main(String[] args) {
		List<List<Integer>> connections = createConnections(
				new Integer[] {0,1}, new Integer[] {1,2}, new Integer[] {2,0}, new Integer[] {1,3});
		List<List<Integer>> result = findCriticalConnections(4, 4, connections);
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

		// We assume that all connections are critical, as we identify a cycle, we will 
		// remove all edges in the cycle and the remaining edges are the critical ones.
		final Map<Integer, Map<Integer, Boolean>> criticalConnections = new HashMap<>();
		adjacencyLists.forEach((key, list) -> {
			criticalConnections.put(key, fillMp(list));
		});
		for (int i = 0; i < noOfServers; i++) {
			if (visited.containsKey(i)) {
				continue;
			}

			visit(i, adjacencyLists, visited, parentMap, criticalConnections);
		}

		return toListOfList(criticalConnections);
	}

	private static Map<Integer, Boolean> fillMp(List<Integer> list) {
		Map<Integer, Boolean> map = new HashMap<>();
		list.forEach(key->{
			map.put(key, Boolean.TRUE);
		});
		return map;
	}

	private static List<List<Integer>> toListOfList(Map<Integer, Map<Integer, Boolean>> criticalConnections) {
		List<List<Integer>> returns = new ArrayList<>();
		Iterator<Integer> keysIter = criticalConnections.keySet().iterator();
		while (keysIter.hasNext()) {
			Integer key = keysIter.next();
			criticalConnections.get(key).forEach((value, flag) -> {
				returns.add(newPair(key, value));
				removePair(value, key, criticalConnections);
			});
			keysIter.remove();
		}
		if (returns.size() == 0) {
			returns.add(newPair(-1, -1));
		}
		return returns;
	}

	private static List<Integer> newPair(Integer key, Integer value) {
		List<Integer> pair = new ArrayList<>();
		pair.add(key);
		pair.add(value);
		return pair;
	}

	private static void visit(Integer key, Map<Integer, List<Integer>> adjacencyLists, Map<Integer, Integer> visited,
			Map<Integer, Integer> parentMap, Map<Integer, Map<Integer, Boolean>> criticalConnections) {
		if (!visited.containsKey(key)) {
			// we only visit the nodes that we have not yet visited
			visited.put(key, 1); // value "1" indicates that node is being visited (when done visiting we mark it as "0"
			List<Integer> children = adjacencyLists.get(key);
			if (children != null) {
				children.forEach(childKey -> {
					if (visited.containsKey(childKey) && visited.get(childKey).intValue() == 1) {
						if (!childKey.equals(parentMap.get(key))) {
							// We found a cycle, now record it by: for each edge in the cycle remove
							// it from the criticalConnection list. 
							recordCycle(key, childKey, parentMap, criticalConnections);
						}
					} else if(!visited.containsKey(childKey)) {
						parentMap.put(childKey, key);
						visit(childKey, adjacencyLists, visited, parentMap, criticalConnections);
					}
				});
			}
			visited.put(key, 0); // now mark that we are done visiting
		}
	}

	private static void recordCycle(Integer cycleTailKey, Integer cycleHeadKey, Map<Integer, Integer> parentMap,
			Map<Integer, Map<Integer, Boolean>> criticalConnections) {
		Integer parentKey = cycleTailKey;
		Integer key = cycleHeadKey;
		do {
			removePair(parentKey, key, criticalConnections);
			removePair(key, parentKey, criticalConnections);
			key = parentKey;
			parentKey = parentMap.get(key);
		} while (key != cycleHeadKey);
	}

	private static void removePair(Integer key, Integer key1, Map<Integer, Map<Integer, Boolean>> criticalConnections) {
		Map<Integer, Boolean> values = criticalConnections.get(key);
		if (values != null) {
			// two cycles may share the same edge and thus we need this != null check
			values.remove(key1);
		}
	}

	private static Map<Integer, List<Integer>> buildAdjacencyLists(int mapCapacity, List<List<Integer>> connections) {
		final Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>(mapCapacity);
		connections.forEach(l -> {
			getList(result, l.get(0)).add(l.get(1));
			getList(result, l.get(1)).add(l.get(0));
		});
		return result;
	}

	private static List<Integer> getList(Map<Integer, List<Integer>> result, Integer key) {
		List<Integer> l = result.getOrDefault(key, new ArrayList<Integer>());
		if (l.size() == 0) {
			result.put(key, l);
		}
		return l;
	}

}
