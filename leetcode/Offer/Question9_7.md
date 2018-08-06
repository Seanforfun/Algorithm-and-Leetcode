# Question9_7

#### Solution
* 对于当前的像素，如果颜色和ocolor相同，就尝试将其上下左右全部填充。
* 如果当前的像素和ocolor不同，则在此处停止回归（即不再继续填充颜色）。
```Java
public class Question9_7 {
	public enum Color{
		RED, 
		GREEN, 
		BLUE
	}
	public static void fillColor(Color[][] screen, int x, int y, Color ncolor){
		fillColor(screen, x, y, screen[x][y], ncolor);
	}
	public static void fillColor(Color[][] screen, int x, int y, Color ocolor, Color ncolor){
		if(x < 0 || y < 0 || x >= screen[0].length || y >= screen.length){
			return;
		}else{
			if(ocolor == screen[y][x]){
				screen[y][x] = ncolor;
				fillColor(screen, x - 1, y, ocolor, ncolor);	//left
				fillColor(screen, x, y - 1, ocolor, ncolor);	//up
				fillColor(screen, x + 1, y, ocolor, ncolor);	//right
				fillColor(screen, x, y + 1, ocolor, ncolor);	//down
			}
		}
	}
	public static void main(String[] args) {
		Color[][] screen = new Color[10][10];
//		for(int i = 0; i < 10; i++){
//			screen[3][i] = Color.BLUE;
//		}
		fillColor(screen, 8, 8, Color.GREEN);
		for(int i = 0; i < screen.length; i++){
			for (int j = 0; j < screen[0].length; j++) {
				System.out.print(screen[i][j]);
			}
			System.out.println();
		}
	}
}
```
