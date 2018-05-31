# Sort 排序
>一般排序所用的元素都会实现Comparable进行比较大小以确定先后顺序。
>定义一个排序接口实现了交换元素，显示元素和比较大小等方法。
```Java
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
```