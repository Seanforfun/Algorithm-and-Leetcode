package ca.mcmaster.chapter.one.stack;

import java.util.Iterator;

public class ListStack<T> implements MyStack<T>{
	private Node first;
	private Integer size = 0;
	private class Node{
		T t;
		Node next;
	}
	public void push(T t) {
		Node oldFirst  = first;
		first = new Node();
		first.t = t;
		first.next = oldFirst;
		size++;
	}
	public T pop() {
		if(size.equals(0)) throw new IndexOutOfBoundsException();
		T t = first.t;
		first = first.next; 
		size--;
		return t;
	}
	public Boolean isEmpty() { return size.equals(0); };
	public Integer size() { return size; }
	
	public static void main(String[] args){
		ListStack<Integer> stack = new ListStack<>();
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		Integer size = stack.size();
		for (int i = 0; i < size; i++) {
			System.out.println(stack.pop());
		}
		System.out.println(stack.isEmpty());
	}
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private Node current = first;
			@Override
			public boolean hasNext() { return current != null;	}
			@Override
			public T next() {
				if(!hasNext()){ return null; }
				else{
					Node res = current;
					current = current.next;
					return res.t;
				}
			}
		};
	}
}
