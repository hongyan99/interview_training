package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreePathFinder {
	public static void main(String[] args) {
		TreeNode root = create(1);
		root.left_ptr = create(2);
		root.right_ptr = create(3);
		root.left_ptr.left_ptr = create(4);
		root.left_ptr.right_ptr = create(5);
		root.right_ptr.left_ptr = create(6);
		root.right_ptr.right_ptr = create(7);
		List<List<Integer>> result = allPathsOfABinaryTree(root);
		for(int i = 0; i < result.size(); i++) {
			List<Integer> list = result.get(i);
			System.out.println(Arrays.toString(list.toArray()));
		}
	}
	
	private static List<List<Integer> > allPathsOfABinaryTree(TreeNode root){
		if(root==null) {
			return new ArrayList<>();
		}
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		findPaths(root, result, new ListNode(root.val));
		return result;
	}
	
	private static void findPaths(TreeNode node, List<List<Integer>> result, ListNode workList) {
		if(node.left_ptr==null && node.right_ptr==null) {
			result.add(createList(workList));
			return;
		}
		
		findPath(node.left_ptr, result, workList);
		findPath(node.right_ptr, result, workList);
	}

	private static void findPath(TreeNode node, List<List<Integer>> result, ListNode workList) {
		if(node!=null) {
			findPaths(node, result, workList.insert(new ListNode(node.val)));
		}
	}

	private static List<Integer> createList(ListNode workList) {
		List<Integer> list = new ArrayList<>();
		while(workList!=null) {
			list.add(0, workList.val);
			workList = workList.next;
		};
		return list;
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

	private static class ListNode {
		public final int val;
		private ListNode next;
		public ListNode(int val) {
			this.val = val;
		}
		
		public ListNode insert(ListNode before) {
			before.next = this;
			return before;
		}
	}
}
