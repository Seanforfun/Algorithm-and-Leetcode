package ca.mcmaster.chapter.four.graph.undirected;

import ca.mcmaster.chapter.four.graph.Graph;
import ca.mcmaster.chapter.four.graph.Path;

public abstract class AbstractPath implements Path {
	protected final Graph g;
	protected final int s;
	public AbstractPath(Graph g, int s) {
		super();
		this.g = g;
		this.s = s;
	};
}
