package ca.mcmaster.chapter.four.graph.undirected;

import ca.mcmaster.chapter.four.graph.Graph;

public interface SymbolGraph {
	/**
	 * @Description: Is key a vertex.
	 * @param key
	 * @return
	 */
	public boolean contains(String key);
	/**
	 * @Description: Index of key.
	 * @param key
	 * @return
	 */
	public int index(String key);
	/**
	 * @Description: name of v in symbol table
	 * @param v
	 * @return
	 */
	public String name(int v);
	
	/**
	 * @Description: Get the anonymous graph object.
	 * @return
	 */
	public Graph G();
}
