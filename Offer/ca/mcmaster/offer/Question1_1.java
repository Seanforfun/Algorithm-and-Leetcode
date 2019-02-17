package ca.mcmaster.offer;

import java.util.HashSet;
import java.util.Set;

public class Question1_1 {
	public static boolean isDuplicateString(String str){
		int length = str.length();
		Set<Character> set = new HashSet<Character>();
		for(int i=0; i < length; i++){
			char c = str.charAt(i);
			if(!set.contains(c))
				set.add(c);
			else
				return false;
		}
		return true;
	}
	public static void main(String[] args) {
		System.out.println(isDuplicateString("12142fsafdhsajkfhldsa"));
	}
}
