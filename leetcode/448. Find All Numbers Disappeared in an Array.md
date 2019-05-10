## 448. Find All Numbers Disappeared in an Array

### Question
Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.

Find all the elements of [1, n] inclusive that do not appear in this array.

Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.

```
Example:

Input:
[4,3,2,7,8,2,3,1]

Output:
[5,6]
```

### Solutions
* Method 1: O(n) S(n)
    ```Java
   class Solution {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for(int num : nums) set.add(num);
            List<Integer> res = new ArrayList<>();
            for(int i = 1; i <= nums.length; i++)
                if(!set.contains(i))
                    res.add(i);
            return res;
        }
    }
	```

* Method 2: O(n) + S(0)
    * Since the index range is same as the length of the array, once we read a value, we change the number saved in that index - 1 to -1.
    * We need to recursively to change the index.
    * Finally we check the indices that are not -1.
    ```Java
    class Solution {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            for(int i = 0; i < nums.length; i++){
                if(nums[i] == -1) continue;
                int index = nums[i];
                while(nums[index - 1] != -1 && nums[index - 1] != index){
                    int nextIndex = nums[index - 1];
                    nums[index - 1] = -1;
                    index = nextIndex;
                }
                if(nums[index - 1] == index) nums[index - 1] = -1;
            }
            List<Integer> res = new ArrayList<>();
            for(int i = 0; i < nums.length; i++){
                if(nums[i] > 0) res.add(i + 1);
            }
            return res;
        }
    }
    ```

### Reference
1. [LeetCode-448：Find All Numbers Disappeared in an Array （寻找缺失多个数字）](https://blog.csdn.net/Koala_Tree/article/details/78349644)