package ca.mcmaster.chapter.one.FIFO;

public interface FifoQueue<T> extends Iterable<T> {
	void enqueue(T t);
	T dequeue();
	Boolean isEmpty();
	Integer size();
}
