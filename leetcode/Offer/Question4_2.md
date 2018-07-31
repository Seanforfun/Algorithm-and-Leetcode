# Question4_2

#### Solution
* 图的定义
```Java
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
					return v.isConnect(vertex);	//递归调用，我们判断当前的顶点是否与所期待的顶点相连。必须在递归中进行return，不然在底层返回了结果对上层并，并没有影响。
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
```
