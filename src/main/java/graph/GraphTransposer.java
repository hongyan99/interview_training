package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class GraphTransposer {
	public static void main(String[] args) {
		Map<Integer, Node> lookup = new HashMap<>();
//		Node root = updateGraph(lookup, 1, 2);
//		updateGraph(lookup, 2, 3);
//		updateGraph(lookup, 3, 1);
//		print(buildOtherGraph(root));
		
		System.out.println("-----------");
		
		lookup.clear();
		Node root = updateGraph(lookup, 1, 2, 3, 4);
		updateGraph(lookup, 2, 1, 3, 4);
		updateGraph(lookup, 3, 1, 2, 4);
		updateGraph(lookup, 4, 1, 2, 3);
		print(buildOtherGraph(root));
	}
	
	static Node updateGraph(Map<Integer, Node> lookup, int... vals) {
		Node node = getCreateNode(vals[0], lookup);
		for(int i = 1; i < vals.length; i++) {
			node.neighbours.add(getCreateNode(vals[i], lookup));
		}
		return node;
	}
	
	
	private static Node getCreateNode(int val, Map<Integer, Node> lookup) {
		Node result = lookup.get(val);
		if(result == null) {
			result = new Node(val);
			lookup.put(val, result);
		}
		return result;
	}

	static void print(Node node) {
		Stack<Node> stack = new Stack<>();
		stack.push(node);
		Map<Node, Boolean> visited = new HashMap<>();
		while(!stack.isEmpty()) {
			Node n = stack.pop();
			if(visited.get(n)!=null) {
				continue;
			}
			visited.put(n, Boolean.TRUE);
			n.neighbours.forEach(nn-> {
				System.out.println("" + n.val + ' ' + nn.val);
				if(visited.get(nn)==null) {
					stack.push(nn);
				}
			});
		}
	}
	
	static Node buildOtherGraph(Node node) {
		Map<Integer, Node> lookup = new HashMap<>();
		Map<Node, Boolean> visited = new HashMap<>();
		Stack<Node> stack = new Stack<>();
		stack.push(node);
		while(!stack.isEmpty()) {
			Node n = stack.pop();	
			if(visited.get(n)!=null) {
				continue;
			}
			visited.put(n, Boolean.TRUE);
			Node m = getCreateNode(n, lookup);
			n.neighbours.forEach(nn-> {
				Node mm = getCreateNode(nn, lookup);
				if(!mm.neighbours.contains(m)) {
					mm.neighbours.add(m);
				}
				if(visited.get(nn)==null) {
					stack.push(nn);
				}
			});
		}
		return lookup.get(node.val);
	}
	
	static Node getCreateNode(Node original, Map<Integer, Node> lookup) {
		Node result = lookup.get(original.val);
		if(result == null) {
			result = new Node(original.val);
			lookup.put(original.val, result);
		}
		return result;
	}
	
	static class Node {
		private Integer val;
		Vector<Node> neighbours = new Vector<Node>(0);
		Node(Integer value) {
			this.val = value;
		}
	}

}
