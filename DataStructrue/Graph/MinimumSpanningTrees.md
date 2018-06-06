# Minimum Spanning trees最小生成树

### 加权图
>每条边都给了一个权重的无向图。

### 最小生成树
>给定一幅加权无向图，找到它的一颗最小生成树。

#### 约定
1. 只考虑连通图。（非联通图无法只生成一棵树。）
2. 边的权重不一定表示距离。（实际上所画出的图形是一种抽象且不唯一的图，顶点之间实际上是没有距离这个概念的）
3. 边的权重可能是0或是负数。
4. 所有边的权重都各不相同。（权重相同会造成生成的最小树不唯一）

#### 树的性质
1. 用一条边连接树中的任意的两个顶点都会产生一个环。
2. 从树中删去一条边会得到两棵独立的树。

#### 切分定理
> 将图的所有顶点分成两个非空且不重合的两个集合，横切边是一条连接两个属于不同集合的顶点的边。
* 在一幅加权图中，给定任意的切分，它的横切边中的权重最小者必然属于图的最小生成树。

### [贪心算法 Greedy Algorithm](https://www.cnblogs.com/MrSaver/p/8641971.html)
* 贪心算法是使所做的选择看起来都是当前最佳的，期望通过所做的局部最优选择来产生一个全局最优解。

* 贪心算法设计步骤
	1. 将优化问题转换成这样一个问题，即先做出选择，再解决剩下的一个子问题。
	2. 证明原问题总是有一个最优解是贪心选择的得到的，从而说明贪心选择的安全。
	3. 说明在做出贪心选择后，剩下的子问题具有这样一个性质。即如果将子问题的最优解和我们所做的贪心选择联合起来，可以得到一个更加负责的动态规划解。

* 使用贪心算法找到最小树
	* 使用切分定理找到最小生成树的一边，不断重复直到找到最小生成树的所有边。
		* 一幅有V个顶点的图，初始状态下所有变均为灰色，找到一种切分，它产生的横切边均不为黑色。将他权重最小的横切边标记为黑色，反复，直到标记了V-1条黑边为止。

### Edge对象
```Java
public class Edge implements Comparable<Edge> {
	private final int v;	//因为无向图，v和w分别表示一条无向边的两个顶点
	private final int w;
	private final double weight;	//无向边的权重
	public Edge(int v, int w, double weight){
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	@Override
	public int compareTo(Edge o) {	//继承Comparable接口，用于之后比较无向边的权重。
		if(this.weight - o.weight > 0) return 1;
		else if(this.weight == o.weight) return 0;
		else return -1;
	}
	public double weight(){
		return this.weight;
	}
	public int either(){
		return v;
	}
	public int other(int v){
		if(v == this.v) return w;
		else return v;
	}
	@Override
	public String toString() {
		return String.format("%d-%d %.2f", v, w, weight);
	}
}
```