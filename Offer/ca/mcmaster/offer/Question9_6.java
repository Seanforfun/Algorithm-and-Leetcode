package ca.mcmaster.offer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Question9_6 {
	public static List<String> getValidParentheses(int n){
		List<String> result = new ArrayList<String>();
		getValidParentheses(n, result);
		return result;
	}
	public static void getValidParentheses(int n, List<String> list){
		if(n == 1)
			list.add("()");
		else{
			getValidParentheses(n - 1, list);
			Set<String> set = new HashSet<>();
			List<String> temp = new ArrayList<>();
			for(String s:list){
				for(int i = 0; i < s.length(); i++){
					String tempString = insertParentheses(i, s);
					if(!set.contains(tempString)){
						set.add(tempString);
						temp.add(tempString);
					}
				}
			}
			list.clear();
			for(String s : temp)
				list.add(s);
		}
	}
	private static String insertParentheses(int pos, String s){
		String first = s.substring(0, pos);
		String second = s.substring(pos);
		return first + "()" + second;
	}
	public static List<String> getValidParentheses1(int n){
		List<String> result = new ArrayList<String>();
		backtrace(result, "", n, n);
		return result;
	}
	public static void backtrace(List<String> list, String s, int left, int right){
		if(left == 0 && right == 0)
			list.add(s);
		if(left > 0) backtrace(list, s + "(", left - 1, right);
		if(right > 0 && right > left) backtrace(list, s + ")", left, right - 1);
	}
	public static void main(String[] args) {
		List<String> result = getValidParentheses1(3);
		for(String s : result)
			System.out.println(s);
	}
}
