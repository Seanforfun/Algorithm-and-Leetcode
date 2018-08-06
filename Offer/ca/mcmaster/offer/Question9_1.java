package ca.mcmaster.offer;

public class Question9_1 {
	public static int countWays(int n){
		if(n < 0)
			return 0;
		else if (n == 0)
			return 1;
		else{
			return countWays(n - 1) + countWays(n - 2) + countWays(n - 3); 
		}
	}
	public static int countWaysDP(int n){
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
