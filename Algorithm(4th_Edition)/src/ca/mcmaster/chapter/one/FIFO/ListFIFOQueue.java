package ca.mcmaster.chapter.one.FIFO;

import java.util.Iterator;

public class ListFIFOQueue<T> implements FifoQueue<T> {
	private Node first;
	private Node last;
	private Integer size = 0;
	private class Node{
		T t;
		Node next;
	}
	public void enqueue(T t) {
		Node oldLast = last;
		last = new Node();
		last.t = t;
		last.next = null;
		if(isEmpty()) first = last;
		else oldLast.next = last;
		size ++;
	}
	public T dequeue() {
		if(isEmpty())	throw new IndexOutOfBoundsException();
		T t = first.t;
		first = first.next;
		size--;
		if(size.equals(0))	last = null;
		return t;
	}
	public Boolean isEmpty() { return size.equals(0); }
	public Integer size() { return size; }
	public Iterator<T> iterator() {
		return new ListFIFOQueueIterator<T>();
	}
	
	private class ListFIFOQueueIterator<T> implements Iterator<T>{
		public boolean hasNext() {	return size > 0;	}
		public T next() {		return (T) dequeue();	}
		public void remove() {
		}
	}
	public static void main(String[] args){
		ListFIFOQueue<Integer> queue = new ListFIFOQueue<>();
		for (int i = 0; i < 10; i++) {
			queue.enqueue(i);
		}
		Integer size = queue.size();
		for (int i = 0; i < size; i++) {
			System.out.println(queue.dequeue());
		}
		System.out.println(queue.isEmpty());
	}
}
