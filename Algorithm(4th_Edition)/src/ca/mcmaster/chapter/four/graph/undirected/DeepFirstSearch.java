package ca.mcmaster.chapter.four.graph.undirected;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcmaster.chapter.four.graph.Graph;

public class DeepFirstSearch extends AbstractSearch {
	private boolean[] marked;
	private int count;
	public DeepFirstSearch(Graph g, int s) {
		super(g, s);
		marked = new boolean[g.V()];
		dfs(g, s);
	}	
	private void dfs(Graph g, int v){
		marked[v] = true;	//It means current vertex has been accessed.
		count++;	//update the number of vertex connected to s.
		for(int w : g.adj(v))
			if(!marked[w]) dfs(g, w);	//Check all point connected to v, if not accessed, access recursively.
	}
	@Override
	public boolean mark(int v) {
		return marked[v];
	}
	@Override
	public int count() {
		return this.count;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyG.txt")));
		Search search = new DeepFirstSearch(g, 12);
		System.out.println(search.mark(9));
		System.out.println(search.count());
		g.display();
	}
}
