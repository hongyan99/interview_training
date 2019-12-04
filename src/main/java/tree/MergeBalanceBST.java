package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MergeBalanceBST {
	public static void main(String[] args) {
		Node root1 = buildNode(new int[] {10, 20}, 0, 2);
		Node root2 = buildNode(new int[] {15}, 0, 1);
		printNodes(mergeTwoBSTs(root1, root2));
	}
	
	public static Node mergeTwoBSTs(Node root1, Node root2) {
		List<Integer> list1 = toList(root1);
		List<Integer> list2 = toList(root2);
		int[] mergedArr = new int[list1.size()+list2.size()];
		int idx1 = 0, idx2 = 0, idx = 0;
		while(idx1<list1.size() && idx2<list2.size()) {
			if(list1.get(idx1)<=list2.get(idx2)) {
				mergedArr[idx++] = list1.get(idx1++);
			} else {
				mergedArr[idx++] = list2.get(idx2++);
			}
		}
		
		if(idx1<list1.size()) {
			for(int i = idx1; i < list1.size(); i++) {
				mergedArr[idx++] = list1.get(i);
			}
		}
		
		if(idx2<list2.size()) {
			for(int i = idx2; i < list2.size(); i++) {
				mergedArr[idx++] = list2.get(i);
			}
		}
		
		return buildNode(mergedArr, 0, mergedArr.length);
	}

	private static Node buildNode(int[] mergedArr, int start, int end) {
		if(start<end) {
			int mid = (start+end) / 2;
			Node node = new Node(mergedArr[mid]);
			node.left = buildNode(mergedArr, start, mid);
			node.right = buildNode(mergedArr, mid+1, end);
			return node;
		}
		return null;
	}

	private static List<Integer> toList(Node root) {
		List<Integer> list = new ArrayList<>();
		Stack<Node> stack = new Stack<>();
		stack.push(root);
		toList(stack, list);
		return list;
	}
	
	private static void toList(Stack<Node> stack, List<Integer> list) {
		while(!stack.isEmpty()) {
			Node node = stack.pop();
			if(node.left!=null) {
				stack.push(node.left);
				toList(stack, list);
			}
			list.add(node.key);
			if(node.right!=null) {
				stack.push(node.right);
				toList(stack, list);
			}
		}
	}
	
	private static void printNodes(Node root) {
		Stack<Node> stack = new Stack<>();
		stack.push(root);
		print(stack);
	}

	private static void print(Stack<Node> stack) {
		if(stack.isEmpty()) {
			return;
		}
		
		Node node = stack.pop();
		System.out.println(node.key);
		if(node.left!=null) {
			System.out.print("L: ");
			stack.push(node.left);
			print(stack);
		}
		if(node.right!=null) {
			System.out.print("R: ");
			stack.push(node.right);
			print(stack);
		}
	}
	
	private static class Node {
		int key;
		Node left;
		Node right;
		
		public Node(int key) {
			this.key = key;
		}
	}
}
