# Question2_5

#### Solution
```Java
	public OfferList<Integer> addition(ListNode<Integer> node1, ListNode<Integer> node2){
		OfferList<Integer> result = new OfferList<Integer>();
		int carry = 0;
		while(node1.next != null || node2.next != null){
			if(node1.next != null && node2.next != null){
				result.add((node1.next.value + node2.next.value + carry) % 10);
				carry = (node1.next.value + node2.next.value + carry) / 10;
				node1 = node1.next;
				node2 = node2.next;
			}else if(node1.next.value != null && node2.next == null){
				result.add((node1.next.value + carry) % 10);
				carry = (node1.next.value + carry) / 10;
				node1 = node1.next;
			}else if (node1.next == null && node2.next != null) {
				result.add((node2.next.value + carry) % 10);
				carry = (node2.next.value + carry) / 10;
				node2 = node2.next;
			}
		}
		if(carry == 1){	//不能忘记处理最后的Carry问题！
			result.add(1);
		}
		return result;
	}
```
