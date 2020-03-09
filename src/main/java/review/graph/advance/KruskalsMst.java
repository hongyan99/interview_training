package review.graph.advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import review.graph.WeightedEdge;

/**
 * Kruskal’s Minimum Spanning Tree Algorithm. This is simply a variation of the 
 * weighted quick find algorithm.
 * 
 * The down-side of this algorithm is that we have to have all the edges before 
 * we can run the algorithm since we have to sort the edges list first. While
 * the Prim's algorithm does not have the requirement.
 * 
 * @author Hongyan
 *
 */
public class KruskalsMst {
	public static void main(String[] args) {
		List<WeightedEdge> edges = new ArrayList<>();
		edges.add(new WeightedEdge(1, 7, 6));
		edges.add(new WeightedEdge(2, 8, 2));
		edges.add(new WeightedEdge(2, 6, 5));
		edges.add(new WeightedEdge(4, 0, 1));
		edges.add(new WeightedEdge(4, 2, 5));
		edges.add(new WeightedEdge(6, 8, 6));
		edges.add(new WeightedEdge(7, 2, 3));
		edges.add(new WeightedEdge(7, 7, 8));
		edges.add(new WeightedEdge(8, 0, 7));
		edges.add(new WeightedEdge(8, 1, 2));
		edges.add(new WeightedEdge(9, 3, 4));
		edges.add(new WeightedEdge(10, 5, 4));
		edges.add(new WeightedEdge(11, 1, 7));
		edges.add(new WeightedEdge(14, 3, 5));
		
		List<WeightedEdge> result = mst(edges, 9);
		result.forEach(e-> {
			System.out.print(e.getValue()+", ");
			System.out.print(e.getSource()+", ");
			System.out.println(e.getDest());
		});
	}
	
	public static List<WeightedEdge> mst(List<WeightedEdge> edges, int dim) {
		// 1) Initialize the parent and size array
		int comp = dim;
		int[] parent = new int[dim];
		for(int i = 0; i < dim; i++) {
			parent[i]=i; // start with all nodes disconnected
		}
		int[] size = new int[dim];
		Arrays.fill(size, 1); // start with all nodes disconnected

		// 2) Sort the edges by weight (light to heavy)
		Collections.sort(edges, new Comparator<WeightedEdge>() {
			@Override
			public int compare(WeightedEdge e1, WeightedEdge e2) {
				return e1.getValue()-e2.getValue();
			}
		});

		// 3) Build minimum-weight non-cyclic graph
		List<WeightedEdge> result = new ArrayList<>();
		for(WeightedEdge e : edges) {
			int rSource = find(parent, e.getSource());
			int rDest = find(parent, e.getDest());
			// Skip cyclic connections
			if(rSource!=rDest) {
				// Connect the smaller component to the larger
				if(size[rSource]>=rDest) {
					parent[rDest] = rSource;
					size[rSource] += size[rDest];
					comp--;
				} else {
					parent[rSource] = rDest;
					size[rDest] += size[rSource];
					comp--;
				}
				result.add(e);
				if(comp==1) {
					break;
				}
			}
		}
		
		return comp==1? result : null;
	}
	
	// Find parent
	private static int find(int[] parent, int node) {
		int p = parent[node];
		while(p!=parent[p]) {
			p=parent[p];
		}
		// path compression to improve amortized complexity
		parent[node] = p; 
		return p;
	}
}
