package ca.mcmaster.chapter.three.bitree;

import java.util.Iterator;

import ca.mcmaster.chapter.one.FIFO.ListFIFOQueue;

public class BinaryTreeSymbolTable<K extends Comparable<K>, V> extends
		BinaryTreeSymbolTableAbstract<K, V> {
	public V get(K k) {		return get(root, k);	}
	public V get(Node node, K k){
		if(node == null) return null;
		int cmp = k.compareTo(node.k);
		if(cmp > 0)	return get(node.right, k);
		else if(cmp < 0) return get(node.left, k);
		else 	return node.v;
	}
	public void put(K k, V v) {
		root = put(root, k, v);
	}
	public Node put(Node node, K k, V v){
		if(node == null)	return new Node(k, v, 1);
		int cmp = k.compareTo(node.k);
		if(cmp < 0)	node.left = put(node.left, k, v);
		else if(cmp > 0) node.right = put(node.right, k, v);
		else	node.v = v;
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
	
	public Iterable<K> keys(){	return keys(min(), max());	}
	public Iterable<K> keys(K lo, K hi){
		ListFIFOQueue<K> queue = new ListFIFOQueue<>();
		keys(root, queue, lo, hi);
		return queue;
	}
	public void keys(Node node, ListFIFOQueue<K> queue, K lo, K hi){
		if(null == node) 		return;
		int cmplo = lo.compareTo(node.k);
		int cmphi = hi.compareTo(node.k);
		if(cmplo < 0)	keys(node.left, queue, lo, hi);
		if(cmplo <= 0 && cmphi >= 0)		queue.enqueue(node.k);
		if(cmphi > 0)	keys(node.right, queue, lo, hi);
	}
	
	public static void main(String[] args) {
		BinaryTreeSymbolTable<Integer, String> table = new BinaryTreeSymbolTable<>();
		table.put(4, "a");
		table.put(3, "b");
		table.put(7, "c");
		table.put(2, "d");
		table.put(5, "e");
		table.put(6, "f");
		table.put(1, "g");
		table.delete(5);
		Iterable<Integer> keys = table.keys();
		Iterator<Integer> it = keys.iterator();
		while(it.hasNext()){
			System.out.println(table.get(it.next()));
		}
//		System.out.println(table.select(0));
	}
}
