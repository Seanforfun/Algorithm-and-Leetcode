package ca.mcmaster.offer;

import java.util.LinkedList;

public class Question4_2 {
	public static void main(String[] args) {
		OfferGraph graph = new OfferGraph();
		OfferGraph.GraphVertex v1 = graph.new GraphVertex(1, new LinkedList<OfferGraph.GraphVertex>());
		OfferGraph.GraphVertex v2 = graph.new GraphVertex(2, new LinkedList<OfferGraph.GraphVertex>());
		OfferGraph.GraphVertex v3 = graph.new GraphVertex(3, new LinkedList<OfferGraph.GraphVertex>());
		OfferGraph.GraphVertex v4 = graph.new GraphVertex(4, new LinkedList<OfferGraph.GraphVertex>());
		OfferGraph.GraphVertex v5 = graph.new GraphVertex(5, new LinkedList<OfferGraph.GraphVertex>());
		OfferGraph.GraphVertex v6 = graph.new GraphVertex(6, new LinkedList<OfferGraph.GraphVertex>());
		OfferGraph.GraphVertex v7 = graph.new GraphVertex(7, new LinkedList<OfferGraph.GraphVertex>());
		OfferGraph.GraphVertex v8 = graph.new GraphVertex(8, new LinkedList<OfferGraph.GraphVertex>());
		OfferGraph.connect(v1, v6);
		OfferGraph.connect(v1, v5);
		OfferGraph.connect(v5, v7);
		OfferGraph.connect(v7, v6);
		OfferGraph.connect(v8, v6);
		OfferGraph.connect(v8, v3);
		OfferGraph.connect(v2, v8);
		OfferGraph.connect(v3, v2);
		OfferGraph.connect(v8, v4);
		OfferGraph.connect(v2, v4);
		System.out.println(v2.isConnect(v6));
	}
}
