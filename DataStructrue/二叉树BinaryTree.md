# 二叉树 Binary Tree

## 二叉树的优势
>在实际使用时会根据链表和有序数组等数据结构的不同优势进行选择。有序数组的优势在于二分查找，链表的优势在于数据项的插入和数据项的删除。但是在有序数组中插入数据就会很慢，同样在链表中查找数据项效率就很低。综合以上情况，二叉树可以利用链表和有序数组的优势，同时可以合并有序数组和链表的优势，二叉树也是一种常用的数据结构。

## 二叉树的增删改查
* 构成树的基本元素是结点， 一个结点存储了两个指针left, right, 键（用于定位node的位置）， 值。
```Java
	protected class Node{
		protected K k;
		protected V v;
		protected Node left, right;
		protected int N;		//当前节点，以及其子结点的所有的节点的个数。
		public Node(K k, V v, int n) {
			this.k = k;	this.v = v;	N = n;
		}
	}
```
* 检查当前节点以及其子结点的个数。
```Java
	public Integer size(Node n){
		if(null == n) return 0;
		return n.N;
	}
```

* 向二叉树中插入元素, 通过递归向二叉树中插入元素。
```Java
	protected Node root;	//对一个二叉树类维护了一个root节点
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
```

* 从二叉树中获取元素
```Java
	private V get(Node node, K k){
		if(null == node) return null;
		if(k.compareTo(node.k) < 0) return get(node.left, k);
		else if (k.compareTo(node.k) > 0) return get(node.right, k);
		else return	node.v;
	}
	public V get(K k){
		return get(root, k);
	}
```

* 从二叉树中删除元素
1. 删除最小的元素
>一直遍历直到左结点为空结点，然后将指向该结点的链接指向该结点的右结点。
```Java
	public void deleteMin(){
		root = deleteMin(root);
	}
	
	private Node deleteMin(Node node){
		if(null == node.left) return node.right; 
		node.left = deleteMin(node.left);
		node.N = size(node.left) + size(node.right) - 1;
		return node;
	}
```
2.删除某个结点
```Java    
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
```

## 遍历树
>二叉树的遍历分三种情况，中序遍历，前序遍历以及后序遍历。中序遍历是一种常见的遍历方式。遍历过程采用递归实现，递归的终止条件是判断节点是否为null，分别对左子节点和右子节点进行遍历。

1. 前序遍历
>按照“根节点-左孩子-右孩子”的顺序进行访问。
```Java
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
```

2. 中序遍历
>按照“左孩子-根节点-右孩子”的顺序进行访问。
```Java
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
```

3. 右序遍历
> 按照“左孩子-右孩子-根节点”的顺序进行访问。
```Java
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
```













