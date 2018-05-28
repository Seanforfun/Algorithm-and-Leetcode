# Directed Graph
>有向图，边是单向的：每条边所连接的两个顶点都是一个有序对，它的邻接性是单向的。
>我们称一条有向边由第一个顶点指出并指向第二个顶点。
>一个顶点的出度(out degree)由该顶点指出的边的总数。
>一个顶点的入度(in degree)为指向该顶点的边的总数。
>一条有向边的第一个顶点称为它的头，第二个顶点称为它的尾。

### 接口
```Java
public interface Digraph {
	/**
	 * @Description: Number of vertex.
	 * @return
	 */
	public int V();
	/**
	 * @Description: Number of edge.
	 * @return
	 */
	public int E();
	/**
	 * @Description: Add an edge between two vertex. v->w.
	 * @param v
	 * @param w
	 */
	public void addEdge(int v, int w);
	/**
	 * @Description: Return vertex point out from v.
	 * @param v
	 * @return
	 */
	public Iterable<Integer> adj(int v);
	/**
	 * @Description: Get the reverse graph of current graph.
	 * @return
	 */
	public Digraph reverse();
	/**
	 * @Description: Print current graph.
	 */
	public void display();
}
```

### 实现类
```Java
public class DigraphImpl implements Digraph {
	private final int V;		//Number of vertex in current digraph
	private int E; 	//Number of edges.
	private ListBag<Integer>[] adj;
	@SuppressWarnings("unchecked")
	public DigraphImpl(int V){
		this.V = V;
		adj = (ListBag<Integer>[])new ListBag[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new ListBag<Integer>(); 
		}
		this.E = 0;
	}
	
	@SuppressWarnings("unchecked")

	public DigraphImpl(FileInputStream in) {
		Scanner s = null;
		s = new Scanner(in);
		this.V = s.nextInt();
		this.E = s.nextInt();
		adj = new ListBag[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new ListBag<Integer>(); 
		}
		for(int i = 0; i < E; i ++){
			int v = s.nextInt();
			int w = s.nextInt();
			adj[v].add(w);
		}
		s.close();
	}
	@Override
	public int V() { return this.V; }
	@Override
	public void addEdge(int v, int w) {	//只增加一条有向边
		adj[v].add(w);
		E++;
	}
	@Override
	public int E() { return E; }
	@Override
	public Iterable<Integer> adj(int v) {
		return adj[v];
	}
	@Override
	public Digraph reverse() {
		DigraphImpl g  = new DigraphImpl(V);
		for(int v = 0; v < V; v++)
			for(int w : adj(v))
				addEdge(w, v);
		return g;
	}
	@Override
	public void display() {
		for(int i = 0; i < V; i++){
			StringBuilder sb = new StringBuilder(i + ": ");
			for(int w : adj[i]){
				sb.append(w + "|");
			}
			System.out.println(sb.toString());
		}
	}
}
```

### 测试
```Java
	public static void main(String[] args) throws IOException {
		FileInputStream in = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyDG.txt"));
		Digraph graph = new DigraphImpl(in);
		in.close();
		graph.display();
	}
```

### 结果
>0: 5|1|
>1:
>2: 0|3|
>3: 5|2|
>4: 3|2|
>5: 4|
>6: 9|4|8|0|
>7: 6|9|
>8: 6|
>9: 11|10|
>10: 12|
>11: 4|12|
>12: 9|
