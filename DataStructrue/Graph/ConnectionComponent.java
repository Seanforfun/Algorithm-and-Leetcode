package ca.mcmaster.chapter.four.graph;

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
