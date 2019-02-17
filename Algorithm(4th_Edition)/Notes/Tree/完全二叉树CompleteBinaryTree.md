# 完全二叉树 Complete Binary Tree
* **堆**就是通过完全二叉树实现的，可以参考排序部分的堆排序。
>当一一棵二叉树的每个结点都大于等于它的两个子结点时，被称为**堆有序**。

>完全二叉树是效率很高的数据结构，堆是一种完全二叉树或者近似完全二叉树，所以效率极高，像十分常用的排序算法、Dijkstra算法、Prim算法等都要用堆才能优化，几乎每次都要考到的二叉排序树的效率也要借助平衡性来提高，而平衡性基于完全二叉树。

## 判断完全二叉树
>若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。

![CompleteBinaryTree](https://i.imgur.com/YzCQuXP.jpg)
* 对于第n行，最多有2^(n-1)个元素。

## 二叉堆的实现方式
* 通过数组实现二叉堆
```Java
public class CompleteBinaryTree<K extends Comparable<K>> {
	private Object[] arr;	//因为无法实现泛型数组，我们通过Object数组替代。第一个位置不存储元素
	private int N;		//树中元素的个数。
	public CompleteBinaryTree(int N) {
		arr = new Object[N];
	}
	private void swap(int i, int j){	//交换两个元素的位置
		Object temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	@SuppressWarnings("unchecked")
	private boolean less(Integer i, Integer j){
		return ((K)arr[i]).compareTo(((K)arr[j])) < 0;
	}
	//上浮方法
	private void swin(int k){
		//当前节点不是第一个元素并且大于它的父结点，则进行位置的交换。
		while(k > 1 && less(k/2, k)){
			swap(k/2, k);
			k /= 2;
		}
	}
	public Integer size(){
		return N;
	}
	@SuppressWarnings("unchecked")
	public K get(int i){
		return (K)arr[i];
	}
	private void sink(int i){
		while(i * 2 <= N){
			int j = i * 2;
			if(j + 1 < N && less(j, j+1)) j++;		//取两个子结点中更大的一个。
			swap(i, j);
			i = j;		//在当前子树中继续进行下沉
		}
	}
}
```

## 完全二叉树的增删改查
* 增
```Java
	public void insert(K k){
		arr[++N] = k;
		swin(N);
	}
```

* 删
```Java
	public K delMax(){
		K max = (K)arr[1];
		swap(1, N + 1);
		arr[N + 1] = null;
		sink(1);
		N--;
		return max;
	}
```
