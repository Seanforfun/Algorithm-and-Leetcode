package ca.mcmaster.offer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import edu.princeton.cs.algs4.Queue;

public class OfferGraph {
	public class GraphVertex{
		int value;
		List<GraphVertex> connections;
		public GraphVertex(int value, List<GraphVertex> connections){
			this.value = value;
			this.connections = connections;
		}
		public boolean isConnectDFS(GraphVertex vertex){
			Set<GraphVertex> set = new HashSet<>();
			for(GraphVertex v : this.connections){
				System.out.println(v.value);
				if(v == vertex){
					return true;
				}
				if(!set.contains(v)){
					set.add(v);
					return v.isConnectDFS(vertex);
				}else
					continue;
			}
			return false;
		}
		public boolean isConnectBFS(GraphVertex vertex){
			if(null == vertex) return false;
			Set<GraphVertex> set = new HashSet<>();
			Queue<GraphVertex> queue = new Queue<>();
			queue.enqueue(this);
			while(!queue.isEmpty()){
				GraphVertex ver = queue.dequeue();
				for(GraphVertex v:ver.connections){
					if(!set.contains(v)){
						if(vertex == v)	return true;
						set.add(v);
						queue.enqueue(v);
					}
				}
			}
			return false;
		}
	}
	public static void connect(GraphVertex v1, GraphVertex v2){
		v1.connections.add(v2);
	}
}
