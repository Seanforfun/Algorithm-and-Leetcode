package ca.mcmaster.offer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
