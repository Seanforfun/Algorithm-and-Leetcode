package ca.mcmaster.offer;

import java.util.HashSet;
import java.util.Set;

public class OfferList<T> {
	private ListNode<T> dummy = new ListNode<T>(null);
	private ListNode<T> curr = dummy;
	private int length = 0;
	public class ListNode<T>{
		public T value;
		public ListNode<T> next;
		public ListNode(T value){
			this.value = value;
		}
	}
	public int size(){
		return length;
	}
	public void add(T t){
		ListNode<T> currentNode = new ListNode<>(t);
		curr.next = currentNode;
		curr = currentNode;
		length++;
	}
	public T get(int index){
		if(index + 1 > length)
			throw new ArrayIndexOutOfBoundsException();
		ListNode<T> current = dummy;
		while(index + 1 > 0){
			current = current.next;
			index--;
		}
		return current.value;
	}
	public ListNode<T> getNode(int index){
		if(index + 1 > length)
			throw new ArrayIndexOutOfBoundsException();
		ListNode<T> curr = dummy;
		while(index + 1 > 0){
			curr = curr.next;
			index --;
		}
		return curr;
	}
	public void remove(int index){
		if(index+1 > length)
			throw new ArrayIndexOutOfBoundsException();
		ListNode<T> pre = dummy;
		while(index > 0){
			pre = pre.next;
			index --;
		}
		ListNode<T> delete = pre.next;
		pre.next = delete.next;
		length--;
	}
	public void display(){
		ListNode<T> curr = dummy;
		while(curr.next != null){
			System.out.print(curr.next.value + "	");
			curr = curr.next;
		}
		System.out.println();
	}
	public void displayAfter(ListNode<T> node){
		if(node == null)
			return;
		System.out.print(node.value + "  ");
		while(node.next != null){
			System.out.print(node.value + "  ");
			node = node.next;
		}
		System.out.println();
	}
	public void removeDuplicate(){
		Set<T> set = new HashSet<>();
		ListNode<T> curr = dummy;
		while(curr.next != null){
			if(!set.contains(curr.next.value)){
				set.add(curr.next.value);
				curr = curr.next;
			}else{
				curr.next = curr.next.next;
				length--;
			}
		}
	}
	public void removeWithNodeStupid(ListNode<T> node){
		ListNode<T> curr = node;
		while(curr.next != null && curr.next.next != null){
			curr.value = curr.next.value;
			curr = curr.next;
		}
		curr.value = curr.next.value;
		curr.next = null;
	}
	public void removeWithNode(ListNode<T> node){
		ListNode<T> curr = node;
		if(curr.next == null){
			curr = null;
			return;
		}
		curr.value = curr.next.value;
		curr.next = curr.next.next;
	}
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
		if(carry == 1){
			result.add(1);
		}
		return result;
	}
	public static void main(String[] args) {
		OfferList<Integer> add1 = new OfferList<>();
		OfferList<Integer> add2 = new OfferList<>();
		add1.add(9);
		add1.add(9);
		add1.add(9);
		add1.display();
		add2.add(9);
		add2.add(9);
		add2.add(9);
		add2.display();
		OfferList<Integer> addition = add1.addition(add1.dummy, add2.dummy);
		addition.display();
//		list.display();
//		System.out.println(list.length);
//		System.out.println(list.get(8));
////		list.removeDuplicateTwoPointer();
//		System.out.println(list.lastK(2));
//		OfferList<Integer>.ListNode<Integer> node = list.getNode(3);
//		System.out.println(node.value);
//		OfferList<Integer>.ListNode<Integer> partition = list.partition(4, list.dummy);
//		list.displayAfter(partition);
////		list.removeWithNode(node);
//		list.display();
	}
}
