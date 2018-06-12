package ca.mcmaster.chapter.four.graph.spt;

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
