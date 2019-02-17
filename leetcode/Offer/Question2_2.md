# Question2_2

#### Solution
* 使用双指针的方法，第一个指针遍历链表，第二个指针指向其后的K个元素，如果正好是null则说明第一个指针是倒数第K个元素。

```Java
	public T lastK(int k){
		ListNode<T> curr = dummy;
		ListNode<T> last;
		while(curr.next != null){
			int step = k;
			last = curr.next;
			while(step > 0){
				last = last.next;
				step--;
				if(step > 0 && last == null){
					throw new ArrayIndexOutOfBoundsException();
				}
			}
			if(last == null)
				return curr.next.value;
			curr = curr.next;
		}
		return null;
	}
```