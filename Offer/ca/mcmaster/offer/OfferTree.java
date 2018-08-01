package ca.mcmaster.offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sun.reflect.generics.tree.Tree;
import ca.mcmaster.chapter.four.graph.Path;
import ca.mcmaster.offer.OfferTree.TreeNode;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
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
	public static boolean hasChild(TreeNode root, TreeNode node){
		if(root == null) return false;
		if(root == node) return true;
		return hasChild(root.left, node) || hasChild(root.right, node);
	}
	private static TreeNode closestSameNode(TreeNode root, TreeNode p, TreeNode q){
		if(root == null) return null;
		boolean findp = hasChild(root, p);
		boolean findq = hasChild(root, q);
		if(findp && findq){
			TreeNode resultLeft = closestSameNode(root.left, p, q);
			 TreeNode resultRight = closestSameNode(root.right, p, q);
			 if(resultLeft == null && resultRight == null)
				 return root;
			 else if (resultLeft == null && resultRight != null)
				return resultRight;
			else{
				return resultLeft;
			}
		}else {
			return null;
		}
	}
	public static TreeNode commonAncester(TreeNode root, TreeNode p, TreeNode q){
		if(!hasChild(root, p) && hasChild(root, q)) return null;
		return closestSameNode(root, p, q);
	}
	public static void preOrder(TreeNode node, StringBuilder sb){
		if(node == null) return;
		sb.append(node.v);
		preOrder(node.left, sb);
		preOrder(node.right, sb);
	}
	public static boolean isSubTree(TreeNode root, TreeNode node){
		StringBuilder sbRoot = new StringBuilder();
		preOrder(root, sbRoot);
		StringBuilder sbNode = new StringBuilder();
		preOrder(node, sbNode);
		return sbRoot.toString().contains(sbNode.toString());
	}
	public static boolean matchTree(TreeNode node1, TreeNode node2){
		boolean result = true;
		if(node1 == null && node2 == null)	return true;
		if(node1 != node2)
			result = false;
		return result && matchTree(node1.left, node2.left) && 
				matchTree(node1.right, node2.right);
	}
	public static boolean checkSubtree(TreeNode root, TreeNode node){
		if(root == null || node == null) return false;
		boolean mid = matchTree(root, node);
		boolean left = checkSubtree(root.left, node);
		boolean right = checkSubtree(root.right, node);
		return mid || left || right;
	}
	public static void getPathList(TreeNode root, LinkedList<PathResult> list, PathResult ps) throws CloneNotSupportedException{
		if(null == root)	return;
		PathResult currentResult = (PathResult) ps.clone();
		currentResult.path.add(root);
		currentResult.count += root.v;
		list.add(currentResult);
		getPathList(root.left, list, currentResult);
		getPathList(root.right, list, currentResult);
	}
	public static void createPathTable(TreeNode root, List<LinkedList<PathResult>> lists) throws CloneNotSupportedException{
		if(root == null) return;
		LinkedList<PathResult> list = new LinkedList<>();
		getPathList(root, list, new PathResult());
		if(list.size() != 0)
			lists.add(list);
		createPathTable(root.left, lists);
		createPathTable(root.right, lists);
	}
	public static List<PathResult> getPathWithCount(TreeNode root, int count) throws CloneNotSupportedException{
		List<PathResult> results = new ArrayList<>();
		List<LinkedList<PathResult>> lists = new ArrayList<>();
		createPathTable(root, lists);
		for(LinkedList<PathResult> list : lists){
			for(PathResult pr : list){
				if(pr.count == count)
					results.add(pr);
			}
		}
		return results;
	}
}
class PathResult implements Cloneable{
	public LinkedList<TreeNode> path = new LinkedList<>();
	public int count = 0;
	@Override
	protected Object clone() throws CloneNotSupportedException {
		PathResult newPr = new PathResult();
		for(TreeNode node : this.path)
			newPr.path.add(node);
		newPr.count = this.count;
		return newPr;
	}
}
