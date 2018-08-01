# Question4_7

#### Solution
```Java
	public static boolean hasChild(TreeNode root, TreeNode node){
		if(root == null) return false;
		if(root == node) return true;
		return hasChild(root.left, node) || hasChild(root.right, node);
	}
	//从根节点往下找，之前的一定都是能找到的，只是在缩小范围。
	public static TreeNode closestSameNode(TreeNode root, TreeNode p, TreeNode q){
		if(root == null) return null;
		boolean findp = hasChild(root, p);
		boolean findq = hasChild(root, q);
		if(findp && findq){
			TreeNode resultLeft = closestSameNode(root.left, p, q);
			 TreeNode resultRight = closestSameNode(root.right, p, q);
			 if(resultLeft == null && resultRight == null)
				 return root;
			 else if (resultLeft == null && resultRight != null)
				return resultRight;
			else{
				return resultLeft;
			}
		}else {
			return null;
		}
	}
```
