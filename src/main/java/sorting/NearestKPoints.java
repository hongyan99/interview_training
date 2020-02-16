package sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Given a point p, and other n points in two-dimensional space, find k points out 
 * of n points which are nearest to p.
 * 
 * @author Hongyan
 *
 */
public class NearestKPoints {
	public static void main(String[] args) {
		print(nearest_neighbours(0, 0, 2, makePoints(1, 1, 0, 1, 1, 0)));
	}
	
	private static void print(List<List<Integer>> points) {
		points.forEach(p-> {
			System.out.println(""+p.get(0)+", " + p.get(1));
		});
	}
	
	private static List<List<Integer>> makePoints(int... xys) {
		List<List<Integer>> points = new ArrayList<>();
		for(int i = 0; i < xys.length; i+=2) {
			List<Integer> p = new ArrayList<>();
			p.add(xys[i]);
			p.add(xys[i+1]);
			points.add(p);
		}
		return points;
	}
	
    public static List<List<Integer>> nearest_neighbours(int p_x, int p_y, int k, List<List<Integer>> n_points) {
        PriorityQueue<Node> points = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o2.compareTo(o1);
			}
		});
        n_points.forEach(p-> {
        	points.add(new Node(p_x, p_y, p.get(0), p.get(1)));
        	if(points.size()>k) {
        		points.poll();
        	}
        });
        List<List<Integer>> returns = new ArrayList<>();
        while(!points.isEmpty()) {
        	returns.add(points.poll().getPoint());
        }
        return returns;
    }
    
    private static class Node implements Comparable<Node> {
    	private final Double distance;
    	private final int x;
    	private final int y;
    	
    	public Node(int x0, int y0, int x, int y) {
    		this.distance = Math.sqrt((0L+x-x0)*(0L+x-x0)+(0L+y-y0)*(0L+y-y0));
    		this.x = x;
    		this.y = y;
    	}
    	
    	public List<Integer> getPoint() {
    		List<Integer> returns = new ArrayList<>();
    		returns.add(x);
    		returns.add(y);
    		return returns;
    	}

		@Override
		public int compareTo(Node o) {
			return o==null? 1 : distance.compareTo(o.distance);
		}
    }
}
