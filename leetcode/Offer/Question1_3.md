# Question1_3

#### Solution
* 我原来是想要通过map去存储a和b中的字符种类和次数，但是这样要做3次遍历，最后一次是遍历两个map。
* 参考书上的做法，是将a和b分别排序，再比较字符串是否相等。这样就减少了遍历的次数。

```Java
public class Question1_3 {
	public static boolean canBecome(String a, String b){
		char[] aArr = a.toCharArray();
		char[] bArr = b.toCharArray();
		Arrays.sort(aArr);
		Arrays.sort(bArr);
		return new String(aArr).equals(new String(bArr));
	}
	public static void main(String[] args) {
		System.out.println(canBecome("apple", "plaep"));
	}
}
```