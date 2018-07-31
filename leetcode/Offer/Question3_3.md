# Question3_3

#### Solution
* 通过ArrayList作为Stack的容器。
```Java
public class SetOfStack {
	private int stackNum = 0;
	private int capacity;
	public List<SetOfStack.Stack> stacks = new ArrayList<SetOfStack.Stack>();
	private class Stack{
		private int writeIndex = 0;
		private int capacity;
		private Integer[] arr;
		public Stack(int capacity){
			this.capacity = capacity;
			this.arr = new Integer[capacity];
		}
		public boolean isFull(){
			return writeIndex >= capacity;
		}
		public boolean isEmpty(){
			return writeIndex == 0;
		}
		public void push(int v){
			if(isFull()) throw new ArrayIndexOutOfBoundsException();
			arr[writeIndex] = v;
			writeIndex++;	//此处不能忘记++！
		}
		public int pop(){
			if(isEmpty()) throw new ArrayIndexOutOfBoundsException();
			int readIndex = writeIndex - 1;
			int result = arr[readIndex];
			writeIndex = readIndex;
			return result;
		}
	}
	public SetOfStack(int capacity){
		this.capacity = capacity;
		stacks.add(new Stack(capacity));
		stackNum++;
	}
	public SetOfStack.Stack getLastStack(){
		return stacks.get(stackNum - 1);
	}
	public void push(int v){
		Stack lastStack = getLastStack();
		if(lastStack.isFull()){
			stacks.add(new Stack(this.capacity));
			stackNum ++;
			Stack newLastStack = getLastStack();
			newLastStack.push(v);
		}else{
			lastStack.push(v);
		}
	}
	public int pop(){
		Stack lastStack = getLastStack();
		if(lastStack.isEmpty()){
			stacks.remove(stackNum - 1);
			stackNum--;
			Stack lastStack2 = getLastStack();
			return lastStack2.pop();
		}else
			return lastStack.pop();
	}
	public static void main(String[] args) {
		SetOfStack stack = new SetOfStack(10);
		for(int i = 0; i < 100; i++){
			stack.push(i);
		}
		System.out.println(stack.stackNum);
		for(int i = 0; i < 100; i++){
			System.out.println(stack.pop());
		}
	}
}
```
