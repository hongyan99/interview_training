package tree;

public class BinaryTreeToDoublyLinked {
	public static void main(String[] args) {
//		TreeNode[] nodes = new TreeNode[20];
//		for(int i = 0; i<20; i++) {
//			nodes[i] = new TreeNode(i+1);
//			if(i>0) {
//				nodes[i-1].left_ptr = nodes[i];
//			}
//		}
//		print(btToDbleList(nodes[0]));
		TreeNode root = new TreeNode(4);
		root.left_ptr = new TreeNode(2);
		root.right_ptr = new TreeNode(5);
		root.left_ptr.left_ptr = new TreeNode(1);
		root.left_ptr.right_ptr = new TreeNode(3);
		print(btToDbleList(root));
	}
	
	private static void print(TreeNode root) {
		TreeNode node = root;
		do {
			System.out.println(node.val);
			node = node.right_ptr;
		} while (node!=root);
	}

	private static TreeNode btToDbleList(TreeNode root) {
		if(root==null) {
			return null;
		}
		TreeNode[] bounds = convert(root);
		return bounds[0];
	}
	
	private static TreeNode[] convert(TreeNode node) {
		TreeNode copy = new TreeNode(node.val);
		TreeNode[] bounds = new TreeNode[2];
		if(node.left_ptr==null && node.right_ptr==null) {
			bounds[0] = copy;
			bounds[1] = copy;
			return packageBounds(bounds);
		}

		TreeNode left = copy;
		if(node.left_ptr!=null) {
			TreeNode[] leftBounds = convert(node.left_ptr);
			link(leftBounds[1], copy);
			if(leftBounds[0]!=null) {
				left = leftBounds[0];
			}
		}
		
		TreeNode right = copy;
		if(node.right_ptr!=null) {
			TreeNode[] rightBounds = convert(node.right_ptr);
			link(copy, rightBounds[0]);
			if(rightBounds[1]!=null) {
				right = rightBounds[1];
			}
		}
		return packageBounds(left, right);
	}
	
	private static TreeNode[] packageBounds(TreeNode... bounds) {
		bounds[1].right_ptr = bounds[0];
		bounds[0].left_ptr = bounds[1];
		return bounds;
	}

	private static void link(TreeNode left, TreeNode right) {
		left.right_ptr = right;
		right.left_ptr = left;
	}

	private static class TreeNode {
        public int val;
        public TreeNode(int val) {
        	this.val = val;
        }
        public TreeNode left_ptr;
        public TreeNode right_ptr;
    }
}
