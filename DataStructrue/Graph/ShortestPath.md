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
		try{
			s = new Scanner(in);
			this.V = s.nextInt();
			this.adj = (Bag<DirectedEdge>[])new Bag[V];
			for (int i = 0; i < V; i++)
				adj[i] = new ListBag<DirectedEdge>(); 
			E = s.nextInt();
			for(int i = 0; i < E; i ++){
				int v = s.nextInt();
				int w = s.nextInt();
				double weight = s.nextDouble();
				addEdge(v, w, weight);
			}
		}finally{
			s.close();
		}
	}
	public int V() {	return this.V;	}
	public int E() {	return this.E;	}
	public void addEdge(int v, int w, double weight) {
		this.adj[v].add(new DirectedEdge(v, w, weight));
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
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/spt/tinyEWD.txt"));
		EdgeWeightedDigraph g = new EdgeWeightedDigraph(is);
		g.display();
	}
}
```

#### Shortest Path最短路径
```Java
public interface SP {
	/**
	 * @Description: The distance between w and v, if not connected, dist is infinity.
	 * @param v
	 * @return
	 */
	public double distTo(int v);
	/**
	 * @Description: If there is a path from s to v.
	 * @param v
	 * @return
	 */
	public boolean hasPathTo(int v);
	/**
	 * @Description: Path from s to v.
	 * @param v
	 * @return
	 */
	public Iterable<DirectedEdge> pathTo(int v);
}
```

### Dijkstra算法计算最短路径。
> 放松边v到w意味着检查从s到w的最短路径是否先从s到v，再由v到w。
```Java
public class DijkstraSP implements SP {
	private DirectedEdge[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq;	//优先级队列，存储了到每个顶点的dist
	public DijkstraSP(EdgeWeightedDigraph g, int s) {
		edgeTo = new DirectedEdge[g.V()];
		distTo = new double[g.V()];
		pq = new IndexMinPQ<>(g.V());
		for(int v = 0; v < g.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;	//在还没有开始时存入最大值，如果最后仍然是最大值则说明没有连通性。
		distTo[s] = 0D;
		pq.insert(s, 0D);
		while(!pq.isEmpty())
			relax(g, pq.delMin());
	}
	@Override
	public double distTo(int v) {
		return distTo[v];
	}
	@Override
	public boolean hasPathTo(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	@Override
	public Iterable<DirectedEdge> pathTo(int v) {
		if(!hasPathTo(v)) return null;
		Stack<DirectedEdge> stack = new Stack<>();
		while(edgeTo[v] != null){
			DirectedEdge e = edgeTo[v];
			stack.push(e);
			v = e.from();
		}
		return stack;
	}
	/**
	 * @Description: Relax the edge e.
	 * @param e
	 */
	@SuppressWarnings("unused")
	private void relax(DirectedEdge e){
		int v = e.from(); int w = e.to();
		if(distTo(w) > distTo(v) + e.weight()){
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
		}
	}
	/**
	 * @Description: G is digraph and v is a vertex.
	 * @param g
	 * @param v
	 */
	private void relax(EdgeWeightedDigraph g, int v){	//顶点的松弛。
		for(DirectedEdge e:g.adj(v)){	//对于某一个顶点，从它指出的所有的有向边。
			int w = e.to();		//e is from v to w，与v相邻的所有边
			if(distTo[w] > distTo[v] + e.weight()){	//如果从s到w的距离大于从s到v加上v-w的距离，则进行更新。
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
				if(pq.contains(w)) pq.changeKey(w, distTo[w]);	//更新或是添加距离值到优先队列中。
				else pq.insert(w, distTo[w]);
			}
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/spt/tinyEWD.txt"));
		EdgeWeightedDigraph g = new EdgeWeightedDigraph(is);
		DijkstraSP dijkstraSP = new DijkstraSP(g, 0);
		Stack<DirectedEdge> pathTo = (Stack<DirectedEdge>) dijkstraSP.pathTo(6);
		StringBuilder sb = new StringBuilder();
		DirectedEdge edge = pathTo.pop();
		sb.append(edge.from() + "->");
		sb.append(edge.to() + "->");
		while(!pathTo.empty()){
			sb.append(pathTo.pop().to() + "->");
		}
		System.out.println(sb.toString());
	}
}
```