# Undirected Graph无向图
>无向图由边(edge)和顶点(vertex)组成。
>当两个顶点通过一条边相连时我们称这两个顶点相邻。同时这条边依附于这两个顶点
>某个顶点的度数为依附它的边的条数。

* 连通图
>如果从任意一个顶点都存在一条路径到达一个任意顶点，我们称这幅图是连通图

## 图的接口
```Java
public interface Graph {
	/**
	 * @Description: Get the vertex number.
	 * @return
	 */
	public int V();
	/**
	 * @Description: Get the edge number.
	 * @return
	 */
	public int E();
	/**
	 * @Description: Create an edge between w and v.
	 * @param v
	 * @param w
	 */
	public void addEdge(int v, int w);
	/**
	 * @Description: Get all vertex adjacent to v.
	 * @param v
	 * @return
	 */
	public Iterable<Integer> adj(int v);
	/**
	 * @Description: Return degree of given vertex.
	 * @param G
	 * @param V
	 * @return
	 */
	static Integer degree(Graph G, int V){
		Integer degree = new Integer(0);
		for(int w : G.adj(V)) degree++;
		return degree;
	}
	/**
	 * @Description: Find the largest degree in the graph
	 * @param G
	 * @param V
	 * @return
	 */
	static int maxDegree(Graph G, int V){
		int max = 0;
		for(int w : G.adj(V) )
			if(w > max)
				max = w;
		return max;
	}
	/**
	 * @Description: Calculate the average degree for all vertex.
	 * @param G
	 * @return
	 */
	static double avgDegree(Graph G){
		return 2 * G.E() / G.V();
	}
		/**
	 * @Description: Get the number of selt loop.
	 * @param G
	 * @param V
	 * @return
	 */
	static int numOfSelfLoop(Graph G, int V){
		int num = 0;
		int vNum = G.V();
		for(int v = 0; v < vNum; v++ )
			for(int w : G.adj(v))
				if(w == v)
					num ++;
		return num/2;	//w,v and v,w will both be counted.
	}
		/**
	 * @Description: Print a graph.
	 */
	default void display(){
		int vertexNum = this.V();
		for(int v = 0; v < vertexNum; v++){
			StringBuilder sb = new StringBuilder(v + " -> ");
			for(int w : adj(v))
				sb.append(w + "");
			System.out.println(sb.toString());
	}
}
```
### 无向图的实现
```Java
public class UndirectedGraph implements Graph {
	private final int V; //vertex
	private int E;	//edge
	private Bag<Integer>[] adj;	//adjacency table.
	@SuppressWarnings("unchecked")
	public UndirectedGraph(int V) {
		this.V = V;
		adj =  new ListBag[V];
		for(int v = 0; v < V; v++)
			adj[v] = new ListBag<>();
	}
	@SuppressWarnings("unchecked")
	public UndirectedGraph(FileInputStream in){	//Read graph from file.
		Scanner scanner = new Scanner(in);
		this.V = scanner.nextInt();
		adj =  new ListBag[V];
		for(int v = 0; v < V; v++)
			adj[v] = new ListBag<>();
		int E = scanner.nextInt();
		for(int e = 0; e < E; e++){
			int v = scanner.nextInt();
			int w = scanner.nextInt();
			addEdge(v, w);
		}
		scanner.close();
	}
	@Override
	public int V() { return V; }
	@Override
	public int E() { return 0; }
	@Override
	public void addEdge(int v, int w) {	//Add a connection between v and w.
		adj[v].add(w);
		adj[w].add(v);
		this.E++;
	}

	@Override
	public Iterable<Integer> adj(int v) {	//Return a list that the certex connected to.
		return adj[v];
	}
}
```

## 图的API
### 接口
```Java
public interface Search {
	/**
	 * @Description: If s and v are connected.
	 * @param v
	 * @return
	 */
	public boolean mark(int v);
	/**
	 * @Description: Number of vertex connected to source.
	 * @return
	 */
	public int count();
}
```

### 抽象类
* 之所以使用抽象类是为了减少重复定义图的构造器方法，而构造器是无法在接口中通过default实现的。
```Java
public abstract class AbstractSearch implements Search {
	protected final Graph g;
	protected final int s;	//source点
	public AbstractSearch(Graph g, int s) {
		this.g = g;
		this.s = s;
	}
}
```

#### 通过加权并查集实现的Seach类： UFSearch
```Java
public class UFSearch extends AbstractSearch {
	private final UF uf;
	public UFSearch(Graph g, int s) {
		super(g, s);
		this.uf = new UF(g.V());
		//Insert connections into the UF.
		for(int v = 0; v < g.V(); v++){
			for(int w : g.adj(v)){
				if(uf.connected(v, w))	continue;
				else	uf.union(v, w);
			}
		}
	}
	@Override
	public boolean mark(int v) {
		return uf.connected(super.s, v);
	}
	@Override
	public int count() {
		return uf.size[super.s];
	}
	private final class UF{	//定义了一个内部final私有类UF，其为加权并查集的实现。
		private final int N;
		private final int[] a;	//并查集数组
		private final int[] size;	//当前结点的子结点的数量。
		public UF(int N){
			this.N = N;
			a = new int[N];
			for(int i = 0; i < N; i++)	a[i] = i;
			size = new int[N];
			for(int i = 0; i < N; i++)	size[i] = 1;
		}
		public int find(int v){
			if(a[v] == v)	return v;
			else	return find(a[v]);
		}
		public void union(int p, int q){
			int qRoot = find(q);	//分别找到p和q的根路径
			int pRoot = find(p);
			if(pRoot == qRoot) return;
			if(size[qRoot] < size[pRoot]){	//比较子树数量，连接到更大的树上以减小树得深度
				a[qRoot] = pRoot;
				size[pRoot] += size[qRoot];
			}else{	//size[qRoot] >= size[pRoot]
				a[pRoot] = qRoot;
				size[qRoot] += size[pRoot];
			}
		}
		public boolean connected(int p, int q){
			return find(q) == find(p);
		}
	}

}
```