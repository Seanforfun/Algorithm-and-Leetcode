package ca.mcmaster.offer;

import java.util.Stack;

public class MyQueue<E> {
	private Stack<E> stack1;
	private Stack<E> stack2;
	public MyQueue() {
		stack1 = new Stack<>();
		stack2 = new Stack<>();
	}
	public void offer(E e){
		stack1.add(e);
	}
	public E poll(){
		while(!stack1.isEmpty()){
			stack2.push(stack1.pop());
		}
		E result = stack2.pop();
		while(!stack2.isEmpty()){
			stack1.push(stack2.pop());
		}
		return result;
	}
	public static void main(String[] args) {
		MyQueue<Integer> queue = new MyQueue<>();
		for(int i = 0; i < 10; i++)
			queue.offer(i);
		for (int i = 0; i < 10; i++)
			System.out.println(queue.poll());
	}
}
