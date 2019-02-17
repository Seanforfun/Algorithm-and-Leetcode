## 并归排序
#### 并归排序的原理
* 当两个数组本身是有序的，我们通过并归排序后仍然是有序的。

#### 原地并归
* 在进行并归时，如果我们要为每次递归创建一个新的数组将会是很大的开销，所以我们在一开始初始化一个aux，其长度和我们要排序的数组一致。

```Java
private static void merge(int[] nums, int lo, int mid, int hi){
		for(int i = lo; i <= hi; i++)
			aux[i] = nums[i];
		int i = lo, j = mid + 1;
		for(int k = lo; k <= hi; k++){
			if(i >  mid) nums[k] = aux[j++];
			else if(j > hi) nums[k] = aux[i++];
			else if(aux[i] <= aux[j]) nums[k] = aux[i++];
			else nums[k] = aux[j++];
		}
	}
```

#### 排序（自顶向下），利用了分治的思想
```Java
public static void sort(int[] nums, int lo, int hi){
		aux = new int[nums.length];
		if(lo >= hi) return;
		int mid = (hi - lo) / 2 + lo;
		sort(nums, lo, mid);	//将mid左侧的所有元素排序。
		sort(nums, mid + 1, hi);	//将mid右侧的所有元素排序。
		merge(nums, lo, mid, hi);	//此时左右已经是有序的了，我们在将左右并归起来。
	}
```