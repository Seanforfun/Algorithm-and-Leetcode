package ca.mcmaster.chapter.four.graph.directed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcmaster.chapter.one.bag.Bag;
import ca.mcmaster.chapter.one.bag.ListBag;

public class DirectedDFS {
	private final boolean[] marked;
	public DirectedDFS(Digraph g, int s){
		this.marked = new boolean[g.V()];
		dfs(g, s);
	}
	public DirectedDFS(Digraph g, Iterable<Integer> sources){
		marked = new boolean[g.V()];
		for(int s : sources)
			if(!marked[s]) dfs(g, s);
	}
	private void dfs(Digraph g, int s) {
		marked[s] = true;
		for(int w : g.adj(s))
			if(!marked[w])	dfs(g, w);
	}
	public boolean mark(int v){
		return marked[v];
	}
	public static void main(String[] args) throws FileNotFoundException {
		Digraph g = new DigraphImpl(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyDG.txt")));
		Bag<Integer> bag = new ListBag<Integer>();
		bag.add(1);
		bag.add(2);
		bag.add(6);
		DirectedDFS d = new DirectedDFS(g, bag);
		StringBuilder sb = new StringBuilder();
		for(int v = 0; v < g.V(); v++){
			if(d.mark(v)) sb.append(v + " ");
		}
		System.out.println(sb.toString());
	}
}
