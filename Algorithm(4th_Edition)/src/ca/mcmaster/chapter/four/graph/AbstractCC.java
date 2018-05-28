package ca.mcmaster.chapter.four.graph;

public abstract class AbstractCC implements ConnectionComponent {
	protected final Graph g;
	public AbstractCC(Graph g){
		this.g = g;
	}
}
