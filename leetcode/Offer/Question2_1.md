# Question2_1

#### Solution
* 可以使用额外内存

```Java
public void removeDuplicate(){
		Set<T> set = new HashSet<>();
		ListNode<T> curr = dummy;
		while(curr.next != null){
			if(!set.contains(curr.next.value)){
				set.add(curr.next.value);
				curr = curr.next;	//写代码中一定要注意只有当不存在的时候才会将node后移，不然继续对当前的结点的后继节点进行检查。
			}else{
				curr.next = curr.next.next;
				length--;
			}
		}
	}
```

* 不使用额外内存,使用两个指针，第一个指针用于遍历，第二个指针用于删除后继结点中的相同元素。

```Java
public void removeDuplicateTwoPointer(){
		ListNode<T> curr = dummy;
		ListNode<T> check;
		while(curr.next != null){
			T t = curr.next.value;
			check = curr.next;
			while(check.next != null){
				if(check.next.value == t){
					check.next = check.next.next;
				}else
					check = check.next;
			}
			curr = curr.next;
		}
	}
```