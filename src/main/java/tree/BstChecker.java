package tree;

public class BstChecker {
	public static void main(String[] args) {
		TreeNode root = create(100);
		root.left_ptr = create(200);
		root.right_ptr = create(300);
		System.out.println(isBST(root));
		root = create(200);
		root.left_ptr = create(100);
		root.right_ptr = create(300);
		System.out.println(isBST(root));
		root = create(300);
		root.left_ptr = create(200);
		root.right_ptr = create(400);
		root.left_ptr.left_ptr = create(100);
		root.left_ptr.right_ptr = create(400);
		System.out.println(isBST(root));
	}
	
	private static boolean isBST(TreeNode root) {
		return isBST(root, null, null);
	}
	
	private static boolean isBST(TreeNode root, Integer leftBound, Integer rightBound) {
		if(root==null) {
			return true;
		}
		if(rightBound!=null && root.right_ptr !=null && root.right_ptr.val > rightBound) {
			return false;
		}
		if(leftBound!=null && root.left_ptr !=null && root.left_ptr.val < leftBound) {
			return false;
		}
		if(root.right_ptr!=null && root.right_ptr.val<root.val) {
			return false;
		}
		if(root.left_ptr!=null && root.left_ptr.val>root.val) {
			return false;
		}
		
		if(isBST(root.left_ptr, leftBound, root.val) && isBST(root.right_ptr, root.val, rightBound)) {
			return true;
		}
		return false;
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
