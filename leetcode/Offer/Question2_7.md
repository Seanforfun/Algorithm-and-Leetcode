# Question2_7

#### Solution
* 因为不知道链表的长度，所以我们使用快慢两个指针，快指针的速度是慢指针的两倍，所以当快指针移动到链表的尾部时，慢指针指向链表的中部（如果链表是偶数的），或者指向正中间的元素。
```Java
public class Question2_7 {
	public static boolean isPalindrome(OfferList<Integer>.ListNode<Integer> dummy){
		Stack<Integer> stack = new Stack<>();
		OfferList<Integer>.ListNode<Integer> slow = dummy;
		OfferList<Integer>.ListNode<Integer> fast = dummy;
		while(fast != null && fast.next != null){
			slow = slow.next;
			stack.push(slow.value);
			fast = fast.next.next;
		}
		if(fast == null)
			stack.pop();
		while(slow.next != null){
			if(slow.next.value != stack.pop())
				return false;
			slow = slow.next;
		}
		return true;
	}
	public static void main(String[] args) {
		OfferList<Integer>.ListNode<Integer> dummy = new OfferList<Integer>().new ListNode<Integer>(null);
		OfferList<Integer>.ListNode<Integer> node1 = new OfferList<Integer>().new ListNode<Integer>(1);
		dummy.next = node1;
		OfferList<Integer>.ListNode<Integer> node2 = new OfferList<Integer>().new ListNode<Integer>(2);
		node1.next = node2;
		OfferList<Integer>.ListNode<Integer> node3 = new OfferList<Integer>().new ListNode<Integer>(3);
		node2.next = node3;
		OfferList<Integer>.ListNode<Integer> node6 = new OfferList<Integer>().new ListNode<Integer>(3);
		node3.next = node6;
		OfferList<Integer>.ListNode<Integer> node4 = new OfferList<Integer>().new ListNode<Integer>(2);
		node6.next = node4;
		OfferList<Integer>.ListNode<Integer> node5 = new OfferList<Integer>().new ListNode<Integer>(1);
		node4.next = node5;
		OfferList<Integer>.ListNode<Integer> node7 = new OfferList<Integer>().new ListNode<Integer>(1);
		node5.next = node7;
		System.out.println(isPalindrome(dummy));
	}
}
```
