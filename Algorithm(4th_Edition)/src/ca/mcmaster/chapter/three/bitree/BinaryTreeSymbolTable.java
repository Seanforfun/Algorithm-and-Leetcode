package ca.mcmaster.chapter.three.bitree;

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
	
	public static void main(String[] args) {
		BinaryTreeSymbolTableAbstract<Integer, String> table = new BinaryTreeSymbolTable<>();
		table.put(1, "a");
		table.put(2, "b");
		table.put(3, "c");
		table.put(8, "d");
		table.put(5, "e");
		table.put(6, "f");
		table.put(7, "g");
//		for (int i = 0; i < 7; i++) {
//			System.out.println(table.get(8));
//		}
		System.out.println(table.select(1));
	}
}
