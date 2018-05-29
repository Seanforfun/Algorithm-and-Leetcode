package ca.mcmaster.chapter.four.graph.undirected;

import ca.mcmaster.chapter.four.graph.Graph;

public abstract class AbstractCC implements ConnectionComponent {
	protected final Graph g;
	public AbstractCC(Graph g){
		this.g = g;
	}
}
