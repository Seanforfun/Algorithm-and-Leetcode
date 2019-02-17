# Question5_3

#### Solution
```Java
public class Question5_3 {
	public static int getClosestBigger(int n){
		int c = n;
		int p = 0;
		int q = 0;
		while(c != 0 && ((c & 1) == 0)){
			q++;
			c >>= 1;
		}
		p = q;
		while((c & 1) == 1){
			p ++;
			c >>= 1;
		}
		if(p >= 31) return -1;
		n |= (1 << p);
		n &= ~((1 << p) - 1);
		n |= (1 << (p - q - 1)) - 1;
		return n;
	}
	public static int getClosestSmall(int n){
		int c = n;
		int p = 0;
		int q = 0;
		while((c & 1) == 1){
			q ++;
			c >>= 1;
		}
		p = q;
		while(c != 0 && (c & 1) == 0){
			p ++;
			c >>= 1;
		}
		n &= ~((1 << (p + 1)) - 1);
		System.out.println(Integer.toBinaryString(~((1 << (p - q)) - 1)));
		int mask = ((1 << p) - 1) & ~((1 << (p - q - 1)) - 1);
		n |= mask;
		return n;
	}
	public static void main(String[] args) {
//		System.out.println(getClosestBigger(Integer.MAX_VALUE));
		System.out.println(getClosestSmall(11));
	}
}
```
