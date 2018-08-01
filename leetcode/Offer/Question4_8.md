# Question4_8

#### Solution
* 使用SubString
```Java
public static void preOrder(TreeNode node, StringBuilder sb){
		if(node == null) return;
		sb.append(node.v);
		preOrder(node.left, sb);
		preOrder(node.right, sb);
	}
	public static boolean isSubTree(TreeNode root, TreeNode node){
		StringBuilder sbRoot = new StringBuilder();
		preOrder(root, sbRoot);
		StringBuilder sbNode = new StringBuilder();
		preOrder(node, sbNode);
		return sbRoot.toString().contains(sbNode.toString());
	}
```

* 传统的递归调用检查
```Java
	public static boolean matchTree(TreeNode node1, TreeNode node2){
		boolean result = true;
		if(node1 == null && node2 == null)	return true;
		if(node1 != node2)
			result = false;
		return result && matchTree(node1.left, node2.left) && 
				matchTree(node1.right, node2.right);
	}
	public static boolean checkSubtree(TreeNode root, TreeNode node){
		if(root == null || node == null) return false;
		boolean mid = matchTree(root, node);
		boolean left = checkSubtree(root.left, node);
		boolean right = checkSubtree(root.right, node);
		return mid || left || right;
	}
```
