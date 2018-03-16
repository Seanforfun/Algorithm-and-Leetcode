package ca.mcmaster.chapter.one.unionfind;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuickFind extends UnionfindAbstract {
	public QuickFind(int N) {
		super(N);
	}
	public void union(int p, int q) {
		int pId = a[p];
		int qId = a[q];
		if(pId == qId) return;
		for(int i = 0; i < a.length; i++)
			if(pId == a[i])	a[i] = qId;
		count--;
	}
	public int find(int p) {
		return a[p];
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
