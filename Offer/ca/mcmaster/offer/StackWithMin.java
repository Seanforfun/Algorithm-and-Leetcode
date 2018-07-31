package ca.mcmaster.offer;

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
