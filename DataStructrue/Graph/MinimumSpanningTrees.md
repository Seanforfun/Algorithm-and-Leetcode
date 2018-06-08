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
		else return this.v;
	}
	@Override
	public String toString() {
		return String.format("%d-%d %.2f", v, w, weight);
	}
}
```

### 加权无向图
```Java
public class EdgeWeightedGraph {
	private final int V;	//Number of vertex
	private int E; //Number of edge
	private final Bag<Edge>[] adj; //Create bag for saving edges of vertex
	@SuppressWarnings("unchecked")
	public EdgeWeightedGraph(int V){
		this.V = V;
		this.E = 0;
		adj = new Bag[V];
		for(int v = 0; v < V ; v++)	adj[v] = new ListBag<>();
	}
	@SuppressWarnings("unchecked")
	public EdgeWeightedGraph(FileInputStream in){
		Scanner scanner = null;
		try {
			scanner = new Scanner(in);
			V = scanner.nextInt();
			E = 0;
			int edgeNum = scanner.nextInt();
			adj = new Bag[V];
			for(int v = 0; v < V ; v++)	adj[v] = new ListBag<>();
			for(int e = 0; e < edgeNum; e++)	addEdge(new Edge(scanner.nextInt(), scanner.nextInt(), scanner.nextDouble()));
		} finally{
			scanner.close();
		}
	}
	private void addEdge(Edge e){
		int v = e.either();
		int w = e.other(v);
		adj[v].add(e);
		adj[w].add(e);
		E++;
	}
	public int V(){
		return this.V;
	}
	public int E(){
		return this.E;
	}
	public Iterable<Edge> adj(int v){
		return adj[v];
	}
	public Iterable<Edge> edges(){
		List<Edge> res = new ArrayList<Edge>();
		for(int v = 0; v < V; v++)
			for(Edge e : adj[v])
				if(v > e.other(v)) res.add(e);	//每条边均会被遍历两次，通过一次判断避免重复性。
		return res;
	}
}
```

### 生成最小树
* 最小生成树接口
```Java
public interface MST {
	/**
	 * @Description: Find the edges on the minimum spanning tree.
	 * @return
	 */
	public Iterable<Edge> edges();
	/**
	 * @Description: Return the total weight of the tree.
	 * @return
	 */
	public double weight();
}
```

#### Prim算法
![Prim轨迹](https://i.imgur.com/mURZUQo.jpg)

* Prim实现类
	* LazyPrim
```Java
public class LazyPrimMST implements MST {
	private final EdgeWeightedGraph g;
	private PriorityQueue<Edge> pq;
	private Queue<Edge> mst;	//minimum spanning tree
	private final boolean[] marked;	//If current vertex is in the tree
	public LazyPrimMST(EdgeWeightedGraph g){
		this.g = g;
		marked = new boolean[g.V()];
		pq = new PriorityQueue<>();
		mst = new LinkedList<Edge>();
		visit(g, 0);
		while(!pq.isEmpty()){
			Edge edge = pq.poll();	//get the smallest edge from the graph
			int v = edge.either(); int w = edge.other(v);
			if(marked[v] && marked[w]) continue;	//两个顶点都已经在最小树中，说明这些边已经失效了。
			mst.offer(edge);
			if(!marked[v]) visit(g, v);
			if(!marked[w]) visit(g, w);
		}
	}
	@Override
	public Iterable<Edge> edges() {
		return mst;
	}
	@Override
	public double weight() {
		double res = 0d;
		for(Edge e : mst)
			res += e.weight();
		return res;
	}
	private void visit(EdgeWeightedGraph g, int v){	//访问图上的某个顶点，将这个顶点加入最小生成树中，并将这个顶点的邻接边加入优先队列，如果两个顶点都在树中，就不用加入了。
		marked[v] = true;
		for(Edge e : g.adj(v))
			if(!marked[e.other(v)]) pq.add(e);
	}
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/mstree/mediumEWG.txt"));
		EdgeWeightedGraph graph = new EdgeWeightedGraph(is);
		LazyPrimMST mst = new LazyPrimMST(graph);
		Iterable<Edge> edges = mst.edges();
		for(Edge e : edges)
			System.out.println(e.toString());
		System.out.println("Weight: " + mst.weight());
	}
}
```
	* 即时Prim
	![即时Prim](https://i.imgur.com/2WX62dl.jpg)
```Java
public class PrimMst implements MST {
	private Edge[] edgeTo;
	private double[] distTo;
	private boolean[] marked;
	private IndexMinPQ<Double> pq;
	public PrimMst(EdgeWeightedGraph g) {
		edgeTo = new Edge[g.V()];
		distTo = new double[g.V()];
		marked = new boolean[g.V()];
		for(int i = 0; i < g.V(); i++){
			distTo[i] = Double.POSITIVE_INFINITY;
		}
		pq = new IndexMinPQ<Double>(g.V());
		distTo[0] = 0.0d;
		while(!pq.isEmpty())	//一定会访问到所有的顶点
			visit(g, pq.delMin());	//将所有的有效横切边从队列中读取出来。
	}
	private void visit(EdgeWeightedGraph g, int v){	//当前的顶点不在最小树中
		marked[v] = true;
		for(Edge e : g.adj(v)){	//遍历连接这个顶点的所有边
			int w = e.other(v);		//找到这些边中除了v的另一个顶点
			if(marked[w]) continue;	//v-w已经在树中，失效
			if(e.weight() < distTo[w]){	//判断是不是可以替代
				edgeTo[w] = e;
				distTo[w] = e.weight();
				if(pq.contains(w)) pq.change(w, distTo[w]);
				else pq.insert(w, distTo[w]);
			}
		}
	}
}
```