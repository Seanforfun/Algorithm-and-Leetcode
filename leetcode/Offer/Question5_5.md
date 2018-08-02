# Question5_5

#### Solution
```Java
public class Question5_5 {
	public static int bitNeedSwap(int a, int b){
		int c = a ^ b;
		int count = 0;
		while(c != 0){
			count = (c & 1) == 1 ? count+1:count;
			c >>= 1;
		}
		return count;
	}
	public static int bitNeedSwap1(int a, int b){
		int count = 0;
		int c = a ^ b;
		while(c != 0){
			c &= (c - 1);	//用于将最后一位1转换成0.
			count ++;
		}
		return count;
	}
	public static void main(String[] args) {
		System.out.println(bitNeedSwap1(0b1001101011, 0b1010100111));
	}
}
```
