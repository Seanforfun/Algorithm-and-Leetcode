package ca.mcmaster.chapter.four.graph.spt;

public class DirectedEdge {
	private final int v; //边的起始点
	private final  int w; //边的指向
	private final double weight; //有向边的权重
	public DirectedEdge(int v, int w, double weight){
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	public double weight(){	return weight;	}
	public int from(){	return this.v;	}
	public int to(){	return this.w;	}
	@Override
	public String toString() {
		return String.format("%d -> %d %.2f", v, w, weight);
	}
}
