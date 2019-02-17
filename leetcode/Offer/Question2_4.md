# Question2_4

#### Solution
```Java
	public ListNode<Integer> partition(Integer k, ListNode<Integer> dummy){
		ListNode<Integer> dummySmall = new ListNode<Integer>(null);
		ListNode<Integer> currSmall = dummySmall;
		ListNode<Integer> dummyOther = new ListNode<Integer>(null);
		ListNode<Integer> currOther = dummyOther;
		while(dummy.next != null){
			Integer value = dummy.next.value;
			if(value < k){
				currSmall.next = dummy.next;
				currSmall = currSmall.next;
			}else{
				currOther.next = dummy.next;
				currOther = currOther.next;
			}
			dummy = dummy.next;
		}
		currSmall.next = dummyOther.next;
		return dummySmall.next;
	}
```
