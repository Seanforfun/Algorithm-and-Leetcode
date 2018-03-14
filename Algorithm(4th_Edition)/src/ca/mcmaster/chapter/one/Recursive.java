package ca.mcmaster.chapter.one;

public class Recursive {
	private static int rank(int key, int[] a){
		return rank(key, a, 0, a.length - 1);
	}
	
	public static int rank(int key, int[] a, int low, int hi){
		System.out.println("low is " + low + "; high is " + hi); 
		if(low > hi){
			return -1;
		}
		int mid = low + (hi - low)/2;
		if(key < a[mid])
			return rank(key, a, low, mid - 1);
		else if(key > a[mid])
			return rank(key, a, mid + 1, hi);
		else
			return mid;
	}
	
	public static void main(String[] args){
		int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		System.out.println(rank(7, a));
	}
}
