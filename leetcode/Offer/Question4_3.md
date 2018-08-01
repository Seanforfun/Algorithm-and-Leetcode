# Question4_3

#### Solution
* 使用递归插入，每次选中中间的变量作为root
```Java
	public static TreeNode arrToTree(Integer[] arr, int low, int high){
		if(low > high) return null;	//测试终止的条件
		int mid = (high - low) / 2 + low;	//选作root的结点
		TreeNode root = new TreeNode(arr[mid]);
		root.left = arrToTree(arr, low, mid - 1);	//递归获得左右子结点。
		root.right = arrToTree(arr, mid + 1, high);
		return root;
	}
	public class Question4_3 {
	public static void main(String[] args) {
		Integer[] arr = new Integer[100];
		for(int i = 0; i < 100; i++)
			arr[i] = i;
		TreeNode root = OfferTree.arrToTree(arr, 0, arr.length - 1);
		System.out.println(root.v);
		System.out.println(root.left.v);
		System.out.println(root.right.v);
		System.out.println(root.left.left.v);
	}
}
```
