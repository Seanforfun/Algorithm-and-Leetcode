package ca.mcmaster.chapter.four.graph;

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
