package ca.mcmaster.chapter.four.graph.directed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;

public class DirectedCycle {
	private final boolean[] marked;
	private final int[] edgeTo;
	private Stack<Integer> cycle = null;	//有向环上的所有顶点
	private final boolean[] onStack;	//递归调用的栈上的所有顶点
	public DirectedCycle(Digraph g){
		onStack = new boolean[g.V()];
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		int size = g.V();
		for(int v = 0; v < size; v++)
			if(!marked[v])	dfs(g, v);
	}
	private void dfs(Digraph g, int v){
		//在此次的遍历中，记录当前的位置可能在环上。如果结束了没有在环上则改回false
		onStack[v] = true;	
		marked[v] = true;
		for(int w : g.adj(v)){
			if(this.hasCycle()) return;
			else if(!marked[w]){	//当前结点未被访问过，一定不是闭环，递归调用
				edgeTo[w] = v;
				dfs(g, w);
			}
			else if(onStack[w]){		//在此次的遍历中，当前点已经在环上，说明已经构成了cycle.v的下一个结点构成了闭环。
				//创建stack对象，并添加元素。
				cycle = new Stack<>();
				for(int x = v; x != w; x = edgeTo[x])	
					cycle.push(x);
				cycle.push(w);	
				cycle.push(v);
			}
		}
		onStack[v] = false;
	}
	
	public boolean hasCycle(){
		return cycle != null;
	}
	
	public Iterable<Integer> cycle(){
		return cycle;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Digraph g = new DigraphImpl(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyDG.txt")));
		DirectedCycle cycle = new DirectedCycle(g);
		StringBuilder sb = new StringBuilder();
		for(int i : cycle.cycle()){
			sb.append(i + " ");
		}
		System.out.println(sb.toString());
	}
}
