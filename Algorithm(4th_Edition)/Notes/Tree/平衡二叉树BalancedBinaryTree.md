# 平衡二叉树Balanced Binary Tree
>平衡二叉树（Balanced Binary Tree）是二叉查找树的一个进化体，也是第一个引入平衡概念的二叉树。平衡二叉树要求对于每一个节点来说，它的左右子树的高度之差不能超过1，如果插入或者删除一个节点使得高度之差大于1，就要进行节点之间的旋转，将二叉树重新维持在一个平衡状态。这个方案很好的解决了二叉查找树退化成链表的问题，把插入，查找，删除的时间复杂度最好情况和最坏情况都维持在O(logN)。但是频繁旋转会使插入和删除牺牲掉O(logN)左右的时间，不过相对二叉查找树来说，时间上稳定了很多。

## 定义
>平衡二叉树定义(AVL)：它或者是一颗空树，或者具有以下性质的二叉树：**它的左子树和右子树的深度之差(平衡因子)的绝对值不超过1**，且它的左子树和右子树都是一颗平衡二叉树。

* 旋转
>通过对树进行简单的修复来让其重新恢复到平衡，而这样的简单操作我们就称之为旋转，当然旋转也有单旋转和双旋转之分。
假设结点X是失衡点，它必须重新恢复平衡，由于任意结点的孩子结点最多有两个，而且导致失衡的必要条件是X结点的两棵子树高度差为2(大于1)，因此一般只有以下4种情况可能导致X点失去平衡：
① 在结点X的左孩子结点的左子树中插入元素
② 在结点X的左孩子结点的右子树中插入元素
③ 在结点X的右孩子结点的左子树中插入元素
④ 在结点X的右孩子结点的右子树中插入元素

* 高度
>高度是指当前结点到叶子结点的最长路径，如所有叶子结点的高度都为0。
![height](https://i.imgur.com/dA8OWAs.png)

* 左左单旋转(LL)情景①分析
![LL](https://i.imgur.com/kpMVlUs.png)
```Java
	private AVLNode singleRotateLeft(AVLNode n){
		AVLNode left1 = n.left;
		n.left = left1.right;
		left1.right = n;
		left1.height = Math.max(left1.left.height, left1.right.height) + 1;
		n.height = Math.max(n.left.height, n.right.height) + 1;
		return left1;
	}
```

* 右右单旋转(RR)情景④分析
![RR](https://i.imgur.com/CkFlj4X.png)
```Java
	private AVLNode singleRotateRight(AVLNode n){
		AVLNode right1 = n.right;
		n.left = right1.left;
		right1.left = n;
		right1.height = Math.max(right1.left.height, right1.right.height) + 1;
		n.height = Math.max(n.left.height, n.right.height) + 1;
		return right1;
	}
```

* 平衡二叉树的双旋转算法与实现
* 左右双旋转(LR)情景②分析
![LR](https://i.imgur.com/926XxBS.png)