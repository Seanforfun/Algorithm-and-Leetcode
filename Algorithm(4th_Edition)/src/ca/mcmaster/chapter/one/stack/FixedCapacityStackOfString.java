package ca.mcmaster.chapter.one.stack;

public class FixedCapacityStackOfString<T> implements MyStack<T> {
	private T[] a;
	private int size;
	@SuppressWarnings("unchecked")
	public FixedCapacityStackOfString(int capacity){ a = (T[])new Object[capacity]; }
	public void push(T t) {
		if(size == a.length)
			this.resize(a.length * 2);
		a[size++]  = t;
	}
	public T pop() {
		if(size > 0 && size < a.length/4 )	this.resize(a.length / 2);
		return (T)a[--size];
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
	
	public static void main(String[] args){
		FixedCapacityStackOfString<String> stack = new FixedCapacityStackOfString<String>(100);
		stack.resize(120);
		for(int i = 0; i < 10; i ++)
			stack.push("" + i);
		System.out.println(stack.isEmpty());
		int size = stack.size();
		for(int i = 0; i < size; i++){
			System.out.println(stack.pop());
		}
		System.out.println(stack.isEmpty());
	}
}
