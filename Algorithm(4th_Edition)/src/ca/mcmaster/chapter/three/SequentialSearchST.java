package ca.mcmaster.chapter.three;

public class SequentialSearchST<K, V> {
	private class Node{
		K k;
		V v;
		Node next;
		public Node(K k, V v, Node next){
			this.k = k;
			this.v = v;
			this.next = next;
		}
	}
	private Node first;
	public V get(K k){
		Node current = first;
		while(current != null){
			if(current.k.equals(k))	return current.v;
			current = current.next;
		}
		return null;
	}
	
	public void put(K k, V v){
		Node current = first;
		while(current != null){
			if(current.k.equals(k)){
				current.v = v;
				return;
			}
			current = current.next;
		}
		first = new Node(k, v, first);
	}
	
	public static void main(String[] args) {
		SequentialSearchST<String, String> table = new SequentialSearchST<>();
		table.put("a", "aaaa");
		table.put("b", "bbbb");
		table.put("c", "cccc");
		table.put("d", "dddd");
		System.out.println(table.get("a"));
		System.out.println(table.get("b"));
		System.out.println(table.get("c"));
		System.out.println(table.get("d"));
		table.put("a", "1111");
		System.out.println(table.get("a"));
		System.out.println(table.get("p"));
	}
}
