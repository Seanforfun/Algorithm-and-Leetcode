package ca.mcmaster.chapter.two.Sort;

public class SelectionSort<T> implements Sort<T> {

	@Override
	public void sort(Comparable<T>[] a) {
		int len = a.length;
		for(int i = 0; i < len; i++){
			for(int j = i; j < len; j++){
				if(Sort.less(a, i, j)){		Sort.swap(a, i, j);	}
			}
		}
	}
	public static void main(String[] args) {
		Sort<Integer> sort = new SelectionSort<>();
		Integer[] a = {1, 2,1,4,2,6,234,65,23,5,2,657,2,546};
		sort.sort(a);
		Sort.show(a);
	}
}
