package ca.mcmaster.chapter.four.graph.directed;

public class StrongCircleComponent implements SCC {
	private boolean[] marked;
	private int[] id;
	private int count;
	public StrongCircleComponent(Digraph g) {
		marked = new boolean[g.V()];
		id = new int[g.V()];
		DepthFirstOrder order = new DepthFirstOrder(g.reverse());
		for(int s : order.reversePost()){	//从栈中顺序读取顶点
			if(!marked[s]){
				dfs(g, s); count++;
			}
		}
	}
	
	private void dfs(Digraph g, int v){
		marked[v] = true;
		id[v] = count;
		for(int w : g.adj(v))
			if(!marked[w])
				dfs(g, w);
	}
	@Override
	public boolean strongConnected(int v, int w) {
		return id[v] == id[w];
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public int id(int v) {
		return id[v];
	}
}
