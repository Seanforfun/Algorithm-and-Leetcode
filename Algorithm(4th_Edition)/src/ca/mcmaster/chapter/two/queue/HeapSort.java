package ca.mcmaster.chapter.two.queue;

public class HeapSort {
	private static Boolean less(Comparable[] a,int i, int j){	return a[i].compareTo(a[j]) < 0;	}
	private static void swap(Comparable[] a, int i, int j){	Comparable temp = a[i]; a[i] = a[j]; a[j] = temp;	}
	public static void sink(Comparable[] a, int k, int length){
		while(2 * k <= length){
			int j = 2 * k;
			if(j < length && less(a, j, j+1))	j++;
			if(!less(a, k, j))	break;
			swap(a, k, j);
			k = j;
		}
	}
	public static void heapSort(Comparable[] a){
		int length = a.length-1;
		for(int i = length/2; i >=0; i--)	sink(a, i, length);
		while(length >= 1){
			swap(a, 0, length--);
			sink(a, 0, length);
		}
	}
	
	public static void show(Comparable[] a){
		for(int i = 0; i < a.length; i++){
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		Integer[] a = new Integer[]{18,27,33,55,6,3,23,2,3,5,2,45,1,4,2,5,7,3,7,456,96,7,23,8};
		heapSort(a);
		show(a);
	}
}
