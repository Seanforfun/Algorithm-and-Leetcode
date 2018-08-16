# Union-Find 并查集
### Definition
* 并查集解决了节点之间的连接问题。
* 是一种图模型。

### 并查集的基本方法
1. 连接union（int p, int q）
2. 查找find（int p），算是一个内部方法，可以定位连接性。
3. 是否连接isConnected（int p, int q）

```Java
public abstract class UnionFind {
	public int[] id;
	public int count;
	public int count(){
		return this.count;
	}
	public abstract int find(int p);
	public abstract void union(int p, int q);
	public boolean isConnected(int p, int q){
		return find(p) == find(q);
	}
}
```

### 并查集的三种实现
#### 快速查找
* union:当得到一个新的连通分量时，我们需要遍历整个数组，所有和其中一个相同的改成另一个，这样的时间复杂度是O(n)。
* 当我们判断两个节点是不是连接时，只需要判断两个节点对应的值是不是相同，时间复杂度是O(1)。

```Java
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
```

#### 快速连接QuickUnion
* 类似于树的连接模型，当查找时，我们通过遍历，不断向上查找根节点。
* 并将其中一个根节点连接到另一个根节点上。这样时间复杂度是O(lgn)。

```Java
public class QuickUnion extends UnionFind{
	public QuickUnion(int N) {
		count = N;
		id = new int[N];
		for(int i = 0; i < N; i++)
			id[i] = i;
	}
	@Override
	public int find(int p) {
		while(id[p] != p)
			p = id[p];
		return p;
	}
	@Override
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if(pRoot == qRoot) return;
		id[pRoot] = qRoot;
		count--;
	}
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("src/ca/mcmaster/chapter/one/unionfind/tinyUF.txt");
		Scanner scanner = new Scanner(f);
		int N = scanner.nextInt();
		QuickUnion uf = new QuickUnion(N);
		while(scanner.hasNext()){
			int p = scanner.nextInt();
			int q = scanner.nextInt();
			if(uf.isConnected(p, q))	continue;
			uf.union(p, q);
			System.out.println(p +" <---> "+ q);
		}
		System.out.println(uf.count() + " components");
	}
}
```

#### 加权并查集
* Quick-Union方法会导致树越来越不平衡，最坏情况会到达O(N),所以加权并查集可以通过维护一个sz数组使树保持平衡。

```Java
public class WeightedUnionFind extends UnionFind{
	private int[] sz;
	public WeightedUnionFind(int N) {
		count = N;
		sz = new int[N];
		id = new int[N];
		for(int i = 0; i < N; i++)
			id[i] = i;
		for(int i = 0; i < N; i++)
			sz[i] = 1;
	}
	@Override
	public int find(int p) {
		while(p != id[p])
			p = id[p];
		return p;
	}
	@Override
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if(pRoot == qRoot) return;
		if(sz[pRoot] > sz[qRoot]){
			id[pRoot] = qRoot;
			sz[qRoot]+= sz[pRoot];
		}else{
			id[qRoot] = pRoot;
			sz[pRoot]+= sz[qRoot];
		}
		count--;
	}
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("src/ca/mcmaster/chapter/one/unionfind/tinyUF.txt");
		Scanner scanner = new Scanner(f);
		int N = scanner.nextInt();
		WeightedUnionFind uf = new WeightedUnionFind(N);
		while(scanner.hasNext()){
			int p = scanner.nextInt();
			int q = scanner.nextInt();
			if(uf.isConnected(p, q))	continue;
			uf.union(p, q);
			System.out.println(p +" <---> "+ q);
		}
		System.out.println(uf.count() + " components");
	}
}
```