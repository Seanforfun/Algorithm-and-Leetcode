package ca.mcmaster.offer;

public class Question1_2 {
	public static String reverseString(String str){
		char[] arr = str.toCharArray();
		int len = str.length();
		int low = 0;
		int high = len - 1;
		while(low < high){
			char temp = arr[low];
			arr[low] = arr[high];
			arr[high] = temp;
			low++; high--;
		}
		return new String(arr);
	}
	
	public static void main(String[] args) {
		System.out.println(reverseString("12345"));
	}
}
