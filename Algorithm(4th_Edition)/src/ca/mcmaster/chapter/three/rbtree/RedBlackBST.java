package ca.mcmaster.chapter.three.rbtree;

public class RedBlackBST<K extends Comparable<K>, V> {
	private Node root;
	public static final Boolean RED = true;
	public static final Boolean BLACK = false;
	private class Node{
		K k;
		V v;
		Node left, right;
		int N;
		Boolean color;
		public Node(K k, V v, int n, Boolean color) {
			this.k = k;
			this.v = v;
			N = n;
			this.color = color;
		}
	}
	
	private Boolean isRed(Node node){
		if(null == node) 		return false;
		return 						node.color == RED;
	}
	
	public Node rotateLeft(Node node){
		Node temp = node.right;
		node.right = temp.left;
		temp.left = node;
		temp.color = node.color;
		node.color = RED;
		temp.N = node.N;
		node.N = 1 + size(node.left) + size(node.right);
		return temp;
	}
	public Node rotateRight(Node node){
		Node temp = node.left;
		node.left = temp.right;
		temp.right = node;
		temp.color = node.color;
		temp.N = size(node);
		node.N = 1 + size(node.left) + size(node.right);
		return temp;
	}
	public int size()	{	return size(root);	}
	public int size(Node node){
		if(null == node)		return 0;
		return 						node.N;
	}
	public void filpColor(Node node){
		node.color = RED;
		node.left.color = BLACK;
		node.right.color = BLACK;
	}
	public void put(K k, V v){
		root = put(root, k, v);
		root.color = BLACK;
	}
	public Node put(Node node, K k, V v){
		if(node == null){		return new Node(k, v, 1, RED)	;	}
		int cmp = k.compareTo(node.k);
		if(cmp < 0)				return put(node.left, k, v);
		else if(cmp > 0)		return put(node.right, k, v);
		else							node.v = v;
		if(isRed(node.right) && !isRed(node.left))		node = rotateLeft(node);
		if(isRed(node.left) && isRed(node.left.left))	node = rotateRight(node);
		if(isRed(node.left) && isRed(node.left.left))	filpColor(node);
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
	private Node moveRedLeft(Node node){
		
	}
}
