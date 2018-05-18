package ca.mcmaster.chapter.three.bitree;

public class BalancedBinaryTree<V extends Comparable<V>> {
	private class AVLNode{
		V v;
		int height;	//当前节点的子树的高度
		AVLNode left, right;
		public AVLNode(V v, AVLNode left, AVLNode right, int height) {
			this.v = v;
			this.left = left;
			this.right = right;
			this.height = height;
		}
		public AVLNode(V v, AVLNode left, AVLNode right) {
			this(v, left, right, 0);
		}
		public AVLNode(V v) {
			this(v, null, null);
		}
	}
	private int height(AVLNode n){
		return n.height;
	}
	
	private AVLNode singleRotateLeft(AVLNode n){
		AVLNode left1 = n.left;
		n.left = left1.right;
		left1.right = n;
		left1.height = Math.max(left1.left.height, left1.right.height) + 1;
		n.height = Math.max(n.left.height, n.right.height) + 1;
		return left1;
	}
	
	private AVLNode singleRotateRight(AVLNode n){
		AVLNode right1 = n.right;
		n.left = right1.left;
		right1.left = n;
		right1.height = Math.max(right1.left.height, right1.right.height) + 1;
		n.height = Math.max(n.left.height, n.right.height) + 1;
		return right1;
	}
}
