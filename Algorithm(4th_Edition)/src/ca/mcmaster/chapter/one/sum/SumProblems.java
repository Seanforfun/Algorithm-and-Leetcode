package ca.mcmaster.chapter.one.sum;

import java.util.Arrays;

public class SumProblems {
	public static int twoSumFast(Integer[] a){
		Arrays.sort(a);
		Integer length = a.length;
		int cnt = 0;
		for(int i = 0; i < length; i++){
			if(Arrays.binarySearch(a, -a[i]) > i){
				cnt++;				
			}
		}
		return cnt;
	}
	
	public static int threeSumFast(Integer[] a){
		int cnt = 0;
		Arrays.sort(a);
		Integer n = a.length;
		for(int i = 0; i < n; i++){
			for (int j = i+1; j < n; j++) {
				if(Arrays.binarySearch(a, -a[i]-a[j]) > j){
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	public static void main(String[] args) {
		Integer[] a = new Integer[]{-3,2,4,-2,5,-2,-2};
		System.out.println(threeSumFast(a));
	}
}
