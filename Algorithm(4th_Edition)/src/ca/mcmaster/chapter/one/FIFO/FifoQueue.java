package ca.mcmaster.chapter.one.FIFO;

public interface FifoQueue<T>{
	void enqueue(T t);
	T dequeue();
	Boolean isEmpty();
	Integer size();
}
