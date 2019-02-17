# Question3_1

#### Solution
* 固定长度的栈
```Java
public class OfferStack {
	private int capacity;
	private Integer[] arr;
	public void display(){
		for(int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + "  ");
		System.out.println();
	}
	public OfferStack(int capacity){
		this.capacity = capacity;
		arr = new Integer[capacity * 3 + 3];
		arr[0] = 1;
		arr[1 + capacity] = 2 + capacity;
		arr[2 + capacity * 2] = 3 + 2 * capacity;
	}
	public void push(Integer v, int index){
		int writeIndex = arr[index + index * capacity];
		if(writeIndex >= (index + 1) * (1 + capacity) )
			throw new ArrayIndexOutOfBoundsException();
		arr[writeIndex] = v;
		arr[index + index * capacity] = ++writeIndex;
	}
	public Integer pop(int index){
		int readIndex = arr[index + index * capacity] - 1;
		Integer v = arr[readIndex];
		arr[readIndex] = null;
		if(v == null)
			throw new ArrayIndexOutOfBoundsException();
		arr[index + index * capacity] = readIndex;	//注意此处的readIndex已经被减过了，可以不用再做计算。
		return v;
	}
	public static void main(String[] args) {
		OfferStack stack = new OfferStack(5);
		stack.push(1, 0);
		stack.push(2, 0);
		stack.push(3, 0);
		stack.push(4, 0);
		stack.push(5, 0);
		stack.push(5, 0);
//		stack.display();
		stack.push(2, 1);
		stack.push(1, 1);
//		System.out.println(stack.pop(0));
//		System.out.println(stack.pop(0));
		System.out.println(stack.pop(0));
//		stack.display();
		System.out.println(stack.pop(0));
		System.out.println(stack.pop(0));
		System.out.println(stack.pop(1));
		System.out.println(stack.pop(1));
	}
}
```
