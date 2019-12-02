package tree;

import java.util.concurrent.atomic.AtomicInteger;

// Given a binary tree, find the number of unival subtrees 
// (the unival tree is a tree which has the same value for every node in it). 
public class UniversalSubTree {
	public static void main(String[] args) {
		TreeNode root = create(5);
		root.left_ptr = create(5);
		root.right_ptr = create(5);
		root.left_ptr.left_ptr = create(5);
		root.left_ptr.right_ptr = create(5);
		root.right_ptr.left_ptr = create(4);
		root.right_ptr.right_ptr = create(5);
		System.out.println(findSingleValueTrees(root));
	}
	
	private static int findSingleValueTrees(TreeNode root) {
		AtomicInteger count = new AtomicInteger();
		findSingleValueTrees(root, count);
		return count.get();
	}
	
	private static boolean findSingleValueTrees(TreeNode root, AtomicInteger count) {
		if(root==null) {
			return true;
		}
		
		boolean returns = true;
		if(!check(root.val, root.left_ptr, count)) {
			returns = false;
		}
		if(!check(root.val, root.right_ptr, count)) {
			returns = false;
		}
		if(returns == true) {
			count.incrementAndGet();
		}
		return returns;
	}
	
	private static boolean check(int val, TreeNode node, AtomicInteger count) {
		return (node==null || (findSingleValueTrees(node, count) && node.val==val));
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
