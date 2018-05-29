package ca.mcmaster.chapter.four.graph.directed;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import ca.mcmaster.chapter.one.bag.ListBag;

public class DigraphImpl implements Digraph {
	private final int V;		//Number of vertex in current digraph
	private int E; 	//Number of edges.
	private ListBag<Integer>[] adj;
	@SuppressWarnings("unchecked")
	public DigraphImpl(int V){
		this.V = V;
		adj = (ListBag<Integer>[])new ListBag[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new ListBag<Integer>(); 
		}
		this.E = 0;
	}
	
	@SuppressWarnings("unchecked")

	public DigraphImpl(FileInputStream in) {
		Scanner s = null;
		s = new Scanner(in);
		this.V = s.nextInt();
		this.E = s.nextInt();
		adj = new ListBag[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new ListBag<Integer>(); 
		}
		for(int i = 0; i < E; i ++){
			int v = s.nextInt();
			int w = s.nextInt();
			adj[v].add(w);
		}
		s.close();
	}
	@Override
	public int V() { return this.V; }
	@Override
	public void addEdge(int v, int w) {
		adj[v].add(w);
		E++;
	}
	@Override
	public int E() { return E; }
	@Override
	public Iterable<Integer> adj(int v) {
		return adj[v];
	}
	@Override
	public Digraph reverse() {
		DigraphImpl g  = new DigraphImpl(V);
		for(int v = 0; v < V; v++)
			for(int w : adj(v))
				addEdge(w, v);		
		return g;
	}
	@Override
	public void display() {
		for(int i = 0; i < V; i++){
			StringBuilder sb = new StringBuilder(i + ": ");
			for(int w : adj[i]){
				sb.append(w + "|");
			}
			System.out.println(sb.toString());
		}
	}
	public static void main(String[] args) throws IOException {
		FileInputStream in = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyDG.txt"));
		Digraph graph = new DigraphImpl(in);
		in.close();
		graph.display();
	}
}
