package tree.practice;

public class BstKthSmallest {
	static class TreeNode
    {
        int val;
        TreeNode left_ptr;
        TreeNode right_ptr;

        TreeNode(int _val)
        {
            val = _val;
            left_ptr = null;
            right_ptr = null;
        }
    }
	
	public static void main(String[] args) {
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		TreeNode node3 = new TreeNode(3);
		node2.left_ptr = node1;
		node2.right_ptr = node3;
		System.out.println(kthSmallest(node2, 3));
		node1 = new TreeNode(1);
		node2 = new TreeNode(2);
		node3 = new TreeNode(3);
		node3.left_ptr = node2;
		node2.left_ptr = node1;
		System.out.println(kthSmallest(node3, 3));
	}
	
	private static int kthSmallest(TreeNode root, int k) {
		Holder holder = new Holder();
		kthSmallest(root, k, holder);
        return holder.node.val;
    }

	private static void kthSmallest(TreeNode node, int k, Holder holder) {
		if(node==null) {
			if(holder.order==Integer.MAX_VALUE) {
				holder.init();
			}
			return;
		}
		
		kthSmallest(node.left_ptr, k, holder);
		holder.inc();
		if(holder.order==k) {
			holder.node = node;
		}
		kthSmallest(node.right_ptr, k, holder);
	}
	
	private static class Holder {
		int order = Integer.MAX_VALUE;
		boolean flag;
		public void inc() {
			if(flag) {
				order++;
			}
		}
		
		public void init() {
			flag=true;
			order = 0;
		}
		
		public TreeNode node;
	}
}
