package tree;

import java.util.Stack;

public class TreeFlipper {
	private static class TreeNode {
	    public int val;
	    public TreeNode left_ptr;
	    public TreeNode right_ptr;
	}

	public static void main(String[] args) {
		TreeNode n6 = create(6);
		TreeNode n4 = create(4);
		TreeNode n7 = create(7);
		n6.left_ptr = n4;
		n6.right_ptr = n7;
		TreeNode n2 = create(2);
		TreeNode n5 = create(5);
		n4.left_ptr = n2;
		n4.right_ptr = n5;
		TreeNode n1 = create(1);
		TreeNode n3 = create(3);
		n2.left_ptr = n1;
		n2.right_ptr = n3;
		printNodes(flipUpsideDown(n6));
	}
	
	private static TreeNode flipUpsideDown(TreeNode root){
		if(root==null) {
			return null;
		}
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		TreeNodeHolder holder = new TreeNodeHolder();
		flip(stack, holder, true);
		return holder.node;
	}
	
	private static TreeNode flip(Stack<TreeNode> stack, TreeNodeHolder holder, boolean isLeft) {
		if(stack.isEmpty()) {
			return null;
		}
		
		TreeNode node = stack.pop();
		TreeNode tail = create(node.val);
		if(node.left_ptr==null) {
			if(isLeft) {
				holder.node = tail;
			}
			return tail;
		} else {
			stack.push(node.left_ptr);
			TreeNode parent = flip(stack, holder, true);
			parent.right_ptr = tail;
			stack.push(node.right_ptr);
			TreeNode child = flip(stack, holder, false);
			parent.left_ptr = child;
			return tail;
		}
	}
	
	private static class TreeNodeHolder {
		TreeNode node;
	}
	
	private static TreeNode create(int val) {
		TreeNode node = new TreeNode();
		node.val = val;
		return node;
	}
	
	private static void printNodes(TreeNode root) {
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		print(stack);
	}

	private static void print(Stack<TreeNode> stack) {
		if(stack.isEmpty()) {
			return;
		}
		
		TreeNode node = stack.pop();
		System.out.println(node.val);
		if(node.left_ptr!=null) {
			System.out.print("L: ");
			stack.push(node.left_ptr);
			print(stack);
		}
		if(node.right_ptr!=null) {
			System.out.print("R: ");
			stack.push(node.right_ptr);
			print(stack);
		}
	}
}

