# Question1_1

#### Solution
```Java
public class Question1_1 {
	public static boolean isDuplicateString(String str){
		int length = str.length();
		Set<Character> set = new HashSet<Character>();
		for(int i=0; i < length; i++){	//int，Integer等类型变量没有实现Iterable方法，所以不能用forEach进行遍历。
			char c = str.charAt(i);	//是CharAt，不是indexAt
			if(!set.contains(c))
				set.add(c)；	//java中的set添加是add方法添加，remove方法删除
			else
				return false;
		}
		return true;
	}
	public static void main(String[] args) {
		System.out.println(isDuplicateString("12142fsafdhsajkfhldsa"));
	}
}
```