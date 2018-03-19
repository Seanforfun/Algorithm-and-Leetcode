package ca.mcmaster.chapter.two.Sort;

public class Sort {
	private static Comparable[] aux;
	@SuppressWarnings("rawtypes")
	public static void selectionSort(Comparable[] a){
		int length = a.length;
		for(int i = 0; i < length; i++){
			int min = i;
			for(int j = i + 1; j < length; j++)
				if(less(a[j], a[min])) min = j;
			swap(a, i, min);
		}
	}
	
	public static void insertSort(Comparable[] a){
		int length = a.length;
		for(int i = 1; i < length; i ++){
			for(int j = i; j > 0 && less(a[j], a[j-1]); j--)
				swap(a, j, j-1);
		}
	}
	
	public static void ShellSort(Comparable[] a){
		int length = a.length;
		int h = 1;
		while(h < length/3)	h = 3*h + 1;
		while(h >= 1){
			for(int i = h; i < length; i++){
				for(int j = i; j >= h && less(a[j], a[j-h]); j -= h)
					swap(a, j, j-h);
			}
			h /= 3;
		}
	}
	
	public static void mergeSort(Comparable[] a){
		aux = new Comparable[a.length];
		mergeSortIn(a, 0, a.length - 1);
	}
	
	public static void mergeSortIn(Comparable[] a, int lo, int hi){
		if(lo >= hi) return;
		int mid = (hi - lo) / 2 + lo;
		mergeSortIn(a, lo, mid);
		mergeSortIn(a, mid + 1, hi);
		merge(a, lo, mid, hi);
	}
	
	public static void mergeSortBU(Comparable[] a){
		int length = a.length;
		aux = new Comparable[length];
		for(int sz = 1; sz < length; sz *= 2){
			for(int lo = 0; lo < length - sz; lo += sz * 2)
				merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, length-1));
		}
	}
	
	public static Boolean less(Comparable a, Comparable b){
		return a.compareTo(b) < 0;
	}
	public static void swap(Comparable[] a, int i, int j){
		Comparable temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	public static void show(Comparable[] a){
		for(int i = 0; i < a.length; i++){
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
	public static Boolean isSorted(Comparable[] a){
		for(int i = 1; i < a.length; i++)
			if(less(a[i], a[i-1])) return false;
		return true;
	}
	public static void merge(Comparable[] a, int lo, int mid, int hi){
		int i = lo, j = mid + 1;
		if(less(a[mid], a[mid+1])) return;
		for (int k = lo; k < aux.length; k++) 
			aux[k] = a[k];
		for(int k = lo; k < a.length; k++){
			if(i > mid)								a[k] = aux[j++];
			else if(j > hi) 							a[k] = aux[i++];
			else if(less(aux[i], aux[j]))	a[k] = aux[i++];
			else											a[k] = aux[j++];
		}
	}
	
	public static int partition(Comparable[] a, int lo, int hi){
		int i = lo, j = hi + 1;
		Comparable v = a[lo];
		while(true){
			while(less(a[++i], v)) if(i == hi) break;
			while(less(v, a[--j])) if(j == lo) break;
			if(i >= j) break;
			swap(a, i, j);
		}
		swap(a, lo, j);
		return j;
	}
	
	public static void quickSort(Comparable[] a, int lo, int hi){
		if(lo >= hi) return;
		int j = partition(a, lo, hi);
		quickSort(a, lo, j-1);
		quickSort(a, j+1, hi);
	}
	
	public static void main(String[] args) {
//		Integer[] a = new Integer[]{18,27,33,55,6,3,23,2,3,5,2,45,1,4,2,5,7,3,7,432,96,7,23,8};
//		Integer[] a = new Integer[]{1,3,5,9,2,5,6,7};
		Integer[] a = new Integer[]{2,1,3,4,5,6,7,8,9};
//		selectionSort(a);
//		insertSort(a);
//		ShellSort(a);
//		merge(a, 0, 3, 7);
//		mergeSortBU(a);
		quickSort(a, 0, a.length -1);
		show(a);
	}
}
