package ca.mcmaster.chapter.two.Sort;

public class InsertionSort<T> implements Sort<T> {
	@Override
	public void sort(Comparable<T>[] a) {
		int size = a.length;
		for(int i = 0; i < size; i ++){
			for(int j = i; j > 0 && Sort.less(a, j, j - 1); j--){	Sort.swap(a, j, j-1);	}
		}
	}
	public static void main(String[] args) {
		Sort<Integer> sort = new SelectionSort<>();
		Integer[] a = {1, 2,1,4,2,6,234,65,23,5,2,657,2,546};
		sort.sort(a);
		Sort.show(a);
	}
}
