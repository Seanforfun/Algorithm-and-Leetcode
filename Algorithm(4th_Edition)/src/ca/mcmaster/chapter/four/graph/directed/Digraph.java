package ca.mcmaster.chapter.four.graph.directed;

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
