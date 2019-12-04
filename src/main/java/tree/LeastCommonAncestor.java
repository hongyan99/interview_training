package tree;

import java.util.ArrayList;
import java.util.List;

public class LeastCommonAncestor {
	public static void main(String[] args) {
		Node[] nodes = new Node[9];
		for(int i = 0; i < 9; i++) {
			nodes[i] = create(i+1);
		}
		nodes[0].left = nodes[1];
		nodes[0].right = nodes[2];
		nodes[1].left = nodes[3];
		nodes[1].right = nodes[4];
		nodes[2].left = nodes[5];
		nodes[2].right = nodes[6];
		nodes[4].left = nodes[7];
		nodes[4].right = nodes[8];
		
		LeastCommonAncestor lca = new LeastCommonAncestor();
		System.out.println(lca.lca(nodes[0], nodes[1], nodes[4]));
		System.out.println(lca.lca(nodes[0], nodes[1], nodes[2]));
	}
	
	private int lca(Node head, Node a, Node b) {
		if(a==b) {
			return a.data;
		}
		List<List<Node>> result = new ArrayList<>();
		findPaths(head, a, b, result, new ListNode(head));
		List<Node> pathA = result.get(0);
		List<Node> pathB = result.get(1);
		Node commonNode = head;
		for(int i = 0; i< pathA.size(); i++) {
			if(pathA.get(i)==pathB.get(i)) {
				commonNode = pathA.get(i);
			} else {
				break;
			}
		}
        return commonNode.data;
    }

	private List<Node> toPath(ListNode node) {
		List<Node> list = new ArrayList<>();
		while(node!=null) {
			list.add(0, node.node);
			node = node.next;
		}
		return list;
	}

	private void findPaths(Node node, Node a, Node b, List<List<Node>> result, ListNode nodeList) {
		if(node==null) return;

		nodeList = nodeList.insert(node);
		if(node==a) result.add(toPath(nodeList));
		if(node==b) result.add(toPath(nodeList));

		if(result.size()==2) return;

		findPaths(node.left, a, b, result, nodeList);
		findPaths(node.right, a, b, result, nodeList);
	}

	private static class ListNode {
		public final Node node;
		private ListNode next;
		
		public ListNode(Node node) {
			this.node = node;
		}
		
		public ListNode insert(Node next) {
			ListNode before = new ListNode(next);
			before.next = this;
			return before;
		}
	}
	
	private static Node create(int val) {
		Node node = new Node();
		node.data = val;
		return node;
	}
	
	private static class Node {
		int data;
		Node left;
		Node right;
	}
}
