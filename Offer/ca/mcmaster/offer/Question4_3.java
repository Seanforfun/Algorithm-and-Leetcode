package ca.mcmaster.offer;

import ca.mcmaster.offer.OfferTree.TreeNode;

public class Question4_3 {
	public static void main(String[] args) {
		Integer[] arr = new Integer[100];
		for(int i = 0; i < 100; i++)
			arr[i] = i;
		TreeNode root = OfferTree.arrToTree(arr, 0, arr.length - 1);
		System.out.println(root.v);
		System.out.println(root.left.v);
		System.out.println(root.right.v);
		System.out.println(root.left.left.v);
	}
}
