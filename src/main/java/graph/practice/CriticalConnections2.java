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
 * 1. Do a DFS traversal and along the way, mark each node with an arrival time (every step forward, 
 *    increment the arrival time as a counter. We use a map to store the arrival time.
 * 2. The moment we reach a child node that has already been marked with an earlier arrival time, we
 *    store the lower arrival time to the lowestTime map.
 * 3. Also, as we unwind back the call stack (right after the recursive call), we store the lowest
 *    arrival time (minimum of the parent and the child arrival time) to the parent as its lowest time.
 * 4. The result is that all nodes in a cycle will be marked with the lowest arrival time within the 
 *    cycle. Since in a bridge edge, one or both of the two nodes of the edge might fall into a cycle
 *    and thus the two sides will have two different time. More precisely, follow the direct of the 
 *    DAG, the child node will have a lowestTime that is greater than the arrivalTime of the parent
 *    node.
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
		final Map<Integer, Integer> arrivalTime = new HashMap<>(); // also function as visited Map
		final Map<Integer, Integer> lowestTime = new HashMap<>();
		final List<List<Integer>> result = new ArrayList<>();

		for (int i = 0; i < noOfServers; i++) {
			if (arrivalTime.get(i)!=null) {
				continue;
			}

			visit(i, adjacencyLists, parentMap, lowestTime, arrivalTime, 0, result);
		}

		if(result.size()==0) {
			addConnection(result, -1, -1);
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

	private static void visit(Integer u, Map<Integer, List<Integer>> adjLists, Map<Integer, Integer> parentMap, 
			Map<Integer, Integer> lowestTime, Map<Integer, Integer> arrivalTime, int time, List<List<Integer>> critical) {

		// we only visit the nodes that we have not yet visited
		List<Integer> children = adjLists.get(u);
		arrivalTime.put(u, time); // also function as visited Map
		lowestTime.put(u, time);
		if (children != null) {
			for(Integer v : children) {
				if (arrivalTime.get(v)==null) {
					parentMap.put(v, u);
					visit(v, adjLists, parentMap, lowestTime, arrivalTime, time + 1, critical);
					lowestTime.put(u, Math.min(lowestTime.get(v), lowestTime.get(u)));
					if(lowestTime.get(v) > arrivalTime.get(u)) {
						addConnection(critical, u, v);
					}
				} else if (!v.equals(parentMap.get(u))) {
					lowestTime.put(u, Math.min(lowestTime.get(v), lowestTime.get(u)));
				}
			}
		}
	}

	private static void addConnection(List<List<Integer>> critical, Integer u, Integer v) {
		List<Integer> edge = new ArrayList<>();
		edge.add(u);
		edge.add(v);
		critical.add(edge);
	}

	private static List<Integer> getList(Map<Integer, List<Integer>> result, Integer key) {
		List<Integer> l = result.getOrDefault(key, new ArrayList<Integer>());
		if (l.size() == 0) {
			result.put(key, l);
		}
		return l;
	}

}
