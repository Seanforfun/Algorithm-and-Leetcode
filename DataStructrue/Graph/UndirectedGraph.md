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

### 通过加权并查集实现的Seach类： UFSearch
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

#### UFSearch测试
```Java
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyG.txt")));
		Search search = new UFSearch(g, 3);
		System.out.println(search.mark(7));
		g.display();
```
* 结果
>false
>0 -> 6 2 1 5
>1 -> 0
>2 -> 0
>3 -> 5 4
>4 -> 5 6 3
>5 -> 3 4 0
>6 -> 0 4
>7 -> 8
>8 -> 7
>9 -> 11 10 12
>10 -> 9
>11 -> 9 12
>12 -> 11 9

### 深度优先查找DFSearch, DFPath
```Java
public class DeepFirstSearch extends AbstractSearch {
	private boolean[] marked;	//A array used to mark if current node is connected to s
	private int count;	//number of vertex connected to s
	public DeepFirstSearch(Graph g, int s) {
		super(g, s);
		marked = new boolean[g.V()];
		dfs(g, s);
	}
	private void dfs(Graph g, int v){
		marked[v] = true;	//It means current vertex has been accessed.
		count++;	//update the number of vertex connected to s.
		for(int w : g.adj(v))
			if(!marked[w]) dfs(g, w);	//Check all point connected to v, if not accessed, access recursively.
	}
	@Override
	public boolean mark(int v) {
		return marked[v];
	}
	@Override
	public int count() {
		return this.count;
	}
}
```

#### 测试
```Java
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyG.txt")));
		Search search = new DeepFirstSearch(g, 12);
		System.out.println(search.mark(9));
		System.out.println(search.count());
		//g.display();
	}
```
* 结果
>true
4

### 寻找路径
* 定义路径的接口函数
```Java
public interface Path {
	/**
	 * @Description: If there is a path from s to v
	 * @param v
	 * @return
	 */
	public boolean hasPathTo(int v);
	/**
	 * @Description: Return the path from s to v if it exists and return null if not existing.
	 * @param v
	 * @return
	 */
	Iterable<Integer> pathTo(int v);
}
```

* 抽象类
>用于定义构造函数。
```Java
public abstract class AbstractPath implements Path {
	protected final Graph g;
	protected final int s;
	public AbstractPath(Graph g, int s) {
		super();
		this.g = g;
		this.s = s;
	};
}
```

* 深度优先查找路径
```Java
public class DepthFirstPath extends AbstractPath {
	private boolean[] marked;	//If current vertex has been accessed.
	private int[] edgeTo;	//Record the path from that point to s.
	public DepthFirstPath(Graph g, int s) {
		super(g, s);
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		dfs(g, s);
	}
	@Override
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	@Override
	public Iterable<Integer> pathTo(int v) {
		if(true == hasPathTo(v)){
			//存入的时候是逆序，读取的时候需要顺序，所以LIFO的队列最为合适，使用栈
			MyStack<Integer> path = new ListStack<>();
			do {
				path.push(v);
				v = edgeTo[v];
			} while (v != s);
			return path;
		}
		return null;
	}
	private void dfs(Graph g, int v){
		marked[v] = true;
		for(int w : g.adj(v)){	//All vertex connected to v
			if(!marked[w]){	//If current vertex has not been accessed, we mark it and save the previous vertex to current vertex.
				edgeTo[w] = v;
				dfs(g, w);
			}
		}
	}
}
```

* 测试
```Java
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyCG.txt")));
		Path p = new DepthFirstPath(g, 0);
		Iterable<Integer> path = p.pathTo(1);
		StringBuilder sb = new StringBuilder("0");
		for(Integer pnode : path){
			sb.append("->" + pnode);
		}
		System.out.println(sb.toString());
	}
```
>0->2->1

### 广度优先搜索
* 广度优先用于寻找最短路径。
* 深度优先（DFP）所寻找到的路径是通过递归调用寻找到路径，递归调用的路径返回是根据adjacency table中的链表的显示顺序。所以返回的值不一定是最短的。
* 广度优先查找定义了相邻的所有顶点，再找到相邻两个，以此类推。
```Java
public class BreadthFirstPath extends AbstractPath{
	private final boolean[] marked;
	private final int[] edgeTo;
	public BreadthFirstPath(Graph g, int s) {
		super(g, s);
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		bfs(g, s);
	}

	@Override
	public boolean hasPathTo(int v) {
		return marked[v];
	}

	@Override
	public Iterable<Integer> pathTo(int v) {
		MyStack<Integer> stack = new ListStack<>();
		do {
			stack.push(v);
			v = edgeTo[v];
		} while (v != s);
		return stack;
	}
	private void bfs(Graph g, int s){
		LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();
		marked[s] = true;
		q.add(s);
		while(!q.isEmpty()){
			Integer v = q.poll();	//从队列中读取下一个顶点并对其遍历相邻顶点。
			for(int w : g.adj(v)){
				if(!marked[w]){	//Current vertex is not accessed.
					q.add(w);	//如果相邻的顶点没有被访问过，就将它加入队列中。
					edgeTo[w] = v;
					marked[w] = true;
				}
			}
		}
	}
}
```

* 测试
```Java
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyCG.txt")));
		Path p = new BreadthFirstPath(g, 0);
		Iterable<Integer> path = p.pathTo(4);
		StringBuilder sb = new StringBuilder("0");
		for(Integer pnode : path){
			sb.append("->" + pnode);
		}
		System.out.println(sb.toString());
	}
```

### 连通分量 Connection Component
>无向图G的极大连通子图称为G的连通分量( Connected Component)。任何连通图的连通分量只有一个，即是其自身，非连通的无向图有多个连通分量。

* 接口
```Java
public interface ConnectionComponent {
	/**
	 * @Description: If v and w are connected.
	 * @param v
	 * @param w
	 * @return
	 */
	public boolean connected(int v, int w);
	/**
	 * @Description: Number of cc in current graph.
	 * @return
	 */
	public int count();
	/**
	 * @Description: Identification of connection component.
	 * @param v
	 * @return
	 */
	public int id(int v);
}
```

* 抽象类
```Java
public abstract class AbstractCC implements ConnectionComponent {
	protected final Graph g;
	public AbstractCC(Graph g){
		this.g = g;
	}
}
```

* 基于DFS的实现
```Java
public class DFSCC extends AbstractCC {
	private boolean[] marked;
	private int[] id;
	private int count;
	public DFSCC(Graph g) {
		super(g);
		marked = new boolean[g.V()];
		id = new int[g.V()];
		for(int v = 0; v < g.V(); v++)	//Traversal all of vertex if not accessed
			if(!marked[v]){	//Current vertex is not accessed.
				dfs(g, v);
				count++;
			}
	}
	@Override
	public boolean connected(int v, int w) {
		return id[w] == id[v];
	}
	@Override
	public int count() {
		return this.count;
	}
	@Override
	public int id(int v) {
		return id[v];
	}
	private void dfs(Graph g, int v){
		marked[v] = true;
		id[v] = count;	//assign current count to this vertex as id.
		for(int w : g.adj(v))
			if(!marked[w])
				dfs(g, w);
	}
}
```

* 测试
```Java
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyG.txt")));
		DFSCC cc = new DFSCC(g);
		int ccNum = cc.count();
		System.out.println("Number of cc: " + ccNum);
		Bag<Integer>[] bag = new ListBag[ccNum];	//Create bag array to save all connection components.
		for(int i = 0; i < ccNum; i++)
			bag[i] = new ListBag<>();
		for(int i = 0; i < g.V(); i++)
			bag[cc.id(i)].add(i);	//Add vertex into bag.
		for(int i = 0; i < ccNum; i++){
			StringBuilder sb = new StringBuilder(i + ": ");
			for(int v : bag[i])
				sb.append(v + " ");
			System.out.println(sb.toString());
		}
	}
```
> Number of cc: 3
> 0: 6 5 4 3 2 1 0
> 1: 8 7
> 2: 12 11 10 9