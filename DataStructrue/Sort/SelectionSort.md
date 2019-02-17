# Selection Sort(Bubble Sort)

>非常简单的一种排序方法，算法复杂度为O(n^2).
>第一层遍历确定了某个位置，第二层遍历从其后的元素中找到最大（最小）的元素放在该位置。
```Java
public class SelectionSort<T> implements Sort<T> {
	@Override
	public void sort(Comparable<T>[] a) {
		int len = a.length;
		for(int i = 0; i < len; i++){
			for(int j = i; j < len; j++){
				if(Sort.less(a, i, j)){	Sort.swap(a, i, j);	}
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
```
* 结果
>657 546 234 65 23 6 5 4 2 2 2 2 1 1
