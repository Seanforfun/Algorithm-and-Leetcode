package ca.mcmaster.offer;

public class Question1_4 {
	public static String replaceWhiteSpace(char[] s, int length){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++){
			if(s[i] == ' ')
				sb.append("%20");
			else
				sb.append(s[i]);
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		String test = " adf sdf123  24";
		System.out.println(replaceWhiteSpace(test.toCharArray(), test.length()));
	}
}
