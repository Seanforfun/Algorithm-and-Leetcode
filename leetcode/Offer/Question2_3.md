# Question2_3

#### Solution
* 删除单向链表的某个结点，并且没有给出头指针，只给了这个结点。
	* 将这个结点后面的所有结点的值向前复制，并删除最后一个结点。
```Java
public void removeWithNodeStupid(ListNode<T> node){
		ListNode<T> curr = node;
		while(curr.next != null && curr.next.next != null){
			curr.value = curr.next.value;
			curr = curr.next;
		}
		curr.value = curr.next.value;
		curr.next = null;
	}
```
	* 将要删除的结点的后一个结点的值向前复制，只删除后一个节点。
```Java
	public void removeWithNode(ListNode<T> node){
		ListNode<T> curr = node;
		if(curr.next == null){
			curr = null;
			return;
		}
		curr.value = curr.next.value;
		curr.next = curr.next.next;
	}
```
