package ca.mcmaster.chapter.two.Sort;

public class BubbleSort {
	public int[] a = {1, 2,1,4,2,6,234,65,23,5,2,657,2,546}; 
	public int[] sort(){
		for(int i = 0; i < a.length; i++){
			for(int j = i; j < a.length; j++){
				if(a[i] > a[j]){
					int temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
		return a;
	}
	public static void main(String[] args) {
		BubbleSort sort = new BubbleSort();
		int[] result = sort.sort();
		for(int i : result)
			System.out.println(i);
	}
}
