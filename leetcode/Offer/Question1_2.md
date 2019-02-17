# Question1_2

#### Solution
```Java
public class Question1_2 {
	public static String reverseString(String str){
		char[] arr = str.toCharArray();	//要记住将String转换成charArray的方法。
		int len = str.length();
		int low = 0;
		int high = len - 1;
		while(low < high){
			char temp = arr[low];
			arr[low] = arr[high];
			arr[high] = temp;
			low++; high--;
		}
		return new String(arr);	//将charArray转换成字符串的方法。
	}
	public static void main(String[] args) {
		System.out.println(reverseString("12345"));
	}
}
```