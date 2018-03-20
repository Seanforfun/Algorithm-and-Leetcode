package ca.mcmaster.chapter.three;

import ca.mcmaster.chapter.one.FIFO.ListFIFOQueue;

public abstract class SearchSTAbstract<K extends Comparable<K>, V> {
	protected K[] keys;
	protected V[] values;
	protected int N;
	public SearchSTAbstract(int capacity){
		keys = (K[]) new Comparable[capacity];
		values = (V[]) new Object[capacity];
	}
	public int size()	{	return N;		}
	public V get(K k){
		if(isEmpty())	return null;
		int i = binarySearch(k);
		if(i < N && keys[i].compareTo(k) == 0)	return values[i];
		else												return null;
	}
	public void put(K k, V v){
		int i = binarySearch(k);
		if(i < N && keys[i].compareTo(k) == 0){
			values[i] = v;	return;
		}
		for(int j = N; j > i; j--){
			keys[j] = keys[j-1];
			values[j] = values[j-1];
		}
		keys[i] = k;
		values[i] = v;
		N++;
	}
	public Boolean isEmpty()	{	return N == 0;	}
	public abstract int binarySearch(K k);
	public void delete(K k){
		int i = binarySearch(k);
		if(i < N && keys[i].compareTo(k) == 0){
			for(int j = i; j < N; j++){
				keys[j] = keys[j+1];
				values[j] = values[j+1];
			}
			keys[N] = null;
			values[N] = null;
			N--;
		}
	}
	public K min(){	return keys[0];	}
	public K max(){	return keys[N-1];	}
	public K select(int k){	return 	keys[k];	}
	public K ceiling(K k){
		int i = binarySearch(k);
		return keys[i];
	}
	public Iterable<K> keys(int lo, int hi){
		if(lo > hi)		return null;
		if(hi >= N	)	throw new ArrayIndexOutOfBoundsException();
		ListFIFOQueue<K> queue = new ListFIFOQueue<>();
		for(int i = binarySearch(keys[lo]); i < binarySearch(keys[hi]); i++){
			queue.enqueue(keys[i]);
			System.out.println(i);
		}
		if(contains(keys[hi]))	queue.enqueue(keys[hi]);
		return queue;
	}
	public Boolean contains(K k){
		int i = binarySearch(k);
		return i < N;
	}
}
