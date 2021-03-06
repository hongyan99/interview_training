package review.graph.advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import review.graph.WeightedEdge;

public class Dijkstra {
	public static void main(String[] args) {
		List<WeightedEdge> edges = new ArrayList<>();
		edges.add(new WeightedEdge(4, 0, 1));
		edges.add(new WeightedEdge(20, 7, 6));
		edges.add(new WeightedEdge(2, 8, 2));
		edges.add(new WeightedEdge(2, 6, 5));
		edges.add(new WeightedEdge(4, 2, 5));
		edges.add(new WeightedEdge(6, 8, 6));
		edges.add(new WeightedEdge(7, 2, 3));
		edges.add(new WeightedEdge(7, 7, 8));
		edges.add(new WeightedEdge(8, 0, 7));
		edges.add(new WeightedEdge(1, 1, 2));
		edges.add(new WeightedEdge(9, 3, 4));
		edges.add(new WeightedEdge(10, 5, 4));
		edges.add(new WeightedEdge(1, 1, 7));
		edges.add(new WeightedEdge(14, 3, 5));
		
		int[] distance = shortest(edges, 9);
		System.out.println(Arrays.toString(distance));
	}
	
	public static int[] shortest(List<WeightedEdge> edges, int dim) {
		if(dim<1 || edges.size()<dim-1) {
			return null;
		}
		
		// 1) Initialization
		// 1.a) Construct the adjacency list
		final Map<Integer, List<WeightedEdge>> adjListMap = new HashMap<>();
		edges.forEach(e-> {
			adjListMap.computeIfAbsent(e.getSource(), n->{
				return new ArrayList<WeightedEdge>();
			}).add(new WeightedEdge(e.getValue(), e.getSource(), e.getDest()));
		});
		
		// 1.b) Construct the priority queue
		PriorityQueue<WeightedEdge> queue = new PriorityQueue<>(new Comparator<WeightedEdge>() {
			@Override
			public int compare(WeightedEdge o1, WeightedEdge o2) {
				return o1.getDistance()-o2.getDistance();
			}
		});
		
		// 1.c) Initialize the captured and the queue
		int[] distances = new int[dim];
		boolean[] captured = new boolean[dim];
		WeightedEdge root = edges.get(0);
		captured[root.getSource()] = true;
		addEdgesFor(0, root.getSource(), adjListMap, captured, queue, distances);

		// 2) Loop through the edges in the queue
		List<WeightedEdge> result = new ArrayList<>();
		while(!queue.isEmpty()) {
			WeightedEdge edge = getNext(queue, captured);
			if(edge!=null) {
				result.add(edge);
				addEdgesFor(edge.getDistance(), edge.getDest(), adjListMap, captured, queue, distances);
			}
		}
		return distances;
	}
	
	// Fetch the next shortest path that at least one end is not captured 
	private static WeightedEdge getNext(PriorityQueue<WeightedEdge> queue, boolean[] captured) {
		if(queue.isEmpty()) {
			return null;
		}
		WeightedEdge edge = queue.poll();
		if(captured[edge.getDest()]) {
			return getNext(queue, captured);
		}
		// mark the other end of the edge as captured
		captured[edge.getDest()] = true;
		return edge;
	}
	
	// Add all edges from the newly captured node to the priority queue.
	private static void addEdgesFor(int distance, int node, Map<Integer, List<WeightedEdge>> adjListMap, 
			boolean[] captured, PriorityQueue<WeightedEdge> queue, int[] distances) {
		List<WeightedEdge> adjList = adjListMap.get(node);
		if(adjList!=null) {
			adjList.forEach(e-> {
				if(!captured[e.getDest()]) {
					// Only is one end is not captured we add the edge to the queue
					WeightedEdge ee = new WeightedEdge(e.getValue(), e.getSource(), e.getDest());
					ee.setDistance(distance+e.getValue());
					distances[ee.getDest()] = ee.getDistance();
					queue.add(ee);
				}
			});
		}
	}
}
