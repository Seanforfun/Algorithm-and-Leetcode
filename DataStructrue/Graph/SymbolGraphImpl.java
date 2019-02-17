package ca.mcmaster.chapter.four.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SymbolGraphImpl implements SymbolGraph {
	private final Map<String, Integer> st;
	private String[] keys;
	private final Graph G;
	public SymbolGraphImpl(String file, String sp) throws FileNotFoundException {
		st = new HashMap<>();
		//fulfill the symbol table
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(file));
			int count = 0;
			while(scanner.hasNextLine()){
				String[] tokens = scanner.nextLine().split(sp);
				for(String t : tokens){
					if(!st.containsKey(t)){	//If not contains token then put it into the st.
						st.put(t, count++);
					}
				}
			}
		}finally{
			scanner.close();
		}
		keys = new String[st.size()];
		for(String name : st.keySet()){
			keys[st.get(name)] = name;
		}
		// Create graph
		G = new UndirectedGraph(st.size());
		//Add edges
		Scanner scanner2 = null;
		try{
			scanner2 = new Scanner(new File(file));
			while(scanner2.hasNextLine()){
				String[] tokens = scanner2.nextLine().split(sp);
				for(int i = 1; i < tokens.length; i++){
					int v = st.get(tokens[0]);
					G.addEdge(v, st.get(tokens[i]));
				}
			}
		}finally{
			scanner2.close();
		}
		
	}
	@Override
	public boolean contains(String key) { return st.containsKey(key); }

	@Override
	public int index(String key) { return st.get(key); }

	@Override
	public String name(int v) { return keys[v]; }

	@Override
	public Graph G() { return G; }
	
	public static void main(String[] args) throws FileNotFoundException {
		SymbolGraphImpl symbolGraphImpl = new SymbolGraphImpl("src/ca/mcmaster/chapter/four/graph/movies.txt", "/");
		Graph graph = symbolGraphImpl.G();
		int vertexNum = graph.V();
		for(int v = 0; v < vertexNum; v++){
			StringBuilder sb = new StringBuilder(symbolGraphImpl.name(v) + " -> ");
			for(int w : graph.adj(v))
				sb.append(symbolGraphImpl.name(w) + "|");
		}
		BreadthFirstPath bfs = new BreadthFirstPath(graph, symbolGraphImpl.index("Bacon, Kevin"));
		Iterable<Integer> pathTo = bfs.pathTo(symbolGraphImpl.index("Kidman, Nicole"));
		System.out.println("Bacon, Kevin");
		for(int w : pathTo){
			System.out.println(symbolGraphImpl.name(w));
		}
	}
}
