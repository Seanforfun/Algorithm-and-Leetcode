# Question1_6

#### Solution
```Java
public class Question1_6 {
	public static int[][] rotate(int m[][], int n){
		for(int layer = 0; layer < n/2; layer++){
			int first = layer;
			int last = n - 1 - layer;
			for(int i = first; i < last; i++){
				int offset = i - first;
				int top = m[first][i];
				m[first][i] = m[last-offset][first];
				m[last-offset][first] = m[last][last-offset];
				m[last][last-offset] = m[i][last];
				m[i][last] = top;
			}
		}
		return m;
	}
	public static void main(String[] args) {
		int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		a = rotate(a, 3);
		for(int i = 0; i < 3; i++){
			for(int j = 0; j <3; j++)
				System.out.print(a[i][j] + "		");
			System.out.println();
		}
	}
}
```