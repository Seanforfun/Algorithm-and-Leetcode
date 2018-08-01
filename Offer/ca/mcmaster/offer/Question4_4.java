package ca.mcmaster.offer;

import java.util.LinkedList;
import java.util.List;

import ca.mcmaster.offer.OfferTree.TreeNode;

public class Question4_4 {
	public static void main(String[] args) {
		Integer[] arr = new Integer[100];
		for(int i = 0; i < 100; i++)
			arr[i] = i;
		TreeNode root = OfferTree.arrToTree(arr, 0, arr.length - 1);
		List<LinkedList<TreeNode>> lists = OfferTree.createLevelList(root);
		for(LinkedList<TreeNode> list : lists){
			for(TreeNode n : list){
				System.out.print(n.v + "	");
			}
			System.out.println();
		}
	}
}
