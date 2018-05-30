package ca.mcmaster.chapter.four.graph.directed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import ca.mcmaster.chapter.four.graph.Path;

public class BreadFirstPathDirectedGraph implements Path {
	private final Digraph g;
	private int s;
	private final boolean[] marked;
	private final int[] edgeTo;
	public BreadFirstPathDirectedGraph(Digraph g, int s) {
		this.g = g;
		this.s = s;
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		bfs(g, s);
	}
	@Override
	public boolean hasPathTo(int v) {
		return marked[v];
	}

	@Override
	public Iterable<Integer> pathTo(int v) {
		Stack<Integer> path = new Stack<>();
		path.push(v);
		while(s != edgeTo[v]){
			path.push(edgeTo[v]);
			v= edgeTo[v];
		}
		return path;
	}
	private void bfs(Digraph g, int s){
		LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();		
		marked[s] = true;
		q.offer(s);
		while(!q.isEmpty()){
			int v = q.poll();
			for(int w : g.adj(v)){
				if(!marked[w]){
					edgeTo[w] = v;
					marked[w] = true;
					q.offer(w);
				}
			}
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
		DigraphImpl g = new DigraphImpl(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyDG.txt")));
		DepthFirstPathDirectedGraph p = new DepthFirstPathDirectedGraph(g, 6);
		Iterable<Integer> pathTo = p.pathTo(4);
		System.out.print(6 + " ");
		for(Integer w : pathTo)	System.out.print(w + " ");
		System.out.println();
	}
}
