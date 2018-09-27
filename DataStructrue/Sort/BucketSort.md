## 桶排序
#### 桶排序方法
1. 先找出数组的最大值max，我们创建一个最大值max + 1长度的数组用于记录所有的数组出现的次数。
2. 因为生成的bucket是按序递增的，我们根据bucket重新填满我们的数组。

#### 代码

```Java
package ca.mcmaster.chapter.two.Sort;

public class BucketSort {
	private static void sort(int[] nums){
		int max = Integer.MIN_VALUE;
		for(int num : nums)
			max = Math.max(max, num);
		int[] bucket = new int[max+1];
		for(int i = 0; i < nums.length; i++)
			bucket[nums[i]]++;
		for(int i = 0, j = 0; j < bucket.length; j++){
			while(bucket[j]-- > 0)
				nums[i++] = j;
		}
	}
	public static void main(String[] args) {
		int[] arr = new int[]{1,4,5,3,2,5,7};
		sort(arr);
		for(int i : arr)
			System.out.print(i + "  ");
		System.out.println();
	}
}
```

### 复杂度分析
* 时间复杂度：O(N)
* 额外空间复杂度：O(N)