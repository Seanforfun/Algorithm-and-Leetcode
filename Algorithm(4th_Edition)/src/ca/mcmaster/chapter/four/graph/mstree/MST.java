package ca.mcmaster.chapter.four.graph.mstree;

public interface MST {
	/**
	 * @Description: Find the edges on the minimum spanning tree.
	 * @return
	 */
	public Iterable<Edge> edges();
	/**
	 * @Description: Return the total weight of the tree.
	 * @return
	 */
	public double weight();
}
