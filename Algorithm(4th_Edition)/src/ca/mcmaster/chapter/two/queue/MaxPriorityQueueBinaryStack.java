package ca.mcmaster.chapter.two.queue;

public class MaxPriorityQueueBinaryStack<T extends Comparable<T>> implements MaxPriorityQueue<T> {
	private T[] pq;
	private int N = 0;
	public MaxPriorityQueueBinaryStack(int maxN){
		pq = (T[]) new Comparable[maxN + 1];
	}
	public void insert(T t) {
		pq[++N] = t;
		swim(N);
	}
	public T max() {
		return pq[1];
	}
	public T delMax() {
		T max = pq[1];
		swap(1, N--);
		pq[N + 1] = null;
		sink(1);
		return max;
	}
	public Boolean isEmpty() {	 return N == 0;	}
	public int size() {	return N;		}
	private Boolean less(int i, int j){	return pq[i].compareTo(pq[j]) < 0;	}
	private void swap(int i, int j){	T temp = pq[i]; pq[i] = pq[j]; pq[j] = temp;	}
	private void swim(int k){
		while(k > 1 && less(k/2, k)){
			swap(k, k/2);
			k = k/2;
		}
	}
	private void sink(int k){
		while(2 * k <= N){
			int j = 2 * k;
			if(j < N && less(j, j+1))	j++;
			if(!less(k, j))	break;
			swap(k, j);
			k = j;
		}
	}
	
	public static void main(String[] args) {
		MaxPriorityQueueBinaryStack<Integer> stack = new MaxPriorityQueueBinaryStack<>(100);
		stack.insert(1);
		stack.insert(8);
		stack.insert(2);
		stack.insert(4);
		stack.insert(5);
		stack.insert(3);
		stack.insert(6);
		for (int i = 0; i < 7; i++) {
			System.out.println(stack.delMax());
		}
	}
}
