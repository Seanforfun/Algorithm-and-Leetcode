package ca.mcmaster.offer;

import java.util.Arrays;

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
