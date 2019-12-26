package graph.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * You need to take n courses and these courses are labeled from 0 to n-1. Few of 
 * these courses have prerequisites. You are given the prerequisites as a list of 
 * pairs where each pair is of form : [x, y]  where to take course 'x', you need 
 * to complete course 'y' before it. Given these pairs and also the count of total 
 * courses n, you need to return the ordering in which the courses should be 
 * taken. Note that there might be multiple possible answers, you need to just 
 * return any one of them and if any answer does not exist, return an array having 
 * -1.
 * 
 * @author hongyanli
 *
 */
public class CourseSchedule {
	public static void main(String[] args) {
		List<List<Integer>> prerequisites = new ArrayList<>();
		addPrerequisite(prerequisites, 1, 0);
		addPrerequisite(prerequisites, 2, 0);
		addPrerequisite(prerequisites, 3, 1);
		addPrerequisite(prerequisites, 3, 2);
		
		List<Integer> result = courseSchedule(4, prerequisites);
		System.out.println(Arrays.toString(result.toArray(new Integer[result.size()])));
	}
	
	private static void addPrerequisite(List<List<Integer>> prerequisites, int a, int b) {
		List<Integer> pair = Arrays.asList(new Integer[] {a, b});
		prerequisites.add(pair);
	}
	
    private static List<Integer> courseSchedule(int n, List<List<Integer>> prerequisites) {
    	final Map<Integer, List<Integer>> adjacencyLists = new HashMap<Integer, List<Integer>>();
    	prerequisites.forEach(pair-> {
    		List<Integer> list = adjacencyLists.getOrDefault(pair.get(0), new ArrayList<>());
    		list.add(pair.get(1));
    		adjacencyLists.put(pair.get(0), list);
    	});
    	
    	final List<Integer> ordered = new ArrayList<>();
    	// Helps track nodes that are been visited (include those already done visiting and those 
    	// that are not yet done visiting)
    	final Set<Integer> visited = new TreeSet<>();
    	// Helps track nodes that are been visited not but yet done visiting
    	final Set<Integer> visiting = new TreeSet<>();
    	for(int p = 0; p < n; p++) {
    		if(!visited.contains(p)) {
    			boolean hasCycle = sort(p, adjacencyLists, visited, ordered, visiting);
    			if(hasCycle) {
    				ordered.clear();
    				ordered.add(-1);
    				break;
    			}
    		}
    	};
    	
		return ordered;
    }
    
    private static boolean sort(Integer parent, Map<Integer, List<Integer>> adjacencyLists, 
    		Set<Integer> visited, List<Integer> ordered, Set<Integer> visiting) {
    	if(visiting.contains(parent)) {
    		return true;
    	}
		if(!visited.contains(parent)) {
			visiting.add(parent);
			visited.add(parent);
			List<Integer> children = adjacencyLists.get(parent);
			if(children!=null) {
				for(Integer c : children) {
					boolean hasCycle = sort(c, adjacencyLists, visited, ordered, visiting);
					if(hasCycle) {
						return true;
					}
				};
			}
			visiting.remove(parent);
			ordered.add(parent);
		}
		return false;
    }

}
