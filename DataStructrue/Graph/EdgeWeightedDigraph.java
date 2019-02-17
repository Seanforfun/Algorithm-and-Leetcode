package ca.mcmaster.chapter.four.graph.spt;

import java.io.FileInputStream;
import java.util.Scanner;

import ca.mcmaster.chapter.one.bag.Bag;
import ca.mcmaster.chapter.one.bag.ListBag;

public class EdgeWeightedDigraph {
	private final int V; //Number of vertex.
	private int E; //Number of edges.
	private Bag<DirectedEdge>[] adj;	//Adjacency list
	@SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(int V){
		this.V = V;
		this.E = 0;
		adj = (Bag<DirectedEdge>[])new Bag[V];
		for(int i = 0; i < V; i++)
			adj[i] = new ListBag<>();
	}
	@SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(FileInputStream in) {
		Scanner s = null;
		s = new Scanner(in);
		this.V = s.nextInt();
		this.E = s.nextInt();
		adj = (Bag<DirectedEdge>[])new Bag[V];
		for (int i = 0; i < V; i++)
			adj[i] = new ListBag<DirectedEdge>(); 
		for(int i = 0; i < E; i ++){
			int v = s.nextInt();
			int w = s.nextInt();
			double weight = s.nextDouble();
			addEdge(v, w, weight);
		}
		s.close();
	}
	public int V() {	return this.V;	}
	public int E() {	return this.E;	}
	public void addEdge(int v, int w, double weight) {
		adj[v].add(new DirectedEdge(v, w, weight));
		this.E ++;
	}
	public Iterable<DirectedEdge> adj(int v) {
		return adj[v];
	}
	public void display() {
		for(int i = 0; i < V; i++){
			StringBuilder sb = new StringBuilder(i + ": ");
			for(DirectedEdge e : adj[i]){
				sb.append(e.to() + "|");
			}
			System.out.println(sb.toString());
		}
	}
}
