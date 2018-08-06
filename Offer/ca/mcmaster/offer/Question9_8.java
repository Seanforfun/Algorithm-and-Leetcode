package ca.mcmaster.offer;

public class Question9_8 {
	public static int getCountDP(int n, int[] dp){
		if(n < 0)
			return 0;
		else if(n == 0)
			return 1;
		else if(dp[n] != 0){
			return dp[n];
		}else{
			dp[n] = getCountDP(n - 25, dp) + getCountDP(n - 10, dp) + getCountDP(n - 5, dp) + getCountDP(n - 1, dp);
			return dp[n];
		}
	}
	public static int getCountDP(int n){
		int[] dp = new int[n + 1];
		dp[0] = 0;
		return getCountDP(n, dp);
	}
	public static void main(String[] args) {
		System.out.println(getCountDP(5));
	}
}
