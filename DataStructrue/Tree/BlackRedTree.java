package ca.mcmaster.chapter.three.bitree;

public class BlackRedTree<K extends Comparable<K>, V > {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private Node root;
	private class Node{
		K k;
		V v;
		int N;
		Node left, right;
		boolean color;
		public Node(K k, V v, int n, boolean color) {
			super();
			this.k = k;
			this.v = v;
			N = n;
			this.color = color;
		}
	}
	private Boolean isRed(Node n){
		if(null == n) return BLACK;
		return n.color;
	}
	private Integer size(Node x){
		if(null == x)	return 0;
		else return x.N;
	}
	
	
	/**
	 * @Description: 单次左旋
	 * @param n
	 * @return
	 */
	private Node rotateLeft(Node n){
		Node x = n.right;
		n.right = x.left;
		x.left = n;
		x.color = n.color;
		n.color = RED;
		x.N = n.N;
		n.N = size(n.left) + size(n.right) + 1;
		return x;
	}
	
	/**
	 * @Description: 单次右旋
	 * @param n
	 * @return
	 */
	private Node rotateRight(Node n){
		Node x = n.left;
		n.left = x.right;
		x.right = n;
		x.color = n.color;
		n.color = RED;
		x.N = n.N;
		n.N = size(n.left) + size(n.right) + 1;
		return x;
	}
	
	/**
	 * @Description: 当两个子链接的颜色均为红色时，将子链接的颜色改为黑色将父链接的颜色从黑色换成红色。
	 * @param n
	 */
	private void flipColor(Node n){
		n.color = RED;
		n.left.color = BLACK;
		n.right.color = BLACK;
	}
	
	public void insert(K k, V v){
		root = insert(root, k, v);
		root.color = BLACK;
	}
	private Node insert(Node h, K k, V v){
		if(null == h)	return new Node(k, v, 1, RED);
		int cmp = k.compareTo(h.k);
		if(cmp < 0)
			h.left = insert(h.left, k, v);
		else if(cmp > 0)
			h.right = insert(h.right, k, v);
		else
			h.v = v;
		if(isRed(h.right) && !isRed(h.left))		h = rotateLeft(h);
		if(isRed(h.left) && isRed(h.left.left))		h = rotateRight(h);
		if(isRed(h.left) && isRed(h.right))		flipColor(h);
		h.N = size(h.left) + size(h.right) + 1;
		return h;
	}
	
	public void inorderTraversal(Node node){
		if(null == node)	return;
		inorderTraversal(node.left);
		System.out.println(node.v);
		inorderTraversal(node.right);
	}
	
	public static void main(String[] args) {
		BlackRedTree<Integer, Integer> brTree = new BlackRedTree<Integer, Integer>();
		brTree.insert(1, 1);
		brTree.insert(2, 2);
		brTree.insert(3, 3);
		brTree.insert(4, 4);
		brTree.insert(5, 5);
		brTree.insert(6, 6);
//		System.out.println(brTree.root.v);
//		System.out.println(brTree.root.left.color);
//		System.out.println(brTree.root.left.v);
		brTree.inorderTraversal(brTree.root);
	}
}
