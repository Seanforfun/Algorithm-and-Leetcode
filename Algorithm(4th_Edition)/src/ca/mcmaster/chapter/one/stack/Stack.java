package ca.mcmaster.chapter.one.stack;

public interface Stack<T> extends Iterable<T> {
	void push(T t);
	T pop();
	Boolean isEmpty();
	Integer size();
}
