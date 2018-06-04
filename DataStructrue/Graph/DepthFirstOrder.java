package ca.mcmaster.chapter.four.graph.directed;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class DepthFirstOrder {
	private final boolean[] marked;
	private final Queue<Integer> pre;
	private final Queue<Integer> post;
	private final Stack<Integer> reversePost;
	public DepthFirstOrder(Digraph g){
		pre = new LinkedBlockingQueue<Integer>();
		post = new LinkedBlockingQueue<>();
		reversePost = new Stack<>();
		marked = new boolean[g.V()];
		for(int v = 0; v < g.V(); v++)
			if(!marked[v]) dfs(g, v);
	}
	
	private void dfs(Digraph g, int v){
		pre.offer(v);
		marked[v] = true;
		for(int w : g.adj(v))
			if(!marked[w])	dfs(g, w);
		post.offer(v);
		reversePost.push(v);
	}
	public Iterable<Integer> pre(){
		return this.pre;
	}
	public Iterable<Integer> post(){
		return this.post();
	}
	public Iterable<Integer> reversePost(){
		return this.reversePost;
	}
}
