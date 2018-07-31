# Question4_1

#### Solution
* 递归的模式
	* 递归结束的条件
	* 不满足条件，退出的条件
	* 继续递归调用的条件。
```Java
public class Question4_1 {
	private static int getHeight(OfferTree.TreeNode root){
		if(null == root) return 0;
		return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
	}
	public static boolean isBalance(OfferTree.TreeNode root){
		if(null == root)	return true;	//递归结束的条件
		int diff = Math.abs(getHeight(root.left) - getHeight(root.right));
		if(diff > 1)	return false;	//不满足条件，退出的条件
		else
			return isBalance(root.left) && isBalance(root.right);	//继续递归调用的条件。
	}
	public static void main(String[] args) {
		OfferTree tree = new OfferTree();
		OfferTree.TreeNode node = tree.new TreeNode(8, null, null);
		OfferTree.TreeNode node0 = tree.new TreeNode(7, node, null);
		OfferTree.TreeNode node1 = tree.new TreeNode(3, node0, null);
		OfferTree.TreeNode node2 = tree.new TreeNode(4, null, null);
		OfferTree.TreeNode node3 = tree.new TreeNode(5, null, null);
		OfferTree.TreeNode node4 = tree.new TreeNode(6, null, null);
		OfferTree.TreeNode node5 = tree.new TreeNode(1, node1, node2);
		OfferTree.TreeNode node6 = tree.new TreeNode(2, node3, node4);
		OfferTree.TreeNode node7 = tree.new TreeNode(0, node5, node6);
		System.out.println(isBalance(node7));
	}
}
```

* 更高效的方法, 需要细细思考这种方法
```Java
private static int checkHeight(OfferTree.TreeNode root){
		if(null == root) return 0;
		int leftHeight = checkHeight(root.left);
		int rightHeight = checkHeight(root.right);
		if(leftHeight == -1 || rightHeight == -1)	return -1;
		return Math.abs(leftHeight - rightHeight) > 1 ? -1:Math.max(leftHeight, rightHeight) + 1;
	}
	private static boolean isBalance1(OfferTree.TreeNode root){
		if(checkHeight(root) != -1)	return true;
		return false;
	}
```