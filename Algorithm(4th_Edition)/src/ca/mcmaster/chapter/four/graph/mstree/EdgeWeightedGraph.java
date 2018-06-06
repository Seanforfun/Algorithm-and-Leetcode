package ca.mcmaster.chapter.four.graph.mstree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ca.mcmaster.chapter.one.bag.Bag;
import ca.mcmaster.chapter.one.bag.ListBag;

public class EdgeWeightedGraph {
	private final int V;	//Number of vertex
	private int E; //Number of edge
	private final Bag<Edge>[] adj; //Create bag for saving edges of vertex
	@SuppressWarnings("unchecked")
	public EdgeWeightedGraph(int V){
		this.V = V;
		this.E = 0;
		adj = new Bag[V];
		for(int v = 0; v < V ; v++)	adj[v] = new ListBag<>();
	}
	@SuppressWarnings("unchecked")
	public EdgeWeightedGraph(FileInputStream in){
		Scanner scanner = null;
		try {
			scanner = new Scanner(in);
			V = scanner.nextInt();
			E = 0;
			int edgeNum = scanner.nextInt();
			adj = new Bag[V];
			for(int v = 0; v < V ; v++)	adj[v] = new ListBag<>();
			for(int e = 0; e < edgeNum; e++)	addEdge(new Edge(scanner.nextInt(), scanner.nextInt(), scanner.nextDouble()));
		} finally{
			scanner.close();
		}
	}
	private void addEdge(Edge e){
		int v = e.either();
		int w = e.other(v);
		adj[v].add(e);
		adj[w].add(e);
		E++;
	}
	public int V(){
		return this.V;
	}
	public int E(){
		return this.E;
	}
	public Iterable<Edge> adj(int v){
		return adj[v];
	}
	public Iterable<Edge> edges(){
		List<Edge> res = new ArrayList<Edge>();
		for(int v = 0; v < V; v++)
			for(Edge e : adj[v]){
				if(v > e.other(v)) res.add(e);
			}
		return res;
	}
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/mstree/tinyEWG.txt"));
		EdgeWeightedGraph graph = new EdgeWeightedGraph(is);
		Iterable<Edge> edges = graph.edges();
		for(Edge e : edges)
			System.out.println(e.toString());
		System.out.println(graph.E);
	}
}
