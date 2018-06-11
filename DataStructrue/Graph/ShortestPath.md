# Shortest Path
> 找到从一个顶点到达另一个顶点的成本的最小路径。
> 在一幅加权有向图中，从顶点s到顶点t的最短路径是从所有从s到t的路径中最小权重者。

### 最短路径的性质
1. 路径是有向的。最短路径需要考虑到各边的方向。
2. 权重不一定等价距离，和最小树相似，可以理解为成本的最小值。
3. 并不是所有顶点都是可达的。为了简化问题，我们的样图都是强连通的（从每个顶点到另一个顶点都是可达的）。
4. 负权重会使问题更复杂，我们假设边的权重都是正的。
5. 最短路径一般都是简单的。忽略构成环的零权重边。
6. 最短路径不一定是唯一的。
7. 可能存在平行边或自环。

### 最短路径树（Shortest Path Tree）
> 给定一幅加权有向图和一个顶点s，以s为起点的一棵最短路径树是图的一幅子图，它包含s和从s可到达的所有顶点。这棵树的根节点为s，树的每条路径都是有向图中的一条最短路径。

#### Directed Edge 有向边对象
```Java
public class DirectedEdge {
	private final int v; //边的起始点
	private final  int w; //边的指向
	private final double weight; //有向边的权重
	public DirectedEdge(int v, int w, double weight){
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	public double weight(){	return weight;	}
	public int from(){	return this.v;	}
	public int to(){	return this.w;	}
	@Override
	public String toString() {
		return String.format("%d -> %d %.2f", v, w, weight);
	}
}
```

#### EdgeWeightDigraph 加权有向图
```Java
public class EdgeWeightedDigraph {
	private final int V; //Number of vertex.
	private int E; //Number of edges.
	private Bag<DirectedEdge>[] adj;	//Adjacency list
	@SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(int V){
		this.V = V;
		this.E = 0;
		adj = (Bag<DirectedEdge>[])new Bag[V];
		for(int i = 0; i < V; i++)
			adj[i] = new ListBag<>();
	}
	@SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(FileInputStream in) {
		Scanner s = null;
		s = new Scanner(in);
		this.V = s.nextInt();
		this.E = s.nextInt();
		adj = (Bag<DirectedEdge>[])new Bag[V];
		for (int i = 0; i < V; i++)
			adj[i] = new ListBag<DirectedEdge>(); 
		for(int i = 0; i < E; i ++){
			int v = s.nextInt();
			int w = s.nextInt();
			double weight = s.nextDouble();
			addEdge(v, w, weight);
		}
		s.close();
	}
	public int V() {	return this.V;	}
	public int E() {	return this.E;	}
	public void addEdge(int v, int w, double weight) {
		adj[v].add(new DirectedEdge(v, w, weight));
		this.E ++;
	}
	public Iterable<DirectedEdge> adj(int v) {
		return adj[v];
	}
	public void display() {
		for(int i = 0; i < V; i++){
			StringBuilder sb = new StringBuilder(i + ": ");
			for(DirectedEdge e : adj[i]){
				sb.append(e.to() + "|");
			}
			System.out.println(sb.toString());
		}
	}
}
```