package ca.mcmaster.offer;

public class Question1_5 {
	public static String compress(String str){
		int len = str.length();
		char prev = str.charAt(0);
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
		sb.append(count);
		return sb.toString();
	}
	public static void main(String[] args) {
		System.out.println(compress("aaaaadfsssscssd"));
	}
}
