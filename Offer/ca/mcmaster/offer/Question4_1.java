package ca.mcmaster.offer;

import ca.mcmaster.offer.OfferTree.TreeNode;

public class Question4_1 {
	private static int getHeight(OfferTree.TreeNode root){
		if(null == root) return 0;
		return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
	}
	public static boolean isBalance(OfferTree.TreeNode root){
		if(null == root)	return true;
		int diff = Math.abs(getHeight(root.left) - getHeight(root.right));
		if(diff > 1)	return false;
		else
			return isBalance(root.left) && isBalance(root.right);
	}
	private static int checkHeight(OfferTree.TreeNode root){
		if(null == root) return 0;
		int leftHeight = checkHeight(root.left);
		int rightHeight = checkHeight(root.right);
		if(leftHeight == -1 || rightHeight == -1)	return -1;
		return Math.abs(leftHeight - rightHeight) > 1 ? -1:Math.max(leftHeight, rightHeight) + 1;
	}
	private static boolean isBalance1(OfferTree.TreeNode root){
		if(checkHeight(root) != -1)	return true;
		return false;
	}
	public static void main(String[] args) {
		OfferTree tree = new OfferTree();
//		OfferTree.TreeNode node = tree.new TreeNode(8, null, null);
//		OfferTree.TreeNode node0 = tree.new TreeNode(7, node, null);
		OfferTree.TreeNode node1 = new TreeNode(3, null, null);
		OfferTree.TreeNode node2 = new TreeNode(4, null, null);
		OfferTree.TreeNode node3 = new TreeNode(5, null, null);
		OfferTree.TreeNode node4 = new TreeNode(6, null, null);
		OfferTree.TreeNode node5 = new TreeNode(1, node1, node2);
		OfferTree.TreeNode node6 = new TreeNode(2, node3, node4);
		OfferTree.TreeNode node7 = new TreeNode(0, node5, node6);
		System.out.println(isBalance1(node7));
	}
}
