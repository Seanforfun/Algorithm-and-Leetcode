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