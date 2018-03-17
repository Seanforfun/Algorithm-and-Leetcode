package ca.mcmaster.chapter.two.Sort;

public class Sort {
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
	public static void main(String[] args) {
		Integer[] a = new Integer[]{2,3,5,2,45,1,4,2,5,7,3,7,432,96,7,23,8};
//		selectionSort(a);
		insertSort(a);
		show(a);
	}
}
