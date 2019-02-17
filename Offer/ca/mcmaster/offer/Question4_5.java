package ca.mcmaster.offer;

import ca.mcmaster.offer.OfferTree.TreeNode;

public class Question4_5 {
	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		TreeNode node1 = new TreeNode(3);
		TreeNode node2 = new TreeNode(1);
		TreeNode node3 = new TreeNode(10);
		TreeNode node4 = new TreeNode(7);
		TreeNode node5 = new TreeNode(12);
		TreeNode node6 = new TreeNode(11);
		root.left = node1;
		root.right = node3;
		node1.left = node2;
		node3.left = node4;
		node3.right = node5;
//		node5.right = node6;
		System.out.println(OfferTree.checkBSTDFS(root));
		System.out.println(OfferTree.checkBSTBFS(root));
	}
}
