# Insert Sort
>算法复杂度仍然是O(n^2)
>内外两次循环，外循环定位，内循环通过比较使外循环定位之前的元素均排序完成。

```Java
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
```
* 结果
657 546 234 65 23 6 5 4 2 2 2 2 1 1