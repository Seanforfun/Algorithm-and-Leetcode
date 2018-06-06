package ca.mcmaster.chapter.four.graph.mstree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class LazyPrimMST implements MST {
	private final EdgeWeightedGraph g;
	private PriorityQueue<Edge> pq;
	private Queue<Edge> mst;	//minimum spanning tree
	private final boolean[] marked;	//If current vertex is in the tree
	public LazyPrimMST(EdgeWeightedGraph g){
		this.g = g;
		marked = new boolean[g.V()];
		pq = new PriorityQueue<>();
		mst = new LinkedList<Edge>();
		visit(g, 0);
		while(!pq.isEmpty()){
			Edge edge = pq.poll();	//get the smallest edge from the graph
			int v = edge.either(); int w = edge.other(v);
			if(marked[v] && marked[w]) continue;	//both vertexs are in the mst
			mst.offer(edge);
			if(!marked[v]) visit(g, v);
			if(!marked[w]) visit(g, w);
		}
	}
	@Override
	public Iterable<Edge> edges() {
		return mst;
	}
	@Override
	public double weight() {
		double res = 0d;
		for(Edge e : mst)
			res += e.weight();
		return res;
	}
	private void visit(EdgeWeightedGraph g, int v){
		marked[v] = true;
		for(Edge e : g.adj(v))
			if(!marked[e.other(v)]) pq.add(e);
	}
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/mstree/mediumEWG.txt"));
		EdgeWeightedGraph graph = new EdgeWeightedGraph(is);
		LazyPrimMST mst = new LazyPrimMST(graph);
		Iterable<Edge> edges = mst.edges();
		for(Edge e : edges)
			System.out.println(e.toString());
		System.out.println("Weight: " + mst.weight());
	}
}
