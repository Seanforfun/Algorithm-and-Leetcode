## 1.Two Sum
### Thinking:
* Method1: Search
We need to find if (target - current) is in the set, if not, add current to a hashSet for checking.
Since to need to return the index according to value, so the key is value of number and value is index of number in array.
```Java
	class Solution {
	    public int[] twoSum(int[] nums, int target) {
	        Map<Integer, Integer> map = new HashMap<>();
	        int numLength = nums.length;
	        for(int i = 0; i < numLength; i++){
	            if(!map.containsKey(target - nums[i])){
	                map.put(nums[i], i);
	            }else{
	                return new int[]{i, map.get(target - nums[i])};
	            }
	        }
	        return null;
	    }
	}
```

### 二刷
1. 二刷的时候仍然想到用hash表解决该问题。键存的是数字的值，值存的是index。
2. 在遍历的时候，我们同时检查target - nums[i]是否存在。
	* 如果不存在，我们将数值加入哈希表。
	* 如果存在，我们直接返回index。

```Java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(!map.containsKey(target - nums[i]))
                map.put(nums[i], i);
            else
                return new int[]{i, map.get(target - nums[i])};
        }
        return null;
    }
}
```