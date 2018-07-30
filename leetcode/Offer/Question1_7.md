# Question1_7

#### Solution
```Java
public class Question1_7 {	//这道题我是通过Set做的，其实还可以通过数组实现。
	public static int[][] setZero(int[][] m){
		int rowNum = m.length;
		int colNum = m[0].length;
		Set<Integer> rowSet = new HashSet<>();
		Set<Integer> colSet = new HashSet<>();
		for(int i = 0; i < rowNum; i++){
			for(int j = 0; j < colNum; j++){
				if(m[i][j] == 0){
					colSet.add(j);
					rowSet.add(i);
				}
			}
		}
		for(Integer row : rowSet){
			for(int i = 0; i < colNum; i++){
				m[row][i] = 0;
			}
		}
		for(Integer column:colSet){
			for(int i = 0; i < rowNum; i++)
				m[i][column] = 0;
		}
		return m;
	}
	public static void main(String[] args) {
		int[][] test = {{1,2,0}, {4,0,6}, {7,8,9}};
		int[][] result = setZero(test);
		for(int i = 0; i < 3; i ++){
			for(int j = 0; j < 3; j++){
				System.out.print(result[i][j] + "	");
			}
			System.out.println();
		}
	}
}
```

* 书中提供利用数组的方法：
```Java
public class Question1_7 {
	public static int[][] setZeroNew(int[][] m){
		int rowNum = m.length;
		int colNum = m[0].length;
		boolean[] rowFlag = new boolean[rowNum];	//注意定义的是数组型，应该写boolean[]而不是boolean
		boolean[] colFlag = new boolean[colNum];
		for(int i = 0; i < rowNum; i++){
			for(int j = 0; j < colNum; j++){
				if(m[i][j] == 0){
					rowFlag[i] = true;	//定义成true而不是0
					colFlag[j] = true;
				}
			}
		}
		for(int i = 0; i < rowNum; i++)
			for(int j = 0; j < colNum; j++)
				if(rowFlag[i] || colFlag[j])
					m[i][j] = 0;
		return m;
	}
	public static void main(String[] args) {
		int[][] test = {{1,2,0}, {4,0,6}, {7,8,9}};
		int[][] result = setZeroNew(test);
		for(int i = 0; i < 3; i ++){
			for(int j = 0; j < 3; j++){
				System.out.print(result[i][j] + "	");
			}
			System.out.println();
		}
	}
}
```