package ca.mcmaster.chapter.three.bitree;

public class BalancedBinaryTree<V extends Comparable<V>> {
	private AVLNode root;
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
		if(n == null)		return -1;
		return n.height;
	}
	
	/**
	 * @Description: LL插入造成不平衡，通过单次向右旋转重新平衡树。
	 * @param n
	 * @return
	 */
	private AVLNode singleRotateRight(AVLNode n){
		AVLNode left1 = n.left;
		n.left = left1.right;
		left1.right = n;
		n.height = Math.max(height(n.left), height(n.right)) + 1;
		left1.height = Math.max(height(left1.left), n.height) + 1;
		return left1;
	}
	
	/**
	 * @Description: RR插入造成不平衡，通过单次向左旋转重新平衡树。
	 * @param n
	 * @return
	 */
	private AVLNode singleRotateLeft(AVLNode n){
		AVLNode right1 = n.right;
		n.right = right1.left;
		right1.left = n;
		n.height = Math.max(height(n.left), height(n.right)) + 1;
		right1.height = Math.max(n.height, height(right1.right)) + 1;
		return right1;
	}
	
	/**
	 * @Description: LR造成的不平衡，需要两次旋转。
	 * @param n
	 * @return
	 */
	private AVLNode doubleRotateLR(AVLNode n){
		n.left = singleRotateLeft(n.left);
		return singleRotateRight(n);
	}
	
	/**
	 * @Description: RL造成的不平衡，两次旋转。
	 * @param n
	 * @return
	 */
	private AVLNode doubleRotateRL(AVLNode n){
		n.right = singleRotateRight(n.right);
		return singleRotateLeft(n);
	}
	
	public void insert(V v){
		if(null == v){
			throw new RuntimeException("Cannot insert null to AVLTree");
		}
		root = insert(v, root);
	}
	
	/**
	 * @Description: 向AVLTree中加入元素。
	 * @param v
	 */
	private AVLNode insert(V v, AVLNode node){
		//当前节点已经为空，新建结点。
		if(null == node){
			node = new AVLNode(v);
		}else if (v.compareTo(node.v) < 0) {
			//L
			node.left = insert(v, node.left);
			//如果造成了不平衡
			if(height(node.left) - height(node.right) == 2){
				//判断是LL还是LR
				if(v.compareTo(node.left.v) < 0){
					//LL
					node = singleRotateRight(node);
				}else{
					//LR
					node = doubleRotateLR(node);
				}
			}
		}else if(v.compareTo(node.v) > 0){
			//R
			node.right = insert(v, node.right);
			//判断是否需要旋转
			if(height(node.right) - height(node.left) == 2){
				//判断LR/RR
				if(v.compareTo(node.right.v) > 0){
					//RR
					node = singleRotateLeft(node);
				}else{
					//RL
					node = doubleRotateRL(node);
				}
			}
		}else{
			//如果要插入的数据和当前遍历到数据一致，do nothing
			;
		}
		
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return node;
	}
	
	public static void main(String[] args) {
		BalancedBinaryTree<Integer> avlTree = new BalancedBinaryTree<>();
		avlTree.insert(5);
		System.out.println("insert :" + 5);
		System.out.println(avlTree.root.v);
		System.out.println(avlTree.height(avlTree.root));
		System.out.println("-------------------------------------");
		avlTree.insert(3);
		System.out.println("insert :" + 3);
		System.out.println(avlTree.root.v);
		System.out.println(avlTree.height(avlTree.root));
		System.out.println("-------------------------------------");
		avlTree.insert(4);
		System.out.println("insert :" + 4);
		System.out.println(avlTree.root.v);
		System.out.println(avlTree.height(avlTree.root));
		System.out.println("-------------------------------------");
		avlTree.insert(6);
		System.out.println("insert :" + 6);
		System.out.println(avlTree.root.v);
		System.out.println(avlTree.height(avlTree.root));
		System.out.println("-------------------------------------");
		avlTree.insert(7);
		System.out.println("insert :" + 7);
		System.out.println(avlTree.root.v);
		System.out.println(avlTree.height(avlTree.root));
		System.out.println("-------------------------------------");
	}
}
