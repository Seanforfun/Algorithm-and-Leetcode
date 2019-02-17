# Question5_6

#### Solution
```Java
public class Question5_6 {
	public static int swapEvenOdd(int n){
		return (((n & 0xAAAAAAAA) << 1) | ((n & 0x55555555) >> 1));
	}
	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(swapEvenOdd(0B101010101010101)));
	}
}
```
