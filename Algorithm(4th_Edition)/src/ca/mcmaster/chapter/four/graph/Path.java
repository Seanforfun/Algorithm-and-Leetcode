package ca.mcmaster.chapter.four.graph;

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
