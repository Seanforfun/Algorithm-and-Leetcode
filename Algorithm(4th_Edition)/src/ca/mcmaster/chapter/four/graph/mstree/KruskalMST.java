package ca.mcmaster.chapter.four.graph.mstree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import ca.mcmaster.chapter.one.unionfind.UnionFind;
import ca.mcmaster.chapter.one.unionfind.WeightedUnionFind;

public class KruskalMST implements MST {
	private List<Edge> mst;	//Queue used to save the minimum spanning tree
	public KruskalMST(EdgeWeightedGraph g) {
		mst = new LinkedList<>();
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		for(Edge e : g.edges())
			pq.add(e);
		UnionFind uf = new WeightedUnionFind(g.V());
		while(!pq.isEmpty() && mst.size() < g.V() - 1){
			Edge edge = pq.poll();
			int v = edge.either(); int w = edge.other(v);
			if(uf.connected(v, w)) continue;
			else{
				mst.add(edge);
				uf.union(v, w);
			}
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
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/mstree/mediumEWG.txt"));
		EdgeWeightedGraph graph = new EdgeWeightedGraph(is);
		MST mst = new KruskalMST(graph);
		Iterable<Edge> edges = mst.edges();
		for(Edge e : edges)
			System.out.println(e.toString());
		System.out.println("Weight: " + mst.weight());
	}
}
