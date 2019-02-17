# Question3_6

#### Solution
![flowchart](https://i.imgur.com/vgfAJFo.png)
```Java
public class Question3_6 {
	public static Stack<Integer> sortStack(Stack<Integer> stack){
		Stack<Integer> result = new Stack<>();
		Integer tmp = null;
		while(!stack.isEmpty()){
			tmp = stack.pop();
			while(!result.isEmpty() && result.peek() > tmp)
				stack.push(result.pop());
			result.push(tmp);
		}
		return result;
	}
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(2);
		stack.push(3);
		stack.push(1);
		stack.push(3);
		stack.push(2);
		stack.push(6);
		stack.push(7);
		stack.push(4);
		stack.push(3);
		stack.push(2);
		Stack<Integer> sortStack = sortStack(stack);
		while(!sortStack.isEmpty()){
			System.out.println(sortStack.pop());
		}
	}
}
```
