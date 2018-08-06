package ca.mcmaster.offer;

public class Question9_3 {
	public static int getMagic(int[] nums){
		return getMagic(nums, 0, nums.length - 1);
	}
	public static int getMagic(int[] nums, int low, int high){
		int mid = (high - low) / 2 + low;
		int midNum = nums[mid];
		if(mid == midNum)
			return mid;
		if(midNum < mid){
			return getMagic(nums, mid, high);
		}else{
			return getMagic(nums, low, mid);
		}
	}
	public static void main(String[] args) {
		System.out.println(getMagic(new int[]{-10, -5, -1, 1, 2, 3, 4, 7, 9, 12, 13}));
	}
}
