package tree.practice;

public class BalancedBst {
	public static void main(String[] args) {
		print(buildBalancedBst(new int[] {8, 10, 12, 15, 16, 20, 25}));
	}
	
	private static void print(TreeNode node) {
		if(node!=null) {
			print(node.left_ptr);
			System.out.println(node.val);
			print(node.right_ptr);
		}
	}

	private static TreeNode buildBalancedBst(int[] a) {
		return build(a, 0, a.length);
	}
	
	private static TreeNode build(int[] a, int start, int end) {
		if(end==start) {
			return null;
		}
		int middle = (start+end)/2;
		TreeNode node = new TreeNode(a[middle]);
		node.left_ptr = build(a, start, middle);
		node.right_ptr = build(a, middle+1, end);
		
		return node;
	}
	
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
    }}
