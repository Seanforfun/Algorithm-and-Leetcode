package ca.mcmaster.chapter.one.bag;

import java.util.Iterator;

public class ListBag<T> implements Bag<T>{
	private Node first;
	private Integer size = 0;
	private class Node{
		T t;
		Node next;
	}
	public void add(T t) {
		Node oldFirst  = first;
		first = new Node();
		first.t = t;
		first.next = oldFirst;
		size++;
	}
	public Boolean isEmpty() { return size.equals(0); };
	public Integer size() { return size; }
	public Iterator<T> iterator() {
		return new ListIterator();
	}
	private class ListIterator implements Iterator<T>{
		private Node current = first;
		public boolean hasNext() { return current != null; }
		public T next() {
			if(!hasNext()) throw new IndexOutOfBoundsException();
			T t = current.t;
			current = current.next;
			return t;
		}
		public void remove() {}
	}
	
	public static void main(String[] args) {
		ListBag<Integer> bag = new ListBag<>();
		for(int i = 0; i < 10; i++){
			bag.add(i);
		}
		Iterator<Integer> it = bag.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
