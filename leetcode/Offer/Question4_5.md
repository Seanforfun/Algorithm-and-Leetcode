# Question4_5

#### Solution
```Java
public static boolean checkBSTDFS(TreeNode root){
		if(root == null) return true;
		boolean lresult = true;	//此处必须要初始化！不然编译不过！而且必须要初始化成true，不然要加更多的判断条件。
		boolean rresult = true;
		if(root.left != null){
			if(root.left.v > root.v)
				return false;
			lresult = checkBSTDFS(root.left);
		}
		if(root.right != null){
			if(root.right.v <= root.v)
				return false;
			rresult = checkBSTDFS(root.right);
		}
		return lresult && rresult;	//逻辑判断的与&&，同时要注意当使用两个布尔型进行判断时最好初始化成true。
	}
	public static boolean checkBSTBFS(TreeNode root){
		if(root == null) return true;
		Queue<TreeNode> queue = new Queue<>();
		queue.enqueue(root);
		while(!queue.isEmpty()){
			TreeNode node = queue.dequeue();
			if(node.left != null){
				if(node.left.v > node.v)	return false;
				queue.enqueue(node.left);
			}
			if(node.right != null){
				if(node.right.v <= node.v)	return false;
				queue.enqueue(node.right);
			}
		}
		return true;
	}
```
