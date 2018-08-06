package ca.mcmaster.offer;

public class Question9_9 {
	public static int count = 0;
	public static void eightQueue(int[][] dp, int row){	//dp[]: index: row; value = column
		if(row == 8){
			System.out.println("Number: " + ++count);
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					System.out.print(dp[i][j] + " ");
				}
				System.out.println();
			}
			return;
		}
		int [][] clone = dp.clone();
		for(int i = 0 ; i < 8; i++){
			clone[row][i] = 1;
			if(isSafe(clone, row, i))
				eightQueue(clone, row + 1);
			clone[row][i] = 0;
		}
	}
	private static boolean isSafe(int[][] dp, int row, int column){
		if(row >= 1){
			for(int i = row - 1; i >=0; i--)
				if(dp[i][column] == 1) return false;
		}
		int tempRow = row;
		int tempColumn = column;
		while(tempRow > 0 && tempColumn > 0){	//left up
			if(dp[--tempRow][--tempColumn] == 1) return false;
		}
		tempRow = row;
		tempColumn = column;
		while(tempRow > 0 && tempColumn < 7){
			if(dp[--tempRow][++tempColumn] == 1) return false;
		}
		return true;
	}
	public static void main(String[] args) {
		int[][] dp = new int[8][8];
		eightQueue(dp, 0);
	}
}
