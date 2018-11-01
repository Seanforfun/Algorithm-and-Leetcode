## QuickSort 快速排序
快速排序较为稳定，时间复杂度为O(NLogN)。

### 实现逻辑
1. 找到一个pivot(基准点)，用来比较大小。
2. 从左向右遍历，找到第一个大于pivot的元素。
3. 从右向左遍历，找到第一个小于pivot的元素。
4. 交换2和3中找到的两个元素。
4. 交换pivot和index的位置，保证左边的数都小于pivot,右边的数都大于pivot。

### 实现代码
```Java
public void sortColors(int[] nums) {
    if(null == nums) return;
    quickSort(nums, 0, nums.length - 1);
}
private void quickSort(int[] nums, int low, int high){
    if(low > high) return;
    int i = partition(nums, low, high);
    quickSort(nums, low, i - 1);  //递归调用
    quickSort(nums, i + 1, high);
}
private int partition(int[] nums, int low, int high){
    int i = low, j = high + 1;
    int cmp = nums[low];
    while(true){
        while(nums[++i] <= cmp) if(i == high) break;  //找到第一个大于pivot的元素
        while(nums[--j] > cmp) if(j == low) break;  //找到第一个小于pivot的元素
        if(i >= j) break;
        swap(nums, i, j); //交换这两个数字的位置。
    }
    swap(nums, low, j); //将pivot和index换位置，此时左边的的所有数都小于pivot，右边的数都大于pivot。
    return j; //返回当前的index位置。
}
private static void swap(int[] nums, int i, int j){
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```
