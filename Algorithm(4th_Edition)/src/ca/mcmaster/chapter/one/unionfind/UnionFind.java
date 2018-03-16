package ca.mcmaster.chapter.one.unionfind;

public interface UnionFind {
	public void union(int p,int q);
	public int find(int p);
	public Boolean connected(int p, int q);
	public int count();
}
