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
}
