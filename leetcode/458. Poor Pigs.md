## 458. Poor Pigs

### Question
There are 1000 buckets, one and only one of them is poisonous, while the rest are filled with water. They all look identical. If a pig drinks the poison it will die within 15 minutes. What is the minimum amount of pigs you need to figure out which bucket is poisonous within one hour?

Answer this question, and write an algorithm for the general case.

General case:

If there are n buckets and a pig drinking poison will die within m minutes, how many pigs (x) you need to figure out the poisonous bucket within p minutes? There is exactly one bucket with poison.

Note:
1. A pig can be allowed to drink simultaneously on as many buckets as one would like, and the feeding takes no time.
2. After a pig has instantly finished drinking buckets, there has to be a cool down time of m minutes. During this time, only observation is allowed and no feedings at all.
3. Any given bucket can be sampled an infinite number of times (by an unlimited number of pigs).


### Solutions
* Method 1: Information Theory
    * For a single pig(bit), we can figure out how many states can be representated.
        * Example: minutesToTest 60 mins, minutesToDie 15mins
        * states to resent die: [15, 30, 45, 60] + live = 5
    * The question changed to: how many bits required to resent the value of buckets.
    ```Java
   class Solution {
        public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
            //how many states can be represented by a pig within the test.
            int statesToPresent = minutesToTest / minutesToDie + 1;
            return (int)Math.ceil(Math.log(buckets) / Math.log(statesToPresent));
        }
    }
	```

### Reference
1. [leetcode 458. Poor Pigs 可怜的猪 + 猪试毒需要最少的数量 + 发现规律](https://blog.csdn.net/JackZhang_123/article/details/78775716)