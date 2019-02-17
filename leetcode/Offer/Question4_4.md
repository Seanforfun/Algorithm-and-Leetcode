# Question4_4

#### Solution
* 仍然使用递归的方法
```Java
	public static void createLevelList(TreeNode root,	//这是一种DFS List<LinkedList<TreeNode>> list, int level){
		if(root == null) return;
		if(list.size() <= level)	//不能使用list.get(level)来判断null,会出现boundary Error.
			list.add(new LinkedList<>());
		List<TreeNode> levelList = list.get(level);
		levelList.add(root);
		createLevelList(root.left, list, level + 1);
		createLevelList(root.right, list, level + 1);
	}
	public static List<LinkedList<TreeNode>> createLevelList(TreeNode root){	//该方法使用了BFS.
		List<LinkedList<TreeNode>> lists = new ArrayList<>();
		LinkedList<TreeNode> rootLevel = new LinkedList<>();
		lists.add(rootLevel);
		rootLevel.add(root);
		Queue<LinkedList<TreeNode>> queue = new Queue<>();
		queue.enqueue(rootLevel);
		while(!queue.isEmpty()){
			LinkedList<TreeNode> list = queue.dequeue();
			LinkedList<TreeNode> newList = null;
			if(list.size() != 0){
				newList = new LinkedList<>();
				lists.add(newList);
				queue.enqueue(newList);
				for(TreeNode node : list){
					if(node.left != null)
						newList.add(node.left);
					if(node.right != null)
						newList.add(node.right);
				}
			}
		}
		return lists;
	}
	public static void main(String[] args) {
		Integer[] arr = new Integer[100];
		for(int i = 0; i < 100; i++)
			arr[i] = i;
		TreeNode root = OfferTree.arrToTree(arr, 0, arr.length - 1);
		List<LinkedList<TreeNode>> lists =  new ArrayList<LinkedList<TreeNode>>();
		OfferTree.createLevelList(root, lists, 0);
		for(LinkedList<TreeNode> list : lists){
			for(TreeNode n : list){
				System.out.print(n.v + "	");
			}
			System.out.println();
		}
	}
```
