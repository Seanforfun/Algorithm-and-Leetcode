# 红黑二叉树 Black Red Tree
>红黑二叉树的效率不如完美平衡二叉树，但是增删改查均能在对数时间内完成。我们通过研究2-3树的性质实现红黑二叉树。

## 2-3树（B-树）
>2-3树是最简单的B-树（或-树）结构，其每个非叶节点都有两个或三个子女，而且所有叶都在统一层上。2-3树不是二叉树，其节点可拥有3个孩子。不过，2-3树与满二叉树相似。若某棵2-3树不包含3-节点，则看上去像满二叉树，其所有内部节点都可有两个孩子，所有的叶子都在同一级别。另一方面，2-3树的一个内部节点确实有3个孩子，故比相同高度的满二叉树的节点更多。高为h的2-3树包含的节点数大于等于高度为h的满二叉树的节点数，即至少有2^h-1个节点。换一个角度分析，包含n的节点的2-3树的高度不大于[log2(n+1)] (即包含n个节点的二叉树的最小高度)。
![2-3Tree](https://i.imgur.com/dutbU8a.png)

## 红黑树Black Red Tree
* 红链接将两个2-结点连接起来构成一个3-结点。
* 黑链接是2-3树中的普通链接。

### 基本单元-结点Node
>就像普通的二叉树，一个结点有左右两个子结点，但是在红黑二叉树中，我们额外添加了一个布尔值用于表示连接到这个结点的链接是红色还是黑色。
```Java
public class BlackRedTree<K extends Comparable<K>, V > {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private class Node{
		K k;
		V v;
		int N;
		Node left, right;
		boolean color;
		public Node(K k, V v, int n, boolean color) {
			super();
			this.k = k;
			this.v = v;
			N = n;
			this.color = color;
		}
	}
	private Boolean isRed(Node n){
		if(null == n) return BLACK;
		return n.color;
	}
}
```

### 操作
#### 旋转
* 单次左旋
```Java
	/**
	 * @Description: 单次左旋
	 * @param n
	 * @return
	 */
	private Node rotateLeft(Node n){
		Node x = n.right;
		n.right = x.left;
		x.left = n;
		x.color = n.color;
		n.color = RED;
		x.N = n.N;
		n.N = size(n.left) + size(n.right) + 1;
		return x;
	}
```

* 单次右旋
```Java
	/**
	 * @Description: 单次右旋
	 * @param n
	 * @return
	 */
	private Node rotateRight(Node n){
		Node x = n.left;
		n.left = x.right;
		x.right = n;
		x.color = n.color;
		n.color = RED;
		x.N = n.N;
		n.N = size(n.left) + size(n.right) + 1;
		return x;
	}
```

* 颜色翻转
```Java
	/**
	 * @Description: 当两个子链接的颜色均为红色时，将子链接的颜色改为黑色将父链接的颜色从黑色换成红色。
	 * @param n
	 */
	private void flipColor(Node n){
		n.color = RED;
		n.left.color = BLACK;
		n.right.color = BLACK;
	}
```

#### 插入
```Java
	public void insert(K k, V v){
		root = insert(root, k, v);
	}
	private Node insert(Node h, K k, V v){
		if(null == h)	return new Node(k, v, 1, RED);	//插入的过程和二叉树完全一致
		int cmp = k.compareTo(h.k);
		if(cmp < 0)
			h.left = insert(h.left, k, v);
		else if(cmp > 0)
			h.right = insert(h.right, k, v);
		else
			h.v = v;
		if(isRed(h.right) && !isRed(h.left))		h = rotateLeft(h);
		if(isRed(h.left) && isRed(h.left.left))		h = rotateRight(h);
		if(isRed(h.left) && isRed(h.right))		flipColor(h);
		h.N = size(h.left) + size(h.right) + 1;
		return h;
	}
```

* 当一个结点的左子链接为黑色，右子链接为红色时，向左旋转。
* 当一个结点的左子链接为红色，左孙链接为红色（两条连续的红色链接）， 向右旋转。
* 当一个结点左右子链接均为红色，将红链接向上传递。

