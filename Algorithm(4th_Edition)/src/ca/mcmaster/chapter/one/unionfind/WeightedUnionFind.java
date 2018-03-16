package ca.mcmaster.chapter.one.unionfind;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WeightedUnionFind extends UnionfindAbstract {
	private int[] size;
	public WeightedUnionFind(int N) {
		super(N);
		size = new int[N];
		for(int i = 0; i < N; i++)
			size[i] = 1;
	}
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if(pRoot == qRoot) return;
		if(size[pRoot] > size[qRoot]){
			a[qRoot] = pRoot;
			size[pRoot] += size[qRoot];
		}
		else{
			a[pRoot] = qRoot;
			size[qRoot] += size[pRoot];
		}
		count--;
	}
	public int find(int p) {
		while(p != a[p]) p = a[p];
		return p;
	}
	public static void main(String[] args) throws FileNotFoundException{
		File f = new File("src/ca/mcmaster/chapter/one/unionfind/tinyUF.txt");
		Scanner scanner = new Scanner(f);
		int N = scanner.nextInt();
		QuickFind uf = new QuickFind(N);
		while(scanner.hasNext()){
			int p = scanner.nextInt();
			int q = scanner.nextInt();
			if(uf.connected(p, q))	continue;
			uf.union(p, q);
			System.out.println(p +" <---> "+ q);
		}
		System.out.println(uf.count() + " components");
	}
}
