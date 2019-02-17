#二分法查找

## 原理
* 对于有序数列，我们不断比较所查找值和中间值的关系，然后不断缩小查找范围。
* 最典型的递归调用

```Java
public class BinarySearchReview {
	public static int binarySearch(int[] nums, int n){
		return binarySearch(nums, n, 0, nums.length - 1);
	}
	private static int binarySearch(int[] nums, int n, int low, int high){
		if(low > high) return -1;
		int mid = (low + high) / 2;
		if(nums[mid] == n) return mid;
		else if(nums[mid] > n)
			return binarySearch(nums, n, low, mid - 1);
		else
			return binarySearch(nums, n, mid + 1, high);
	}
	public static void main(String[] args) {
		int[] a = {0, 1,2,3,4,5,6,7,8,9};
		System.out.println(binarySearch(a, 5));
	}
}
```