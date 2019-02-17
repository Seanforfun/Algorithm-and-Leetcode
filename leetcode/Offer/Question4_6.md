# Question4_6

#### Solution
```Java
	public static boolean hasChild(TreeNode root, TreeNode node){
		if(root == null) return false;
		if(root == node) return true;
		return hasChild(root.left, node) || hasChild(root.right, node);
	}
	private static TreeNode closestSameNode(TreeNode root, TreeNode p, TreeNode q){
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
	public static TreeNode commonAncester(TreeNode root, TreeNode p, TreeNode q){
		if(!hasChild(root, p) && hasChild(root, q)) return null;	//确定两个节点均在树中。
		return closestSameNode(root, p, q);
	}
```
