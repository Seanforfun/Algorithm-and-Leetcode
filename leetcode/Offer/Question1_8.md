# Question1_8

#### Solution
* 这道题只能使用isSubstring一次，所以我们不能单纯的通过遍历一次次判断，通过研究可以发现，无论s1,s2如何旋转，只要答案应该是true，我们可以得知s1一定是s2s2的子集。
* 这道题的思路大于编程技巧，在测试之前我们一定要认真的研究题目的方法。

```Java
public class Question1_8 {
	public static boolean isSubstring(String s1, String s2){
		if(s1.contains(s2))
			return true;
		return false;
	}
	public static boolean isRotation(String s1, String s2){
		if(s1.length() != s2.length())
			return false;
		String doubleString = s1 + s1;
		if(isSubstring(doubleString, s2))
			return true;
		return false;
	}
	public static void main(String[] args) {
		System.out.println(isRotation("waterbottle", "terbottlewa"));
	}
}
```