package ca.mcmaster.offer;

public class Question5_1 {
	public static int update(int m, int n, int j, int i){
		int allOnes = ~0;
		int left = allOnes << (j + 1);
		int right = ~(allOnes << i);
		int mask = left | right;
		int clearM = m & mask;
		int movedN = n << i;
		return clearM | movedN;
	}
	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(update(0B10000000000, 0B10011, 6, 2)));
	}
}
