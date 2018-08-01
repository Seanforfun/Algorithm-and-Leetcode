package ca.mcmaster.offer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sun.javafx.geom.AreaOp.AddOp;

import edu.princeton.cs.algs4.Queue;

public class OfferTree {
	public static class TreeNode{
		public int v;
		public TreeNode left, right, parent;
		public TreeNode(int v, TreeNode left, TreeNode right){
			this.v = v;
			this.left = left;
			this.right = right;
		}
		public TreeNode(int v){
			this.v = v;
		}
		public void addLeftChild(TreeNode child){
			this.left = child;
			child.parent = this;
		}
		public void addRightChild(TreeNode child){
			this.right = child;
			child.parent = this;
		}
	}
	public static TreeNode arrToTree(Integer[] arr, int low, int high){
		if(low > high) return null;
		int mid = (high - low) / 2 + low;
		TreeNode root = new TreeNode(arr[mid]);
		root.left = arrToTree(arr, low, mid - 1);
		root.right = arrToTree(arr, mid + 1, high);
		return root;
	}
	public static void createLevelList(TreeNode root, List<LinkedList<TreeNode>> list, int level){
		if(root == null) return;
		if(list.size() <= level)
			list.add(new LinkedList<>());
		List<TreeNode> levelList = list.get(level);
		levelList.add(root);
		createLevelList(root.left, list, level + 1);
		createLevelList(root.right, list, level + 1);
	}
	public static List<LinkedList<TreeNode>> createLevelList(TreeNode root){
		List<LinkedList<TreeNode>> lists = new ArrayList<>();
		LinkedList<TreeNode> rootLevel = new LinkedList<>();
		lists.add(rootLevel);
		rootLevel.add(root);
		Queue<LinkedList<TreeNode>> queue = new Queue<>();
		queue.enqueue(rootLevel);
		while(!queue.isEmpty()){
			LinkedList<TreeNode> list = queue.dequeue();
			LinkedList<TreeNode> newList = null;
			if(list.size() != 0){
				newList = new LinkedList<>();
				lists.add(newList);
				queue.enqueue(newList);
				for(TreeNode node : list){
					if(node.left != null)
						newList.add(node.left);
					if(node.right != null)
						newList.add(node.right);
				}
			}
		}
		return lists;
	}
	public static boolean checkBSTDFS(TreeNode root){
		if(root == null) return true;
		boolean lresult = true;
		boolean rresult = true;
		if(root.left != null){
			if(root.left.v > root.v)
				return false;
			lresult = checkBSTDFS(root.left);
		}
		if(root.right != null){
			if(root.right.v <= root.v)
				return false;
			rresult = checkBSTDFS(root.right);
		}
		return lresult && rresult;
	}
	public static boolean checkBSTBFS(TreeNode root){
		if(root == null) return true;
		Queue<TreeNode> queue = new Queue<>();
		queue.enqueue(root);
		while(!queue.isEmpty()){
			TreeNode node = queue.dequeue();
			if(node.left != null){
				if(node.left.v > node.v)	return false;
				queue.enqueue(node.left);
			}
			if(node.right != null){
				if(node.right.v <= node.v)	return false;
				queue.enqueue(node.right);
			}
		}
		return true;
	}
	private static TreeNode getMostLeft(TreeNode node){
		if(null == node) return null;
		if(node.left == null) return node;
		return getMostLeft(node.left);
	}
	public static TreeNode getNextBST(TreeNode node){
		if(node == null) return null;
		if(node.right == null){
			TreeNode current = node.parent;
			while(current != null && current.v < node.v){
				current = current.parent;
			}
			return current;
		}
		return getMostLeft(node.right);
	}
}
