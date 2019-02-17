package ca.mcmaster.chapter.four.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcmaster.chapter.one.stack.ListStack;
import ca.mcmaster.chapter.one.stack.MyStack;

public class DepthFirstPath extends AbstractPath {
	private boolean[] marked;	//If current vertex has been accessed.
	private int[] edgeTo;	//Record the path from that point to s.
	public DepthFirstPath(Graph g, int s) {
		super(g, s);
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		dfs(g, s);
	}
	@Override
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	@Override
	public Iterable<Integer> pathTo(int v) {
		if(true == hasPathTo(v)){
			MyStack<Integer> path = new ListStack<>();
			do {
				path.push(v);
				v = edgeTo[v];
			} while (v != s);
			return path;
		}
		return null;
	}
	
	private void dfs(Graph g, int v){
		marked[v] = true;
		for(int w : g.adj(v)){	//All vertex connected to v
			if(!marked[w]){
				edgeTo[w] = v;
				dfs(g, w);
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyCG.txt")));
		Path p = new DepthFirstPath(g, 0);
		Iterable<Integer> path = p.pathTo(1);
		StringBuilder sb = new StringBuilder("0");
		for(Integer pnode : path){
			sb.append("->" + pnode);
		}
		System.out.println(sb.toString());
	}
}
