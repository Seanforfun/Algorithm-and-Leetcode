package ca.mcmaster.chapter.one;

import edu.princeton.cs.algs4.Stack;

public class Evaluate {
	public static void main(String[] args){
		String s = "( 1 + ( ( 1 + 1 ) * ( 1 * 1 ) ) )";
		Stack<String> ops = new Stack<String>();
		Stack<Double> value = new Stack<Double>();
		String[] tokens = s.split(" ");
		
		//Put all useful values into stack.
		for(String t:tokens){
			if(t.equals("(")) continue;
			else if(t.equals("+")) ops.push(t);
			else if(t.equals("-")) ops.push(t);
			else if(t.equals("*")) ops.push(t);
			else if(t.equals("/")) ops.push(t);
			else if(t.equals(")")){
				String op = ops.pop();
				Double v = value.pop();
				if(op.equals("+")) v = value.pop() + v;
				if(op.equals("-")) v = value.pop() - v;
				if(op.equals("*")) v = value.pop() * v;
				if(op.equals("/")) v = value.pop() / v;
				value.push(v);
			}else
				value.push(Double.parseDouble(t));
		}
		System.out.println(value.pop());
	}
}
