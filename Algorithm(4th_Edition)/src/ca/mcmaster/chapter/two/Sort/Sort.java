package ca.mcmaster.chapter.two.Sort;

public interface Sort<T> {
	/**
	 * @Description: Sort the array in a descent order.
	 * @param a: array to sort
	 */
	public void sort(Comparable<T>[] a);
	@SuppressWarnings("unchecked")
	static <T> boolean less(Comparable<T>[] a, int i, int j){
		return a[i].compareTo((T)a[j]) < 0;
	}
	static void swap(Comparable[] a, int i, int j){
		Comparable temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	static void show(Comparable[] a){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < a.length; i++)
			sb.append(a[i] + " ");
		System.out.println(sb.toString());
	}
}
