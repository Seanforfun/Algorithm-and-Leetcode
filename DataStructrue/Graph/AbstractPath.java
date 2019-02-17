package ca.mcmaster.chapter.four.graph;

public abstract class AbstractPath implements Path {
	protected final Graph g;
	protected final int s;
	public AbstractPath(Graph g, int s) {
		super();
		this.g = g;
		this.s = s;
	};
}
