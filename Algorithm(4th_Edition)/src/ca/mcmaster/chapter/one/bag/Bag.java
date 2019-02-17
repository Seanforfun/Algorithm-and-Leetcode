package ca.mcmaster.chapter.one.bag;

public interface Bag<T> extends Iterable<T> {
	void add(T t);
	Boolean isEmpty();
	Integer size();
}
