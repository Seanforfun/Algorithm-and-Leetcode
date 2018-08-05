# Question9_1

#### Solution
```Java
public class Question9_1 {
	public static int countWays(int n){	//从结束向开头递归，对于n，都有三种可能，即最后一步是通过+3， +2， +1到达的，在此处进入递归。
		if(n < 0)
			return 0;
		else if (n == 0)
			return 1;
		else{
			return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
		}
	}
	public static int countWaysDP(int n){	//通过动态规划，将已经计算过的值缓存起来，而后的一些结果是基于原来的一些结果，所以将这些值缓存起来加加快速度。
	//尤其考虑到一些值，每次都通过递归进行计算，所以缓存起来减少了递归的次数。
		int[] dp = new int[n + 1];
		for(int i = 0; i < n + 1; i++)
			dp[i] = -1;
		return countWaysDP(n, dp);
	}
	public static int countWaysDP(int n, int[] dp){
		if(n < 0)
			return 0;
		else if(n == 0){
			return 1;
		}else if(dp[n] != -1){
			return dp[n];
		}else{
			dp[n] = countWaysDP(n - 1, dp) + countWaysDP(n - 2, dp) + countWaysDP(n - 3, dp);
			return dp[n];
		}
	}
	public static void main(String[] args) {
		System.out.println(countWaysDP(3));
	}
}
```
