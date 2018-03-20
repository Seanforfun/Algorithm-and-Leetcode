package ca.mcmaster.chapter.three;

import java.util.Iterator;

public class BinarySearchST<K extends Comparable<K>, V> extends SearchSTAbstract<K, V> {
	public BinarySearchST(int capacity) {	super(capacity);		}
	public int binarySearch(K k) {
		int lo = 0, hi = N - 1;
		while(lo <= hi){
			int mid = lo + (hi - lo) / 2;
			int cmp = keys[mid].compareTo(k);
			if(cmp < 0)				lo = mid + 1;
			else if(cmp > 0) 		hi = mid - 1;
			else return mid;
		}
		return lo;
	}
	public Integer traditionalBinarySearch(K k, int lo, int hi){
		if(lo > hi) return lo;
		int mid = lo + (hi - lo) / 2;
		int cmp = keys[mid].compareTo(k);
		if(cmp > 0) return traditionalBinarySearch(k, lo, mid - 1);
		else if(cmp < 0) return traditionalBinarySearch(k, mid + 1, hi);
		else return mid;
	}
	
	public static void main(String[] args) {
		BinarySearchST<String, String> table = new BinarySearchST<>(100);
		table.put("a", "a");
		table.put("c", "c");
		table.put("b", "b");
		table.put("d", "d");
		System.out.println(table.get("d"));
		table.delete("d");
		System.out.println(table.get("d"));
		Iterable<String> queue = table.keys(0, 2);
		Iterator<String> it = queue.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
