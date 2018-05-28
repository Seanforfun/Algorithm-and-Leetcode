package ca.mcmaster.chapter.four.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.mcmaster.chapter.one.bag.Bag;
import ca.mcmaster.chapter.one.bag.ListBag;

public class DFSCC extends AbstractCC {
	private boolean[] marked;
	private int[] id;
	private int count;
	public DFSCC(Graph g) {
		super(g);
		marked = new boolean[g.V()];
		id = new int[g.V()];
		for(int v = 0; v < g.V(); v++)
			if(!marked[v]){	//Current vertex is not accessed.
				dfs(g, v);
				count++;
			}
	}
	@Override
	public boolean connected(int v, int w) {
		return id[w] == id[v];
	}
	@Override
	public int count() {
		return this.count;
	}
	@Override
	public int id(int v) {
		return id[v];
	}
	private void dfs(Graph g, int v){
		marked[v] = true;
		id[v] = count;
		for(int w : g.adj(v))
			if(!marked[w])
				dfs(g, w);
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyG.txt")));
		DFSCC cc = new DFSCC(g);
		int ccNum = cc.count();
		System.out.println("Number of cc: " + ccNum);
		Bag<Integer>[] bag = new ListBag[ccNum];
		for(int i = 0; i < ccNum; i++)
			bag[i] = new ListBag<>();
		for(int i = 0; i < g.V(); i++)
			bag[cc.id(i)].add(i);
		for(int i = 0; i < ccNum; i++){
			StringBuilder sb = new StringBuilder(i + ": ");
			for(int v : bag[i])
				sb.append(v + " ");
			System.out.println(sb.toString());
		}
	}
}
