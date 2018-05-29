package ca.mcmaster.chapter.four.graph.directed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcmaster.chapter.four.graph.Path;
import ca.mcmaster.chapter.one.bag.Bag;
import ca.mcmaster.chapter.one.bag.ListBag;

public class DepthFirstPathDirectedGraph implements Path {
	private final DigraphImpl g;
	private int s;
	private final boolean[] marked;	//Used to mark if current vertex has been accessed.
	private final int[] edgeTo;		//Used to save the vertex ahead of current vertex.
	public DepthFirstPathDirectedGraph(DigraphImpl g, int s){
		this.s = s;
		this.g = (DigraphImpl) g;
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		dfs(g, s);
	}
	public DepthFirstPathDirectedGraph(String file, int s) throws FileNotFoundException{
		g = new DigraphImpl(new FileInputStream(new File(file)));
		this.s = s;
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		dfs(g, s);
	}
	@Override
	public boolean hasPathTo(int v) {
		return edgeTo[v] != 0;
	}
	@Override
	public Iterable<Integer> pathTo(int v) {
		Bag<Integer> path = new ListBag<Integer>();
		path.add(v);
		while(edgeTo[v] != s){
			path.add(edgeTo[v]);
			v = edgeTo[v];
		}
		return path;
	}
	private void dfs(DigraphImpl g, int v){
		marked[v] = true;
		for(int w : g.adj(v)){
			if(!marked[w]){
				edgeTo[w] = v;
				dfs(g, w);
			}
		}
	}
}
