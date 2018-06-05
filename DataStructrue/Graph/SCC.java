package ca.mcmaster.chapter.four.graph.directed;

public interface SCC {
	/**
	 * @Description: Check if v and w are connected.
	 * @param v
	 * @param w
	 * @return
	 */
	public boolean strongConnected(int v, int w);
	/**
	 * @Description: The number of strong connected component.
	 * @return
	 */
	public int count();
	/**
	 * @Description: Which of the strong component belongs to.
	 * @param v
	 * @return
	 */
	int id(int v);
}
