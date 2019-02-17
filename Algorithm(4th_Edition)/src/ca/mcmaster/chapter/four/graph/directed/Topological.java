package ca.mcmaster.chapter.four.graph.directed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Topological {
	private Iterable<Integer> order;
	public Topological(Digraph g){
		DirectedCycle dc = new DirectedCycle(g);
		if(!dc.hasCycle()){
			DepthFirstOrder dfo = new DepthFirstOrder(g);
			order = dfo.reversePost();
		}
	}
	public Iterable<Integer> order(){
		return order;
	}
	public boolean isDAG(){
		return order != null;
	}
	public static void main(String[] args) throws FileNotFoundException {
		Digraph g = new DigraphImpl(new FileInputStream(new File("src/ca/mcmaster/chapter/four/graph/tinyDG.txt")));
		Topological topological  = new Topological(g);
		StringBuilder sb = new StringBuilder();
		for(int i : topological.order()){
			sb.append(i + " ");
		}
		System.out.println(sb.toString());
	}
}
