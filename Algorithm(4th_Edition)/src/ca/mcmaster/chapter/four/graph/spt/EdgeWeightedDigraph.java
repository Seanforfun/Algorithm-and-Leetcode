package ca.mcmaster.chapter.four.graph.spt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		try{
			s = new Scanner(in);
			this.V = s.nextInt();
			this.adj = (Bag<DirectedEdge>[])new Bag[V];
			for (int i = 0; i < V; i++)
				adj[i] = new ListBag<DirectedEdge>(); 
			E = s.nextInt();
			for(int i = 0; i < E; i ++){
				int v = s.nextInt();
				int w = s.nextInt();
				double weight = s.nextDouble();
				addEdge(v, w, weight);
			}
		}finally{
			s.close();
		}
	}
	public int V() {	return this.V;	}
	public int E() {	return this.E;	}
	public void addEdge(int v, int w, double weight) {
		this.adj[v].add(new DirectedEdge(v, w, weight));
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
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/spt/tinyEWD.txt"));
		EdgeWeightedDigraph g = new EdgeWeightedDigraph(is);
		g.display();
	}
}
