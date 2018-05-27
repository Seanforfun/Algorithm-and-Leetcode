package ca.mcmaster.chapter.four.graph;

public abstract class AbstractSearch implements Search {
	protected final Graph g;
	protected final int s;
	public AbstractSearch(Graph g, int s) {
		this.g = g;
		this.s = s;
	}
}
