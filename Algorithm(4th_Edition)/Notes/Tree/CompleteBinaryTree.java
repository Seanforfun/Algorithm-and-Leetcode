public class CompleteBinaryTree<K extends Comparable<K>> {
	private Object[] arr;	//因为无法实现泛型数组，我们通过Object数组替代。第一个位置不存储元素
	private int N;		//树中元素的个数。
	public CompleteBinaryTree(int N) {
		arr = new Object[N];
	}
	private void swap(int i, int j){	//交换两个元素的位置
		Object temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	@SuppressWarnings("unchecked")
	private boolean less(Integer i, Integer j){
		return ((K)arr[i]).compareTo(((K)arr[j])) < 0;
	}
	private void swin(int k){
		while(k > 1 && less(k/2, k)){
			swap(k/2, k);
			k /= 2;
		}
	}
	public void insert(K k){
		arr[++N] = k;
		swin(N);
	}
	public Integer size(){
		return N;
	}
	@SuppressWarnings("unchecked")
	public K get(int i){
		return (K)arr[i];
	}
	private void sink(int i){
		while(i * 2 <= N){
			int j = i * 2;
			if(j + 1 < N && less(j, j+1)) j++;		//取两个子结点中更大的一个。
			swap(i, j);
			i = j;		//在当前子树中继续进行下沉
		}
	}
	
	public K delMax(){
		K max = (K)arr[1];
		swap(1, N + 1);
		arr[N + 1] = null;
		sink(1);
		N--;
		return max;
	}
	
	public static void main(String[] args) {
		CompleteBinaryTree<Integer> cbtree = new CompleteBinaryTree<>(100);
		cbtree.insert(1);
		cbtree.insert(2);
		cbtree.insert(3);
		cbtree.insert(4);
		cbtree.insert(5);
		cbtree.insert(6);
		cbtree.delMax();
		for(int i = 1; i <= cbtree.size(); i++){
			System.out.println(cbtree.get(i));
		}
	}
}
