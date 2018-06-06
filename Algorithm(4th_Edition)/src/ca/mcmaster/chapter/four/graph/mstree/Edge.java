package ca.mcmaster.chapter.four.graph.mstree;

public class Edge implements Comparable<Edge> {
	private final int v;
	private final int w;
	private final double weight;
	public Edge(int v, int w, double weight){
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	@Override
	public int compareTo(Edge o) {
		if(this.weight - o.weight > 0) return 1;
		else if(this.weight == o.weight) return 0;
		else return -1;
	}
	public double weight(){
		return this.weight;
	}
	public int either(){
		return v;
	}
	public int other(int v){
		if(v == this.v) return this.w;
		else
			return this.v;
	}
	@Override
	public String toString() {
		return String.format("%d-%d %.2f", v, w, weight);
	}
}
