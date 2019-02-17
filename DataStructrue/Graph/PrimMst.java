package ca.mcmaster.chapter.four.graph.mstree;

import edu.princeton.cs.algs4.IndexMinPQ;

public class PrimMst implements MST {
	private Edge[] edgeTo;
	private double[] distTo;
	private boolean[] marked;
	private IndexMinPQ<Double> pq;
	public PrimMst(EdgeWeightedGraph g) {
		edgeTo = new Edge[g.V()];
		distTo = new double[g.V()];
		marked = new boolean[g.V()];
		for(int i = 0; i < g.V(); i++){
			distTo[i] = Double.POSITIVE_INFINITY;
		}
		pq = new IndexMinPQ<Double>(g.V());
		distTo[0] = 0.0d;
		while(!pq.isEmpty())
			visit(g, pq.delMin());
	}
	private void visit(EdgeWeightedGraph g, int v){	//当前的顶点不在最小树中
		marked[v] = true;
		for(Edge e : g.adj(v)){	//遍历连接这个顶点的所有边
			int w = e.other(v);		//找到这些边中除了v的另一个顶点
			if(marked[w]) continue;	//v-w已经在树中，失效
			if(e.weight() < distTo[w]){	//判断是不是可以替代
				edgeTo[w] = e;
				distTo[w] = e.weight();
				if(pq.contains(w)) pq.change(w, distTo[w]);
				else pq.insert(w, distTo[w]);
			}
		}
	}
	@Override
	public Iterable<Edge> edges() {
		return null;
	}
	@Override
	public double weight() {
		return 0;
	}
}
