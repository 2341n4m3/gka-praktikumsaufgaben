package graph.impls;



import graph.Vertex;
import graph.util.GkaGraphBuilder;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;

public class UndirectedWeightedGraphBuilder
		extends
		AbstractBaseGraphBuilder<WeightedPseudograph<Vertex, DefaultWeightedEdge>, Vertex, DefaultEdge>
		implements GkaGraphBuilder<WeightedPseudograph<Vertex, DefaultWeightedEdge>> {

	public UndirectedWeightedGraphBuilder() {
		graph = new WeightedPseudograph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}

		@Override
		public boolean readLine(String line) {
			line = line.replace(" ", "");
			line = line.replace(";","");
			String[] vertices = line.split("--|:");
			DefaultWeightedEdge addedEdge = null;
			if (vertices.length == 3) {
				Vertex source = makeVertexFrom(vertices[0]);
				Vertex target = makeVertexFrom(vertices[1]);
				graph.addVertex(source);
				graph.addVertex(target);
				addedEdge = graph.addEdge(source, target);
				graph.setEdgeWeight(addedEdge, Double.parseDouble(vertices[2]));
				return true;
			} else if (vertices.length == 2){
				Vertex source = makeVertexFrom(vertices[0]);
				Vertex target = makeVertexFrom(vertices[1]);
				graph.addVertex(source);
				graph.addVertex(target);
				addedEdge = graph.addEdge(source, target);
				return true;
			 } else if (vertices.length == 1){
				    Vertex source = makeVertexFrom(vertices[0]);
				    graph.addVertex(source);
				    return true;
				   
			}else {
				return false;
			}
	};


	@Override
	protected Vertex makeVertexFrom(String str) {
		return new Vertex(str);
	}

}
