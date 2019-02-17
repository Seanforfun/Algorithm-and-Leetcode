package ca.mcmaster.chapter.one.unionfind;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuickUnion extends UnionfindAbstract{
	public QuickUnion(int N) {
		super(N);
	}
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if(qRoot == pRoot) return;
		a[pRoot] = qRoot;
		count--;
	}
	public int find(int p) {
		while( p != a[p])	p = a[p];
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
