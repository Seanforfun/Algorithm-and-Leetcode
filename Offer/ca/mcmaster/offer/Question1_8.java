package ca.mcmaster.offer;

public class Question1_8 {
	public static boolean isSubstring(String s1, String s2){
		if(s1.contains(s2))
			return true;
		return false;
	}
	public static boolean isRotation(String s1, String s2){
		if(s1.length() != s2.length())
			return false;
		String doubleString = s1 + s1;
		if(isSubstring(doubleString, s2))
			return true;
		return false;
	}
	public static void main(String[] args) {
		System.out.println(isRotation("waterbottle", "terbottlewa"));
	}
}
