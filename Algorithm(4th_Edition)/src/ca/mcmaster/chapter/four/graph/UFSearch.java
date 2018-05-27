package ca.mcmaster.chapter.four.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UFSearch extends AbstractSearch {
	private final UF uf;
	public UFSearch(Graph g, int s) {
		super(g, s);
		this.uf = new UF(g.V());
		//Insert connections into the UF.
		for(int v = 0; v < g.V(); v++){
			for(int w : g.adj(v)){
				if(uf.connected(v, w))	continue;
				else	uf.union(v, w);
			}
		}
	}

	@Override
	public boolean mark(int v) {
		return uf.connected(super.s, v);
	}

	@Override
	public int count() {
		return uf.size[super.s];
	}
	private final class UF{
		private final int N;
		private final int[] a;
		private final int[] size;
		public UF(int N){
			this.N = N;
			a = new int[N];
			for(int i = 0; i < N; i++)	a[i] = i;
			size = new int[N];
			for(int i = 0; i < N; i++)	size[i] = 1;
		}
		public int find(int v){
			if(a[v] == v)	return v;
			else	return find(a[v]);
		}
		public void union(int p, int q){
			int qRoot = find(q);
			int pRoot = find(p);
			if(pRoot == qRoot) return;
			if(size[qRoot] < size[pRoot]){
				a[qRoot] = pRoot;
				size[pRoot] += size[qRoot];
			}else{	//size[qRoot] >= size[pRoot]
				a[pRoot] = qRoot;
				size[qRoot] += size[pRoot];
			}
		}
		public boolean connected(int p, int q){
			return find(q) == find(p);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new UndirectedGraph(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyG.txt")));
		Search search = new UFSearch(g, 9);
		System.out.println(search.mark(4));
	}
}
