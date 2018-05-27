package ca.mcmaster.chapter.one.stack;

public interface MyStack<T> extends Iterable<T>{
	void push(T t);
	T pop();
	Boolean isEmpty();
	Integer size();
}
