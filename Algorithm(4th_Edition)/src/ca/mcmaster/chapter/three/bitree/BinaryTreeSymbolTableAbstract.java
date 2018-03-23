package ca.mcmaster.chapter.three.bitree;

public abstract class BinaryTreeSymbolTableAbstract<K extends Comparable<K>, V> {
	protected class Node{
		protected K k;
		protected V v;
		protected Node left, right;
		protected int N;
		public Node(K k, V v, int n) {
			this.k = k;	this.v = v;	N = n;
		}
	}
	protected Node root;
	public int size(Node x){
		if(x == null) return 0;
		else				return x.N;
	}
	public int size(){	return size(root);	}
	public abstract V get(K k);
	public abstract void put(K k, V v);
	public K min(){	return min(root).k;	}
	public Node min(Node node){
		if(node.left == null)	return node;
		return min(node.left);
	}
	public K max(){	return max(root).k;	}
	public Node max(Node node){
		if(node.right == null)		return node;
		return max(node.right);
	}
	public K floor(K k){								//返回小于等于当前键值的最大值
		Node node = floor(root, k);
		if(node == null) return null;
		return node.k;
	}
	public Node floor(Node node, K k){
		if(null == node) return null;
		int cmp = k.compareTo(node.k);
		if(cmp == 0) 	return node;
		else if(cmp < 0)		return floor(node.left, k);
		Node temp =  floor(node.right, k);
		if(temp != null) return temp;
		else						return node;
	}
	public K select(int k){
		return select(root, k).k;
	}
	public Node select(Node node, int k){
		if(node == null)		return null;
		int t = size(node.left);
		if(t > k)			return select(node.left, k);
		else if(t < k)	return select(node.right, k - t - 1);
		else					return node;
	}
	public Integer rank(K k){	return rank(root, k);	}
	public Integer rank(Node node, K k){
		if(node == null)	return 0;
		int cmp = k.compareTo(node.k);
		if(cmp < 0)				return rank(node.left, k);
		else if(cmp > 0) 		return 1 + size(node.left) + rank(node.right, k);
		else							return size(node.left) + 1;
	}
	public void delMin(){	root = delMin(root);	}
	public Node delMin(Node node){
		if(node.left == null)	return node.right;		//当前节点就是最小的
		node.left = delMin(node.left);
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
	public void delete(K k){	root = delete(root,k);	}
	public Node delete(Node node, K k){
		if(node == null)		return null;
		int cmp = k.compareTo(node.k);
		if(cmp > 0)				node.right = delete(node.right, k);
		else if(cmp < 0)		node.left =  delete(node.left, k);
		else{
			if(node.right == null)		return node.left;
			if(node.left == null)		return node.right;
			Node temp = node;
			node = min(node.right);
			node.left = temp.left;
			node.right = delMin(temp.right);
		}
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
}
