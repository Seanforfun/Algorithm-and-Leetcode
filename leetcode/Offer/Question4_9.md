# Question4_9

#### Solution
```Java
	public static void getPathList(TreeNode root, LinkedList<PathResult> list, PathResult ps) throws CloneNotSupportedException{
		if(null == root)	return;
		PathResult currentResult = (PathResult) ps.clone();
		currentResult.path.add(root);
		currentResult.count += root.v;
		list.add(currentResult);
		getPathList(root.left, list, currentResult);
		getPathList(root.right, list, currentResult);
	}
	public static void createPathTable(TreeNode root, List<LinkedList<PathResult>> lists) throws CloneNotSupportedException{
		if(root == null) return;
		LinkedList<PathResult> list = new LinkedList<>();
		getPathList(root, list, new PathResult());
		if(list.size() != 0)
			lists.add(list);
		createPathTable(root.left, lists);
		createPathTable(root.right, lists);
	}
	public static List<PathResult> getPathWithCount(TreeNode root, int count) throws CloneNotSupportedException{
		List<PathResult> results = new ArrayList<>();
		List<LinkedList<PathResult>> lists = new ArrayList<>();
		createPathTable(root, lists);
		for(LinkedList<PathResult> list : lists){
			for(PathResult pr : list){
				if(pr.count == count)
					results.add(pr);
			}
		}
		return results;
	}
class PathResult implements Cloneable{
	public LinkedList<TreeNode> path = new LinkedList<>();
	public int count = 0;
	@Override
	protected Object clone() throws CloneNotSupportedException {	//clone是pretexted方法，必须要重写，并且object默认的是浅克隆！！！
		PathResult newPr = new PathResult();
		for(TreeNode node : this.path)
			newPr.path.add(node);
		newPr.count = this.count;
		return newPr;
	}
}
```
