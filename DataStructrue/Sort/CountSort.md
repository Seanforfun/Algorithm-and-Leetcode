# 计数排序
#### 计数排序方法
1. 先找出数组的最大值max，我们创建一个最大值max + 1长度的数组用于记录所有的数组出现的次数。
2. 因为生成的bucket是按序递增的，我们根据bucket重新填满我们的数组。

#### 技术排序分析
技术排序可以使用额外的内存空间，所以思考上就会简单很多。总体的效率是Ο(n+k)，k是整数的范围。
1. 举例： 7,6,3,1,3，其中最大值为7，我们建造一个大小为8的数组（即为max + 1）。
2. 我们遍历需要排序的数组，可以得到：
    | 0 | 1 |  2 | 3 | 4 | 5 | 6 | 7 |
    |---|---|---|---|---|---|---|---|
    | 0 | 1 | 0 | 2 | 0 | 0 | 1 | 1 |
3. 其中上排是数字，下排是数字对应出现的次数。
4. 至此，我们可以遍历这个数组的每一个位置，然后将index填充到新的数组中。实现排序

#### 代码
```Java
package ca.mcmaster.chapter.two.Sort;

public class CountSort {
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