public class BinaryTree<K extends Comparable<K>, V> {
	protected class Node{
		protected Node left, right;
		protected K k;
		protected V v;
		protected Integer N;
		public Node(K k, V v, Integer n){this.k = k; this.v = v; this.N = n;}
	}
	
	public Integer size(Node n){
		if(null == n) return 0;
		return n.N;
	}
	
	protected Node root;
	private Node put(Node node, K k, V v){
		if(null == node) return new Node(k, v, 1);
		if(k.compareTo(node.k) < 0)	node.left = put(node.left, k, v);
		else if(k.compareTo(node.k) > 0)	node.right = put(node.right, k, v);
		else	node.v = v;
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
	public void put(K k, V v){
		root = put(root, k, v);
	}
	
	private V get(Node node, K k){
		if(null == node) return null;
		if(k.compareTo(node.k) < 0) return get(node.left, k);
		else if (k.compareTo(node.k) > 0) return get(node.right, k);
		else return	node.v;
	}
	public V get(K k){
		return get(root, k);
	}
	
	public void deleteMin(){
		root = deleteMin(root);
	}
	
	private Node deleteMin(Node node){
		if(null == node.left) return node.right; 
		node.left = deleteMin(node.left);
		node.N = size(node.left) + size(node.right) - 1;
		return node;
	}
	
	public Node min(Node n){
		if(n.left == null) return n;
		return min(n.left);
	}
	
	private Node delete(Node n, K k){
		if(n == null) return null;
		int cmp = k.compareTo(n.k);
		if(cmp < 0)	n.left = delete(n.left, k);//delete node is at left side of n
		else if(cmp > 0)	n.right = delete(n.right, k);
		else{	//Current node is the node to be deleted
			if(n.right == null)	return n.left;
			if(n.left == null)		return n.right;
			Node temp = min(n.right);	//temp will replace n to be the parent node.
			temp.left = n.left;
			temp.right = deleteMin(n.right);
			n = temp;
		}
		return n;
	}
	public void delete(K k){
		root = delete(root, k);
	}
	
	
	/**
	 * @Description: 前序遍历，访问当前节点，继而访问左结点，最后访问右结点
	 * @param n
	 */
	private void preOrderTraversal(Node n){
		if(n != null)		System.out.println(n.v);
		if(n.left != null)	preOrderTraversal(n.left);
		if(n.right != null) preOrderTraversal(n.right);
	}
	
	public void preOrderTraversal(){
		preOrderTraversal(root);
	}
	
	/**
	 * @Description: 中序遍历，先访问左结点，后访问当前节点，最后访问右结点。
	 * @param n
	 */
	private void inOrderTraversal(Node n){
		if(n != null && n.left != null) inOrderTraversal(n.left);
		if(null != n) System.out.println(n.v);
		if(n != null && n.right != null) inOrderTraversal(n.right);
	}
	
	public void inOrderTraversal(){
		inOrderTraversal(root);
	}
	
	/**
	 * @Description: 右序遍历，先访问左结点，后访问右结点，最后访问当前节点
	 * @param n
	 */
	private void postOrderTraversal(Node n){
		if(n != null && n.left != null) postOrderTraversal(n.left);
		if(n != null && n.right != null) postOrderTraversal(n.right);
		if(n != null) System.out.println(n.v);
	}
	
	public void postOrderTraversal(){
		postOrderTraversal(root);
	}
	
	public static void main(String[] args) {
		BinaryTree<Integer, Integer> binaryTree = new BinaryTree<>();
		binaryTree.put(11, 11);
		binaryTree.put(8, 8);
		binaryTree.put(16, 16);
		binaryTree.put(4, 4);
		binaryTree.put(9, 9);
		binaryTree.put(14, 14);
		binaryTree.put(18, 18);
		binaryTree.preOrderTraversal();
		System.out.println("=====================");
		binaryTree.inOrderTraversal();
		System.out.println("=====================");
		binaryTree.postOrderTraversal();
	}
}
