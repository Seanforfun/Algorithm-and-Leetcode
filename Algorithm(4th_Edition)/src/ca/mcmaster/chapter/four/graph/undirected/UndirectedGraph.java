package ca.mcmaster.chapter.four.graph.undirected;

import java.io.FileInputStream;
import java.util.Scanner;

import ca.mcmaster.chapter.four.graph.Graph;
import ca.mcmaster.chapter.one.bag.Bag;
import ca.mcmaster.chapter.one.bag.ListBag;

public class UndirectedGraph implements Graph {
	private final int V; //vertex
	@SuppressWarnings("unused")
	private int E;	//edge
	private Bag<Integer>[] adj;	//adjacency table.
	@SuppressWarnings("unchecked")
	public UndirectedGraph(int V) {
		this.V = V;
		adj =  new ListBag[V];
		for(int v = 0; v < V; v++)
			adj[v] = new ListBag<>();
	}
	@SuppressWarnings("unchecked")
	public UndirectedGraph(FileInputStream in){
		Scanner scanner = new Scanner(in);
		this.V = scanner.nextInt();
		adj =  new ListBag[V];
		for(int v = 0; v < V; v++)
			adj[v] = new ListBag<>();
		int E = scanner.nextInt();
		for(int e = 0; e < E; e++){
			int v = scanner.nextInt();
			int w = scanner.nextInt();
			addEdge(v, w);
		}
		scanner.close();
	}
	@Override
	public int V() { return V; }
	@Override
	public int E() { return 0; }
	@Override
	public void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
		this.E++;
	}

	@Override
	public Iterable<Integer> adj(int v) {
		return adj[v];
	}
}
