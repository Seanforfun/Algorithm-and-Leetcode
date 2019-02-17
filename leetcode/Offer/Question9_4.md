# Question9_4

#### Solution
* {1， 2， 3}
	* n = 0 {}:
		* {}
	* n = 1 {1}:
		* {}, {1}
	* n = 2 {1, 2}
		* {}, {1}, {2}, {1, 2}
	* n = 3 {1, 2, 3}
		* {}, {1}, {2}, {3}, {1, 2}, {1, 3}, {2, 3}, {1, 2, 3}
* 根据分析发现，每一个n+1都包含了n的所有元素，并且增加了新增的元素是原先的元素的集合增加了n。这种当前结果是根据上一个结果的方法最适合使用递归。而且是自底而上的递归。
```Java
public class Question9_4 {
	public static List<ArrayList<Integer>> getSubset(List<Integer> set){
		List<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		getSubset(set, set.size(), result);
		return result;
	}
	public static void getSubset(List<Integer> set, int index, List<ArrayList<Integer>> result){
		if(index < 0) return;
		if(index == 0){
			ArrayList<Integer> single = new ArrayList<>();
			result.add(single);
			return;
		}else{
			getSubset(set, index - 1, result);
			Integer current = set.get(index - 1);
			List<ArrayList<Integer>> more = new ArrayList<ArrayList<Integer>>();
			for(ArrayList<Integer> l : result){
				ArrayList<Integer> list = new ArrayList<>();
				list.add(current);
				for(Integer i : l)
					list.add(i);
				more.add(list);
			}
			result.addAll(more);
		}
	}
	public static void main(String[] args) {
		List<Integer> set = new LinkedList<Integer>();
		for(int i = 0; i < 3; i++){
			set.add(i);
		}
		List<ArrayList<Integer>> subset = getSubset(set);
		for(ArrayList<Integer> list : subset){
			for(Integer i : list)
				System.out.print(i + "  ");
			System.out.println();
		}
	}
}
```
