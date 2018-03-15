package ca.mcmaster.chapter.one;

import edu.princeton.cs.algs4.In;

public class MyString {
	public static Boolean isPalindrome(String s){
		int len = s.length();
		for(int i = 0; i < (len /2); i++){
			if(s.charAt(i) != s.charAt(len - 1 - i)){
				return false;
			}
		}
		return true;
	}
	
	public static Boolean isSorted(String[] s){
		for(int i = 1; i < s.length; i++){
			String pre = s[i-1];
			String next = s[i];
			System.out.println("pre is: " + pre + "; next is: " + next);
			if(pre.compareTo(next) > 0)
				return false;
		}
		return true;
	}
	
	public static void main(String[] args){
//		System.out.println(isPalindrome("112233454332211"));
//		System.out.println(isSorted(new String[]{"a","e","c","d","e","f"}));
	}
}
