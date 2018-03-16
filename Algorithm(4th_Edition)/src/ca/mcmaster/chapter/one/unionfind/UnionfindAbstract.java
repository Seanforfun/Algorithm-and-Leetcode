package ca.mcmaster.chapter.one.unionfind;

public abstract class UnionfindAbstract implements UnionFind {
	int count;
	int[] a;
	public UnionfindAbstract(int N) {
		count = N;
		a = new int[N];
		for(int i = 0; i < N; i++)
			a[i] = i;
	}
	public Boolean connected(int p, int q) { return find(p) == find(q); }
	public int count() { return count; }
}
