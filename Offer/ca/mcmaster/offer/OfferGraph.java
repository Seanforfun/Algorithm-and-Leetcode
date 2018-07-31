package ca.mcmaster.offer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OfferGraph {
	public class GraphVertex{
		int value;
		List<GraphVertex> connections;
		public GraphVertex(int value, List<GraphVertex> connections){
			this.value = value;
			this.connections = connections;
		}
		public boolean isConnect(GraphVertex vertex){
			Set<GraphVertex> set = new HashSet<>();
			for(GraphVertex v : this.connections){
				System.out.println(v.value);
				if(v == vertex){
					return true;
				}
				if(!set.contains(v)){
					set.add(v);
					return v.isConnect(vertex);
				}else
					continue;
			}
			return false;
		}
	}
	public static void connect(GraphVertex v1, GraphVertex v2){
		v1.connections.add(v2);
	}
}
