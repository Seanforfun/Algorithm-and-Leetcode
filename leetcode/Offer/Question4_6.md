# Question4_6

#### Solution
```Java
	private static TreeNode getMostLeft(TreeNode node){	//递归获得当前节点最左侧的子结点。
		if(null == node) return null;
		if(node.left == null) return node;
		return getMostLeft(node.left);
	}
	public static TreeNode getNextBST(TreeNode node){
		if(node == null) return null;
		if(node.right == null){	//当前节点没有右子结点，则要递归向上获得第一个大于当前节点的结点返回。
			TreeNode current = node.parent;
			while(current != null && current.v < node.v){
				current = current.parent;
			}
			return current;
		}
		return getMostLeft(node.right);
	}
```
