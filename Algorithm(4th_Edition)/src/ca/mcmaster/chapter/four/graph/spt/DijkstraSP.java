package ca.mcmaster.chapter.four.graph.spt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;

import edu.princeton.cs.algs4.IndexMinPQ;

public class DijkstraSP implements SP {
	private DirectedEdge[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq;	//优先级队列，存储了到每个顶点的dist
	public DijkstraSP(EdgeWeightedDigraph g, int s) {
		edgeTo = new DirectedEdge[g.V()];
		distTo = new double[g.V()];
		pq = new IndexMinPQ<>(g.V());
		for(int v = 0; v < g.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0D;
		pq.insert(s, 0D);
		while(!pq.isEmpty())
			relax(g, pq.delMin());
	}
	@Override
	public double distTo(int v) {
		return distTo[v];
	}
	@Override
	public boolean hasPathTo(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	@Override
	public Iterable<DirectedEdge> pathTo(int v) {
		if(!hasPathTo(v)) return null;
		Stack<DirectedEdge> stack = new Stack<>();
		while(edgeTo[v] != null){
			DirectedEdge e = edgeTo[v];
			stack.push(e);
			v = e.from();
		}
		return stack;
	}
	/**
	 * @Description: Relax the edge e.
	 * @param e
	 */
	@SuppressWarnings("unused")
	private void relax(DirectedEdge e){
		int v = e.from(); int w = e.to();
		if(distTo(w) > distTo(v) + e.weight()){
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
		}
	}
	/**
	 * @Description: G is digraph and v is a vertex.
	 * @param g
	 * @param v
	 */
	private void relax(EdgeWeightedDigraph g, int v){
		for(DirectedEdge e:g.adj(v)){
			int w = e.to();		//e is from v to w.
			if(distTo[w] > distTo[v] + e.weight()){
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
				if(pq.contains(w)) pq.changeKey(w, distTo[w]);
				else pq.insert(w, distTo[w]);
			}
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/spt/tinyEWD.txt"));
		EdgeWeightedDigraph g = new EdgeWeightedDigraph(is);
		DijkstraSP dijkstraSP = new DijkstraSP(g, 0);
		Stack<DirectedEdge> pathTo = (Stack<DirectedEdge>) dijkstraSP.pathTo(6);
		StringBuilder sb = new StringBuilder();
		DirectedEdge edge = pathTo.pop();
		sb.append(edge.from() + "->");
		sb.append(edge.to() + "->");
		while(!pathTo.empty()){
			sb.append(pathTo.pop().to() + "->");
		}
		System.out.println(sb.toString());
	}
}
