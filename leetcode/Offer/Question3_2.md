# Question3_2

#### Solution
* 在压栈的时候就存入当前的系统最小值。
```Java
public class StackWithMin {
	private int writeIndex = 0;
	private Integer capacity;
	private Object[] arr;
	private Integer min = Integer.MAX_VALUE;
	public StackWithMin(Integer capacity){
		this.capacity = capacity;
		arr = new Object[capacity];
	}
	class Item{
		Integer value;
		Integer minVal;
		public Item(Integer value, Integer min){
			this.value = value;
			this.minVal = min;
		}
	}
	public void push(int v){
		this.min = Math.min(this.min, v);
		if(writeIndex >= capacity)
			throw new ArrayIndexOutOfBoundsException();
		arr[writeIndex] = (Object)new Item(v, this.min);
		writeIndex++;
	}
	public Integer pop(){
		int readIndex = writeIndex - 1;
		if(readIndex < 0)
			throw new ArrayIndexOutOfBoundsException();
		Item result = (Item)arr[readIndex];
		writeIndex--;
		return result.value;
	}
	public Integer min(){
		int readIndex = writeIndex - 1;
		if(readIndex < 0) throw new ArrayIndexOutOfBoundsException();
		Item result = (Item)arr[readIndex];
		return result.minVal;
	}
	public static void main(String[] args) {
		StackWithMin stack = new StackWithMin(20);
		for(int i = 5; i < 10; i++){
			stack.push(i);
		}
		for(int i = 0; i < 5; i++){
			stack.push(i);
		}
		for(int i = 0; i < 10; i++){
			System.out.println(stack.min());
			System.out.println(stack.pop());
		}
	}
}
```

* 也可以通过两个栈实现，如果输入的结果小于等于最小值则进行压栈，这样就能减少存储空间。例子：当地一个值就是最小值，则再也不需要对最小值进行存储了。
