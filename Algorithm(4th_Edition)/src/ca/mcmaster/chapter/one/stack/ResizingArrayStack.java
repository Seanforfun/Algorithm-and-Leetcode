package ca.mcmaster.chapter.one.stack;

import java.util.Iterator;

public class ResizingArrayStack<T> implements MyStack<T>, Iterable<T> {
	private T[] a;
	private int size;
	@SuppressWarnings("unchecked")
	public ResizingArrayStack(int capacity){ a = (T[])new Object[capacity]; }
	public void push(T t) {
		if(size == a.length)
			this.resize(a.length * 2);
		a[size++]  = t;
	}
	public T pop() {
		if(size > 0 && size < a.length/4 )	this.resize(a.length / 2);
		T ret = (T)a[--size];
		a[size] = null;
		return ret;
	}
	public Boolean isEmpty() { return size == 0;}
	public Integer size() {	return size;}
	@SuppressWarnings("unchecked")
	public void resize(int capacity){
		if(capacity < size) return;
		T[] temp = (T[])new Object[capacity];
		for(int i = 0; i < size; i++)
			temp[i] = a[i];
		a = temp;
	}
	
	private class ReverseArrayIterator implements Iterator<T>{
		public boolean hasNext() { return size > 0; }
		public T next() { return a[--size]; }
		public void remove() {}
	}
	
	public ReverseArrayIterator iterator() {
		return new ReverseArrayIterator();
	}
	
	public static void main(String[] args){
		ResizingArrayStack<String> stack = new ResizingArrayStack<String>(100);
		stack.resize(120);
		for(int i = 0; i < 10; i ++)
			stack.push("" + i);
		System.out.println(stack.isEmpty());
//		int size = stack.size();
//		for(int i = 0; i < size; i++){
//			System.out.println(stack.pop());
//		}
		Iterator<String> it = stack.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		System.out.println(stack.isEmpty());
	}
}
