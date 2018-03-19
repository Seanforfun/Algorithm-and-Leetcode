package ca.mcmaster.chapter.two.queue;

public interface MaxPriorityQueue<T extends Comparable<T>> {
	/**
	 * @Description: Insert an element into the priority queue
	 * @param t
	 */
	public void insert(T t);
	
	/**
	 * @Description: Return the max value from the priority queue.
	 * @return
	 */
	public T max();
	
	/**
	 * @Description: Return and delete the max value from the priority queue.
	 * @return
	 */
	public T delMax();
	
	/**
	 * @Description: Check if current queue is empty.
	 * @return
	 */
	public Boolean isEmpty();
	
	/**
	 * @Description: Return the number of the elements left in the queue.
	 * @return
	 */
	public int size();
}
