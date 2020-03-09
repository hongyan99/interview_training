package review.graph.advance;

import java.util.Arrays;

import review.graph.Edge;

/**
 * Union find algorithm allows on to build component as edges are coming in.
 * 
 * @author Hongyan
 *
 */
public class WeightQuickFind {
	public static void main(String[] args) {
		int count = 15;
		int[] parent = new int[count];
		for(int i=0; i < count; i++) {
			parent[i] = i;
		}
		int[] size = new int[count];
		Arrays.fill(size, 1);
		int comp = count;
		comp = wqu(parent, size, new Edge(0,13), comp);
		comp = wqu(parent, size, new Edge(1,2), comp);
		comp = wqu(parent, size, new Edge(1,7), comp);
		comp = wqu(parent, size, new Edge(1,8), comp);
		comp = wqu(parent, size, new Edge(2,3), comp);
		comp = wqu(parent, size, new Edge(2,6), comp);
		comp = wqu(parent, size, new Edge(3,4), comp);
		comp = wqu(parent, size, new Edge(5,4), comp);
		comp = wqu(parent, size, new Edge(3,5), comp);
		comp = wqu(parent, size, new Edge(8,9), comp);
		comp = wqu(parent, size, new Edge(8,12), comp);
		comp = wqu(parent, size, new Edge(9,10), comp);
		comp = wqu(parent, size, new Edge(9,11), comp);
		comp = wqu(parent, size, new Edge(13,1), comp);
		System.out.println(comp);
		System.out.println(Arrays.toString(parent));
		System.out.println(Arrays.toString(size));
	}
	
	/**
	 * This is the weight quick union algorithm.
	 * 
	 * @param parent stores the parent pointer. A root parent always points to itself.
	 * @param size stores the component size. The size corresponding to a root node is 
	 * 		the component size.
	 * @param edge the new edge to include.
	 * @param comp the number of components.
	 * @return the updated number of components.
	 */
	public static int wqu(int[] parent, int[] size, Edge edge, int comp) {
		int rootU = findRoot(parent, edge.getSource());
		int rootV = findRoot(parent, edge.getDest());
		
		if(rootU==rootV) {
			// already connected.
			return comp;
		}
		
		// Not connected yet, check weight and connect the smaller one to the larger
		if(size[rootU]>=size[rootV]) {
			parent[rootV] = rootU;
			size[rootU] += size[rootV];
			comp--;
		} else {
			parent[rootU] = rootV;
			size[rootV] += size[rootU];
			comp--;
		}
		return comp;
	}
	
	// Find parent
	private static int findRoot(int[] parent, int i) {
		int p = i;
		while(parent[p]!=p) {
			p = parent[p];
		}
		// path compression to improve amortized complexity
		parent[i]=p;
		return p;
	}
}
