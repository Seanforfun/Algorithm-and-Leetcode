package ca.mcmaster.offer;

import ca.mcmaster.offer.OfferTree.TreeNode;

public class Question4_8 {
	public static void main(String[] args) {
		TreeNode node50 = new TreeNode(50);
		TreeNode node40 = new TreeNode(40);
		TreeNode node30 = new TreeNode(30);
		TreeNode node45 = new TreeNode(45);
		TreeNode node44 = new TreeNode(44);
		TreeNode node46 = new TreeNode(46);
		TreeNode node60 = new TreeNode(60);
		TreeNode node55 = new TreeNode(55);
		TreeNode node65 = new TreeNode(65);
		node50.addLeftChild(node40);
		node40.addLeftChild(node30);
		node40.addRightChild(node45);
		node45.addLeftChild(node44);
		node45.addRightChild(node46);
		node50.addRightChild(node60);
		node60.addLeftChild(node55);
		node60.addRightChild(node65);
		System.out.println(OfferTree.isSubTree(node50, node60));
		System.out.println(OfferTree.matchTree(node50, node50));
		System.out.println(OfferTree.checkSubtree(node40, node45));
	}
}
