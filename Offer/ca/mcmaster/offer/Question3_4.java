package ca.mcmaster.offer;

import java.util.Stack;

public class Question3_4 {
	private static class Tower{
		private Stack<Integer> disks;
		private int index;
		public Tower(int index){
			this.index = index;
			disks = new Stack<>();
		}
		public int getIndex(){
			return this.index;
		}
		public void add(int v){
			if(!disks.isEmpty() && disks.peek() < v)	throw new RuntimeException("Error putting disk!");
			else {
				disks.push(v);
			}
		}
		public void moveTopTo(Tower t){
			int top = disks.pop();
			t.add(top);
		}
		public void moveDisks(int n, Tower destination, Tower buffer){
			if(n > 0){
				moveDisks(n - 1, buffer, destination);
				moveTopTo(destination);
				buffer.moveDisks(n - 1, destination, this);
			}
		}
	}
	public static void main(String[] args) {
		Tower[] towers = new Tower[3];
		for(int i = 0; i < 3; i++)
			towers[i] = new Tower(i);
		for(int i = 10; i >= 0; i--)
			towers[0].add(i);
		towers[0].moveDisks(11, towers[2], towers[1]);
		for(int i = 0; i < 11; i++){
			System.out.println(towers[2].disks.pop());
		}
	}
}
