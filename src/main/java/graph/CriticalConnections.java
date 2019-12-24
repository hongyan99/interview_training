package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CriticalConnections {
	public static void main(String[] args) {
		@SuppressWarnings("unchecked")
		List<List<Integer>> connections = createConnections(newPair(0,1), newPair(1,2), newPair(2,0), newPair(1,3));
		List<List<Integer>> criticalConnections = findCriticalConnections(4, 4, connections);
		criticalConnections.forEach(l-> {
			System.out.println("(" + l.get(0) + ", " + l.get(1) + ")");
		});
//		List<List<Integer>> criticalConnections = findCriticalConnections(1, 0, new ArrayList<>());
//		criticalConnections.forEach(l-> {
//			System.out.println("(" + l.get(0) + ", " + l.get(1) + ")");
//		});
	}
	
	private static List<List<Integer>> createConnections(
			@SuppressWarnings("unchecked") List<Integer> ... pairs) {
		return Arrays.asList(pairs);
	}
	
	public static List<List<Integer>> findCriticalConnections(
			int noOfServers, int noOfConnections, List<List<Integer>> connections) {
    	final Map<Integer, List<Integer>> adjacencyLists = buildAdjacencyLists(connections);
    	// We will use the parentMap combined with the visited map to find the cycles and mark the nodes
    	//  in cycle, thus any node not marked as in a cycle are critical.
    	Map<Integer, Integer> parentMap = new HashMap<>();
    	// we use a map to store the visited. If the value is 1, it denotes the node is visited. If the value
    	// is 0, it indicate that the node is done visiting. And at last, when we found a cycle, we will update
    	// value to 2, and thus at the end, all nodes that have visited value equal to 0 are the ones that are
    	// critical.
    	Map<Integer, Integer> visited = new HashMap<>();
    	
    	// TODO: Need to make sure that we are copying the values of the map values not their references
    	final Map<Integer, List<Integer>> criticalConnections = new HashMap<>();
    	adjacencyLists.forEach((key, value)-> {
    		criticalConnections.put(key, new ArrayList<>(value));
    	});
    	for(int i = 0; i< noOfServers; i++) {
    		if(visited.containsKey(i)) {
    			continue;
    		}

    		visit(i, adjacencyLists, visited, parentMap, criticalConnections);
    	}
		
		return toListOfList(criticalConnections);
    }

	private static List<List<Integer>> toListOfList(Map<Integer, List<Integer>> criticalConnections) {
		List<List<Integer>> returns = new ArrayList<>();
		Iterator<Integer> keysIter = criticalConnections.keySet().iterator();
		while(keysIter.hasNext()) {
			Integer key = keysIter.next();
			criticalConnections.get(key).forEach(value-> {
			returns.add(newPair(key, value));
			removePair(value, key, criticalConnections);
			});
			keysIter.remove();
		}
		if(returns.size()==0) {
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

	private static void visit(int key, Map<Integer, List<Integer>> adjacencyLists, 
			Map<Integer, Integer> visited, Map<Integer, Integer> parentMap,
			Map<Integer, List<Integer>> criticalConnections) {
		if(!visited.containsKey(key)) {
			visited.put(key, 1);
			List<Integer> children = adjacencyLists.get(key);
			if(children!=null) {
				children.forEach(childKey-> {
					if(visited.containsKey(childKey) && visited.get(childKey).intValue()==1) {
						if(!childKey.equals(parentMap.get(key))) {
							// We found a cycle, now record it by removing all edges in the cycle
							recordCycle(key, childKey, parentMap, criticalConnections);
						}
					} else {
						parentMap.put(childKey, key);
						visit(childKey, adjacencyLists, visited, parentMap, criticalConnections);
					}
				});
			}
			visited.put(key, 0);
		}
	}

	private static void recordCycle(Integer key, Integer cycleHeadKey, Map<Integer, Integer> parentMap,
			Map<Integer, List<Integer>> criticalConnections) {
		removePair(key, cycleHeadKey, criticalConnections);
		removePair(cycleHeadKey, key, criticalConnections);
		Integer parentKey = parentMap.get(key);
		while(key!=cycleHeadKey) {
			removePair(parentKey, key, criticalConnections);
			removePair(key, parentKey, criticalConnections);
			key = parentKey;
			parentKey = parentMap.get(key);
		}
	}

	private static void removePair(Integer key, Integer key1,
			Map<Integer, List<Integer>> criticalConnections) {
		List<Integer> values = criticalConnections.get(key);
		if(values!=null) {
			values.remove(key1);
		}
	}

	private static Map<Integer, List<Integer>> buildAdjacencyLists(List<List<Integer>> connections) {
    	final Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>();
    	connections.forEach(l-> {
    		getList(result, l.get(0)).add(l.get(1));
    		getList(result, l.get(1)).add(l.get(0));
    	});
		return result;
	}

	private static List<Integer> getList(Map<Integer, List<Integer>> result, Integer key) {
		List<Integer> l = result.getOrDefault(key, new ArrayList<Integer>());
		result.put(key, l);
		return l;
	}
}
