package ca.mcmaster.chapter.four.graph.undirected;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.LinkedBlockingQueue;

import ca.mcmaster.chapter.four.graph.Graph;
import ca.mcmaster.chapter.four.graph.Path;
import ca.mcmaster.chapter.one.stack.ListStack;
import ca.mcmaster.chapter.one.stack.MyStack;

public class BreadthFirstPath extends AbstractPath{
	private final boolean[] marked;
	private final int[] edgeTo;
	public BreadthFirstPath(Graph g, int s) {
		super(g, s);
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
		MyStack<Integer> stack = new ListStack<>();
		do {
			stack.push(v);
			v = edgeTo[v];
		} while (v != s);
		return stack;
	}
	
	private void bfs(Graph g, int s){
		LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();
		marked[s] = true;
		q.add(s);
		while(!q.isEmpty()){
			Integer v = q.poll();
			for(int w : g.adj(v)){
				if(!marked[w]){	//Current vertex is not accessed.
					q.add(w);
					edgeTo[w] = v;
					marked[w] = true;
				}
			}
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyCG.txt")));
		Path p = new BreadthFirstPath(g, 0);
		Iterable<Integer> path = p.pathTo(4);
		StringBuilder sb = new StringBuilder("0");
		for(Integer pnode : path){
			sb.append("->" + pnode);
		}
		System.out.println(sb.toString());
	}
}
