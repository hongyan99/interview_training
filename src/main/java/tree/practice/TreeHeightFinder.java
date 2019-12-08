package tree.practice;

import java.util.Vector;

public class TreeHeightFinder {
	public static void main(String[] args) {
		TreeNode root = new TreeNode();
		System.out.println(findHeight(root));
		TreeNode node = new TreeNode();
		node.children.add(new TreeNode());
		root.children.add(node);
		System.out.println(findHeight(root));
	}
	
	private static int findHeight(TreeNode root) {
		if(root==null) {
			return 0;
		}
		int height = 0;
		for(int i = 0; i < root.children.size(); i++) {
			height = Math.max(height, findHeight(root.children.get(i))+1);
		}
        return height;
    }
	
	private static class TreeNode {
        Vector<TreeNode> children = new Vector<>();
    }
}
