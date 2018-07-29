# Question1_4

#### Solution
```Java
public class Question1_4 {
	public static String replaceWhiteSpace(char[] s, int length){
		StringBuilder sb = new StringBuilder();	//要注意StringBuilder是非线程安全的！
		for(int i = 0; i < length; i++){
			if(s[i] == ' ')	//要注意是双等号！
				sb.append("%20");
			else
				sb.append(s[i]);	//StringBuilder可以append char型变量，不需要转化成String类型。
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		String test = " adf sdf123  24";
		System.out.println(replaceWhiteSpace(test.toCharArray(), test.length()));
	}
}
```