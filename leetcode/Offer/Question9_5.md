# Question9_5

#### Solution
* 按顺序提出字符，并将其插入原来已经有的字符的每一个可能的位置。
	* 已经有ab,要插入c。插入的位置是0， 1， 2.
		* cab, acb, abc
```Java
public class Question9_5 {
	public static List<String> getPerm(String s){
		List<String> result = new ArrayList<>();
		if(null == s) return result;
		getPerm(s, s.length(), result);
		return result;
	}
	public static void getPerm(String s, int index, List<String> result){
		if(index == 1){
			result.add(s.substring(0, 1));
			return;
		}
		else{
			getPerm(s, index - 1, result);
			Character c = s.charAt(index - 1);
			List<String> temp = new ArrayList<>();
			for(String string : result){
				temp.addAll(insertCharacter(string, c));
			}
			result.clear();
			result.addAll(temp);
		}
	}
	private static List<String> insertCharacter(String s, Character c){
		List<String> result = new ArrayList<>();
		int len = s.length();
		for(int i = 0; i <= len; i++){
			String first = s.substring(0, i);
			String second = s.substring(i, len);
			result.add(first + c + second);
		}
		return result;
	}
	public static void main(String[] args) {
		List<String> perm = getPerm("abcd");
		for(String s : perm)
			System.out.println(perm);
	}
}public class Question9_5 {
	public static List<String> getPerm(String s){
		List<String> result = new ArrayList<>();
		if(null == s) return result;
		getPerm(s, s.length(), result);
		return result;
	}
	public static void getPerm(String s, int index, List<String> result){
		if(index == 1){
			result.add(s.substring(0, 1));
			return;
		}
		else{
			getPerm(s, index - 1, result);
			Character c = s.charAt(index - 1);
			List<String> temp = new ArrayList<>();
			for(String string : result){
				temp.addAll(insertCharacter(string, c));
			}
			result.clear();
			result.addAll(temp);
		}
	}
	private static List<String> insertCharacter(String s, Character c){
		List<String> result = new ArrayList<>();
		int len = s.length();
		for(int i = 0; i <= len; i++){
			String first = s.substring(0, i);
			String second = s.substring(i, len);
			result.add(first + c + second);
		}
		return result;
	}
	public static void main(String[] args) {
		List<String> perm = getPerm("abcd");
		for(String s : perm)
			System.out.println(perm);
	}
}
```
