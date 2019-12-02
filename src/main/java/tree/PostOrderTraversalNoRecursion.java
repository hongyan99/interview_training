package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class PostOrderTraversalNoRecursion {
	public static void main(String[] args) {
		TreeNode root = create(1);
		root.left_ptr = create(2);
		root.right_ptr = create(3);
		root.left_ptr.left_ptr = create(4);
		root.left_ptr.right_ptr = create(5);
		System.out.println(Arrays.toString(postorderTraversal(root)));
	}
	
	private static int[] postorderTraversal(TreeNode root){
		if(root==null) {
			return new int[0];
		}
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		return postorderTraversal(stack);
	}

	private static int[] postorderTraversal(Stack<TreeNode> stack){
		Set<TreeNode> included = new HashSet<TreeNode>();
		List<Integer> values = new ArrayList<Integer>();
		while(!stack.isEmpty()) {
			TreeNode node = stack.peek();
			if(visited(included,node.left_ptr) && visited(included,node.right_ptr)) {
				values.add(node.val);
				stack.pop();
				included.add(node);
				continue;
			}
			if(node.right_ptr!=null && !included.contains(node.right_ptr)) {
				stack.push(node.right_ptr);
			}
			if(node.left_ptr!=null && !included.contains(node.left_ptr)) {
				stack.push(node.left_ptr);
			}
		}
		
		int[] returns = new int[values.size()];
		for(int i = 0; i < values.size(); i++) {
			returns[i] = values.get(i);
		}
		return returns;
	}
	
	private static boolean visited(Set<TreeNode> included, TreeNode node) {
		return node==null || included.contains(node);
	}
	
	private static TreeNode create(int val) {
		TreeNode node = new TreeNode();
		node.val = val;
		return node;
	}

	private static class TreeNode {
        public int val;
        public TreeNode left_ptr;
        public TreeNode right_ptr;
    }
}
