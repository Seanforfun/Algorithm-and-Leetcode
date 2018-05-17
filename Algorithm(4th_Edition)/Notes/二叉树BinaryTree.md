# 二叉树 Binary Tree

## 二叉树的优势
>在实际使用时会根据链表和有序数组等数据结构的不同优势进行选择。有序数组的优势在于二分查找，链表的优势在于数据项的插入和数据项的删除。但是在有序数组中插入数据就会很慢，同样在链表中查找数据项效率就很低。综合以上情况，二叉树可以利用链表和有序数组的优势，同时可以合并有序数组和链表的优势，二叉树也是一种常用的数据结构。

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