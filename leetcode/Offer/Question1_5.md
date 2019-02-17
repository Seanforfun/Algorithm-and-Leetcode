# Question1_5

#### Solution
```Java
public class Question1_5 {
	public static String compress(String str){
		int len = str.length();
		char prev = str.charAt(0);	//因为第一个字符无法和别的字符进行比较，所以我们拉在循环外面做。
		int count = 1;
		StringBuilder sb = new StringBuilder();
		sb.append(prev);
		for(int i = 1; i < len; i++){
			if(str.charAt(i) != prev){
				sb.append(count);
				prev = str.charAt(i);
				sb.append(prev);
				count = 1;
			}else
				count++;
		}
		sb.append(count);	//最后一个字符没有后面的字符进行比较，所以无法写入count，所以应该在循环外写入count。
		return sb.toString();
	}
	public static void main(String[] args) {
		System.out.println(compress("aaaaadfsssscssd"));
	}
}
```