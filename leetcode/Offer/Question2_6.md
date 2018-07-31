# Question2_6

#### Solution
```Java
public class Question2_6 {
	public static OfferList<Integer>.ListNode<Integer> isBegining(OfferList<Integer>.ListNode<Integer> dummy){
		OfferList<Integer>.ListNode<Integer> slow = dummy.next.next;
		OfferList<Integer>.ListNode<Integer> fast = dummy.next.next;
		while(slow != fast){
			slow = slow.next;
			fast = fast.next.next;
		}
		if(fast == null)
			return null;
		OfferList<Integer>.ListNode<Integer> beginning = dummy.next;
		while(beginning != fast){
			beginning = beginning.next;
			fast = fast.next;
		}
		return beginning;
	}
	public static void main(String[] args) {
		OfferList<Integer>.ListNode<Integer> dummy = new OfferList<Integer>().new ListNode<Integer>(null); 
		OfferList<Integer>.ListNode<Integer> head = dummy;
		OfferList<Integer>.ListNode<Integer> begin = null;
		for(int i = 0; i < 20; i++){
			OfferList<Integer>.ListNode<Integer> temp = new OfferList<Integer>().new ListNode<Integer>(i);
			begin = temp;
			dummy.next = temp;
			dummy = dummy.next;
		}
		System.out.println(begin.value);
		for(int i = 20; i <40; i++){
			OfferList<Integer>.ListNode<Integer> temp = new OfferList<Integer>().new ListNode<Integer>(i);
			dummy.next = temp;
			dummy = dummy.next;
		}
		dummy.next = begin;
		System.out.println(isBegining(head).value);
	}
}
```
